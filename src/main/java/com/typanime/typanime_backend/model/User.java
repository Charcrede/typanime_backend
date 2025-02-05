package com.typanime.typanime_backend.model;

import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.ROLE_USER;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true, columnDefinition = "INT DEFAULT 0")
    private int speed;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Column(nullable = true, columnDefinition = "INT DEFAULT 0")
    private int accuracy;

    @Column(nullable = true, columnDefinition = "INT DEFAULT 0")
    private int countS;

    public int getCountS() {
        return countS;
    }

    public void setCountS(int countS) {
        this.countS = countS;
    }

    @Column(nullable = true, columnDefinition = "INT DEFAULT 0")
    private long progress;

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    @Column(nullable = true, columnDefinition = "INT DEFAULT 0")
    private int count;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    // Constructeur par défaut requis pour JPA
    public User() {}

    // Constructeur personnalisé
    public User(String email, String password, String username, int speed, int accuracy, int count, long progress, int countS) {
        this.email = email;
        this.password = password; // Pensez à hacher le mot de passe ici
        this.username = username;
        this.role = Role.ROLE_USER;
        this.speed = speed;
        this.accuracy = accuracy;
        this.progress = progress;
        this.count = count;
        this.countS = countS;
    }

    // Getters et Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password; // Pensez à hacher le mot de passe ici
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Méthode pour vérifier si l'utilisateur est administrateur
    public boolean isAdmin() {
        return role == Role.ROLE_ADMIN;
    }

    // Implémentations de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Tu peux ajuster selon tes règles métier
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Tu peux ajuster selon tes règles métier
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Tu peux ajuster selon tes règles métier
    }

    @Override
    public boolean isEnabled() {
        return true;  // Tu peux ajuster selon tes règles métier
    }
}

 enum Role {
    ROLE_USER,
    ROLE_ADMIN;
}
