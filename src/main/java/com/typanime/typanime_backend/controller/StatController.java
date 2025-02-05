package com.typanime.typanime_backend.controller;

import com.typanime.typanime_backend.dto.StatDTO;
import com.typanime.typanime_backend.model.Challenge;
import com.typanime.typanime_backend.model.Citation;
import com.typanime.typanime_backend.model.Stat;
import com.typanime.typanime_backend.model.Synopsis;
import com.typanime.typanime_backend.model.User;
import com.typanime.typanime_backend.repository.ChallengeRepository;
import com.typanime.typanime_backend.repository.CitationRepository;
import com.typanime.typanime_backend.repository.StatRepository;
import com.typanime.typanime_backend.repository.SynopsisRepository;
import com.typanime.typanime_backend.repository.UserRepository;
import com.typanime.typanime_backend.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stats")
public class StatController {
    private final JwtTokenProvider jwtTokenProvider;
    private final StatRepository statRepository;
    private final UserRepository userRepository;
    private final SynopsisRepository synopsisRepository;
    private final CitationRepository citationRepository;
    private final ChallengeRepository challengeRepository;

    @Autowired
    public StatController(JwtTokenProvider jwtTokenProvider, UserRepository userRepository,StatRepository statRepository,SynopsisRepository synopsisRepository, CitationRepository citationRepository, ChallengeRepository challengeRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.statRepository = statRepository;
        this.userRepository = userRepository;
        this.synopsisRepository = synopsisRepository;
        this.citationRepository = citationRepository;
        this.challengeRepository = challengeRepository;
    }
    // Récupérer toutes les statistiques sans pagination
    @GetMapping("/all")
    public List<Stat> getAllStats() {
        return statRepository.findAll();
    }

    // Récupérer une statistique par ID
    @GetMapping("/{id}")
    public ResponseEntity<Stat> getStatById(@PathVariable Long id) {
        return statRepository.findById(id)
                .map(stat -> ResponseEntity.ok().body(stat))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/last10/{username}")
    public ResponseEntity<List<Stat>> getLast10Stats(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        List<Stat> stats = statRepository.findTop10ByUserOrderByIdDesc(user);
        return ResponseEntity.ok(stats);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStat(@RequestBody StatDTO statDTO) {
        User user = userRepository.findByUsername(statDTO.getUsername());
        long sc = synopsisRepository.countAllSynopsis();
        long cc = citationRepository.countAllCitation();
        Optional<Stat> existingStat = Optional.empty();
    
        Stat stat = new Stat();
        stat.setAccuracy(statDTO.getAccuracy());
        stat.setSpeed(statDTO.getSpeed());
        stat.setUser(user);
        if (statDTO.getSynopsisId() != null) {
            Synopsis synopsis = synopsisRepository.findById(statDTO.getSynopsisId())
            .orElseThrow(() -> new RuntimeException("Synopsis non trouvé"));
            existingStat = statRepository.findByUserAndSynopsis(user, synopsis);
            if (existingStat.isPresent() && existingStat.get().getSpeed() <= statDTO.getSpeed()) {
                existingStat.get().setSpeed(statDTO.getSpeed());
                existingStat.get().setAccuracy(statDTO.getAccuracy());
            }else{
                stat.setSynopsis(synopsis);
            }
        }
        if (statDTO.getCitationId() != null) {
            Citation citation = citationRepository.findById(statDTO.getCitationId())
                .orElseThrow(() -> new RuntimeException("Citation non trouvé"));
            existingStat = statRepository.findByUserAndCitation(user, citation);
            if (existingStat.isPresent() && existingStat.get().getSpeed() <= statDTO.getSpeed()) {
                existingStat.get().setSpeed(statDTO.getSpeed());
                existingStat.get().setAccuracy(statDTO.getAccuracy());
            }else{
                stat.setCitation(citation);
            }
        }
        if (statDTO.getChallengeId() != null) {
            Challenge challenge = challengeRepository.findById(statDTO.getChallengeId())
                .orElseThrow(() -> new RuntimeException("Challenge non trouvé"));
            stat.setChallenge(challenge);
        }

        user.setSpeed(user.getSpeed() + statDTO.getSpeed());
        user.setCount(user.getCount() + 1);
        user.setAccuracy(user.getAccuracy() + statDTO.getAccuracy());
        if (existingStat.isEmpty()) {
            user.setProgress((user.getCountS()+1) * 100 / (cc + sc));
            user.setCountS(user.getCountS() + 1);
            statRepository.save(stat);
            userRepository.save(user);
            return ResponseEntity.ok(stat);
        }else{
            statRepository.save(existingStat.get());
            userRepository.save(user);
            return ResponseEntity.ok(existingStat.get());
        }
    }
    

    // Mettre à jour une statistique existante
    @PutMapping("/upadate/{id}")
    public ResponseEntity<Stat> updateStat(@PathVariable Long id, @RequestBody Stat statDetails) {
        return statRepository.findById(id)
                .map(stat -> {
                    stat.setTries(statDetails.getTries());
                    stat.setAccuracy(statDetails.getAccuracy());
                    stat.setSpeed(statDetails.getSpeed());
                    stat.setUser(statDetails.getUser());  // Mettre à jour l'utilisateur associé
                    stat.setChallenge(statDetails.getChallenge());  // Mettre à jour le challenge associé
                    Stat updatedStat = statRepository.save(stat);
                    return ResponseEntity.ok().body(updatedStat);
                }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une statistique par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStat(@PathVariable Long id) {
        return statRepository.findById(id)
                .map(stat -> {
                    statRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<User> deleteStatsByUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        user.setAccuracy(0);
        user.setCountS(0);
        user.setCount(0);
        user.setSpeed(0);
        user.setProgress(0);
        statRepository.deleteByUser(user);
        return ResponseEntity.ok(user);
    }
}
