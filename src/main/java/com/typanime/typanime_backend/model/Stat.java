package com.typanime.typanime_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stats")
@Getter
@Setter
@NoArgsConstructor
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer tries;  // Nombre d'essais (entier)
    private int accuracy; // Précision (décimal)
    private int speed; // Vitesse (MPM)

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "citation_id", nullable = true)
    private Citation citation;

    @ManyToOne
    @JoinColumn(name = "synopsis_id", nullable = true)
    private Synopsis synopsis;

    @ManyToOne
    @JoinColumn(name = "challenge_id", nullable = true)
    @JsonIgnore
    private Challenge challenge;

    // Constructeur avec paramètres
    public Stat(Integer tries, int accuracy, int speed, Challenge challenge, User user, Citation citation, Synopsis synopsis) {
        this.tries = tries;
        this.accuracy = accuracy;
        this.speed = speed;
        this.challenge = challenge;
        this.user = user;
        this.citation = citation;
        this.synopsis = synopsis;
    }
}
