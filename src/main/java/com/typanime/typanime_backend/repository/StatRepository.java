package com.typanime.typanime_backend.repository;

import com.typanime.typanime_backend.model.Citation;
import com.typanime.typanime_backend.model.Stat;
import com.typanime.typanime_backend.model.Synopsis;
import com.typanime.typanime_backend.model.User;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {
    // Des méthodes personnalisées peuvent être ajoutées si nécessaire
    Optional<Stat> findByUserAndSynopsis(User user, Synopsis synopsis);

    Optional<Stat> findByUserAndCitation(User user, Citation citation);
    List<Stat> findTop10ByUserOrderByIdDesc(User user);

    @Modifying
    @Transactional
    void deleteByUser(User user);
}
