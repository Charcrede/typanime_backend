package com.typanime.typanime_backend.repository;

import com.typanime.typanime_backend.model.Synopsis;
import com.typanime.typanime_backend.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SynopsisRepository extends JpaRepository<Synopsis, Long> {
    Optional<Synopsis> findById(Long Id);
    
    @Query("SELECT COUNT(s) FROM Synopsis s")
    long countAllSynopsis();

}
