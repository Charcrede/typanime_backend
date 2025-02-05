package com.typanime.typanime_backend.controller;

import com.typanime.typanime_backend.model.Citation;
import com.typanime.typanime_backend.repository.CitationRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/citations")
public class CitationController {

    @Autowired
    private CitationRepository citationRepository;

    // Endpoint pour récupérer les citations avec pagination
    @GetMapping("/all")
    public PagedModel<EntityModel<Citation>> getCitations(Pageable pageable,
                                                          PagedResourcesAssembler<Citation> assembler) {
        return assembler.toModel(citationRepository.findAll(pageable));
    }

    @GetMapping("/allC")
    public List<Citation> getAll() {
        return citationRepository.findAll();
    }

    // Récupérer une citation par ID
    @GetMapping("/{id}")
    public ResponseEntity<Citation> getCitationById(@PathVariable Long id) {
        return citationRepository.findById(id)
                .map(citation -> ResponseEntity.ok().body(citation))
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer une nouvelle citation
    @PostMapping("/create")
    public Citation createCitation(@RequestBody Citation citation) {
        return citationRepository.save(citation);
    }

    // Mettre à jour une citation existante
    @PutMapping("update/{id}")
    public ResponseEntity<Citation> updateCitation(@PathVariable Long id, @RequestBody Citation citationDetails) {
        return citationRepository.findById(id)
                .map(citation -> {
                    citation.setPersoName(citationDetails.getPersoName());
                    citation.setAnimeName(citationDetails.getAnimeName());
                    citation.setText(citationDetails.getText());
                    citation.setUrl(citationDetails.getUrl());
                    // Mettre à jour les autres champs, si nécessaire
                    // Par exemple : citation.setChallenges(citationDetails.getChallenges());
                    Citation updatedCitation = citationRepository.save(citation);
                    return ResponseEntity.ok().body(updatedCitation);
                }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une citation par ID
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteCitation(@PathVariable Long id) {
        return citationRepository.findById(id)
                .map(citation -> {
                    citationRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
