package com.typanime.typanime_backend.controller;

import com.typanime.typanime_backend.model.Stat;
import com.typanime.typanime_backend.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatController {

    @Autowired
    private StatRepository statRepository;

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

    // Créer une nouvelle statistique
    @PostMapping
    public Stat createStat(@RequestBody Stat stat) {
        return statRepository.save(stat);
    }

    // Mettre à jour une statistique existante
    @PutMapping("/{id}")
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
}
