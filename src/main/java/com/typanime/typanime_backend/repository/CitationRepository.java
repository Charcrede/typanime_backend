package com.typanime.typanime_backend.repository;

import com.typanime.typanime_backend.model.Citation;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CitationRepository extends JpaRepository<Citation, Long> {
     Optional<Citation> findById(Long Id);

     @Query("SELECT COUNT(s) FROM Citation s")
     long countAllCitation();

}
