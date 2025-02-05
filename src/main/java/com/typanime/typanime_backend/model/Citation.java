package com.typanime.typanime_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "citations")
public class Citation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String persoName;
    private String animeName;
    private String url;

    @Column(columnDefinition = "TEXT")
    private String text;


    public Citation() {
       
    }

    public Citation(String animeName, String persoName, String text, String url) {
        this.animeName = animeName;
        this.persoName = persoName;
        this.text = text;
        this.url = url;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public String getPersoName() {
        return persoName;
    }

    public void setPersoName(String persoName) {
        this.persoName = persoName;
    }

    public String getAnimeName() {
        return animeName;
    }

    public void setAnimeName(String animeName) {
        this.animeName = animeName;
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
