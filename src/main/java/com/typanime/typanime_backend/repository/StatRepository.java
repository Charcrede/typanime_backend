package com.typanime.typanime_backend.repository;

import com.typanime.typanime_backend.model.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {
    // Des méthodes personnalisées peuvent être ajoutées si nécessaire
}
