package com.typanime.typanime_backend.repository;

import com.typanime.typanime_backend.model.Citation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitationRepository extends JpaRepository<Citation, Long> {
}
