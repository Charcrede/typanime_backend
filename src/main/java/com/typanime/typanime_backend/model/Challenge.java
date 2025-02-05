package com.typanime.typanime_backend.model;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "challenges")
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("speed DESC")
    private List<Stat> statList;

    @ManyToOne
    @JoinColumn(name = "citation_id", nullable = true) 
    private Citation citation;

    @ManyToOne
    @JoinColumn(name = "synopsis_id", nullable = true) 
    private Synopsis synopsis;

    private Long liveTime;  // Utiliser Long au lieu de long pour permettre null

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = true, columnDefinition = "INT DEFAULT 0")
    private int members;

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Stat> getStatList() {
        return statList;
    }

    public void setStatList(List<Stat> statList) {
        this.statList = statList;
    }

    public Citation getCitation() {
        return citation;
    }

    public void setCitation(Citation citation) {
        this.citation = citation;
    }

    public Synopsis getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(Synopsis synopsis) {
        this.synopsis = synopsis;
    }

    public Long getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(Long liveTime) {
        this.liveTime = liveTime;
    }

    public void setStats(List<Stat> statList) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
