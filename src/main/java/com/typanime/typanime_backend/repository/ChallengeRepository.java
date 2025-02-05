package com.typanime.typanime_backend.repository;

import com.typanime.typanime_backend.model.Challenge;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    // Tu peux ajouter des méthodes personnalisées ici si nécessaire
     Optional<Challenge> findById(Long Id);
}
