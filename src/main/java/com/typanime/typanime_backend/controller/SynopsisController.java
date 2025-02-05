package com.typanime.typanime_backend.controller;

import com.typanime.typanime_backend.model.Citation;
import com.typanime.typanime_backend.model.Synopsis;
import com.typanime.typanime_backend.repository.SynopsisRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class SynopsisController {

    @Autowired
    private SynopsisRepository synopsisRepository;

    // Endpoint pour récupérer les synopsis avec pagination
    @GetMapping("/synopsis/all")
    public PagedModel<EntityModel<Synopsis>> getSynopsis(Pageable pageable,
                                                          PagedResourcesAssembler<Synopsis> assembler) {
        return assembler.toModel(synopsisRepository.findAll(pageable));
    }

    @GetMapping("/synopsis/allS")
    public List<Synopsis> getAll() {
        return synopsisRepository.findAll();
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
