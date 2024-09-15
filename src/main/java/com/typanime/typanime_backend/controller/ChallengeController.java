package com.typanime.typanime_backend.controller;

import com.typanime.typanime_backend.model.Challenge;
import com.typanime.typanime_backend.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    @Autowired
    private ChallengeRepository challengeRepository;

    // Récupérer tous les challenges avec pagination
    @GetMapping
    public Page<Challenge> getChallenges(@RequestParam(defaultValue = "0") int page, 
                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return challengeRepository.findAll(pageable);
    }

    // Récupérer un challenge par ID
    @GetMapping("/{id}")
    public ResponseEntity<Challenge> getChallengeById(@PathVariable Long id) {
        Optional<Challenge> challenge = challengeRepository.findById(id);
        if (challenge.isPresent()) {
            return ResponseEntity.ok(challenge.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Créer un nouveau challenge
    @PostMapping
    public Challenge createChallenge(@RequestBody Challenge challenge) {
        return challengeRepository.save(challenge);
    }

    // Mettre à jour un challenge
    @PutMapping("/{id}")
    public ResponseEntity<Challenge> updateChallenge(@PathVariable Long id, @RequestBody Challenge challengeDetails) {
        Optional<Challenge> optionalChallenge = challengeRepository.findById(id);
        if (optionalChallenge.isPresent()) {
            Challenge challenge = optionalChallenge.get();
            challenge.setLiveTime(challengeDetails.getLiveTime());
            challenge.setStats(challengeDetails.getStatList());
            challenge.setCitation(challengeDetails.getCitation());
            challenge.setSynopsis(challengeDetails.getSynopsis());
            Challenge updatedChallenge = challengeRepository.save(challenge);
            return ResponseEntity.ok(updatedChallenge);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un challenge
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable Long id) {
        if (challengeRepository.existsById(id)) {
            challengeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
