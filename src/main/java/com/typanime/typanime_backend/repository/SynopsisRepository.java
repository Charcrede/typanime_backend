package com.typanime.typanime_backend.repository;

import com.typanime.typanime_backend.model.Synopsis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynopsisRepository extends JpaRepository<Synopsis, Long> {
}
