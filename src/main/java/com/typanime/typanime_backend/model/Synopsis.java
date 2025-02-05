package com.typanime.typanime_backend.model;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "synopsis")
public class Synopsis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String anime;
    private String url;

    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToMany(mappedBy = "synopsis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Challenge> challenges;

    // Constructeur par défaut requis pour JPA
    public Synopsis() {
    }

    // Constructeur sans 'id' car il est généré automatiquement
    public Synopsis(String anime, String text, String url) {
        this.anime = anime;
        this.text = text;
        this.url = url;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
