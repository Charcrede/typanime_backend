package com.typanime.typanime_backend.controller;

import com.typanime.typanime_backend.model.Synopsis;
import com.typanime.typanime_backend.repository.SynopsisRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class SynopsisController {

    @Autowired
    private SynopsisRepository synopsisRepository;

    // Endpoint pour récupérer les synopsis avec pagination
    @GetMapping("/synopsis/all")
    public Page<Synopsis> getSynopses(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return synopsisRepository.findAll(pageable);
    }

    // Créer un nouveau synopsis
    @PostMapping("/synopsis/create")
    public Synopsis createSynopsis(@RequestBody Synopsis synopsis) {
        return synopsisRepository.save(synopsis);
    }

    // Récupérer un synopsis par ID
    @GetMapping("/synopsis/{id}")
    public Synopsis getSynopsisById(@PathVariable Long id) {
        return synopsisRepository.findById(id)
                .orElseThrow();
    }

    // Mettre à jour un synopsis
    @PutMapping("/synopsis/update/{id}")
    public Synopsis updateSynopsis(@PathVariable Long id, @RequestBody Synopsis synopsisDetails) {
        Synopsis synopsis = synopsisRepository.findById(id)
                .orElseThrow();

        synopsis.setAnime(synopsisDetails.getAnime());
        synopsis.setText(synopsisDetails.getText());
        synopsis.setUrl(synopsisDetails.getUrl());

        return synopsisRepository.save(synopsis);
    }

    // Supprimer un synopsis
    @DeleteMapping("/synopsis/delete/{id}")
    public ResponseEntity<?> deleteSynopsis(@PathVariable Long id) {
        Synopsis synopsis = synopsisRepository.findById(id)
                .orElseThrow();

        synopsisRepository.delete(synopsis);
        return ResponseEntity.ok().build();
    }
}
