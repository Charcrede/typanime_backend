package com.typanime.typanime_backend.model;

import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    // Constructeur par défaut requis pour JPA
    public User() {}

    // Constructeur personnalisé
    public User(String email, String password, String username) {
        this.email = email;
        this.password = password; // Pensez à hacher le mot de passe ici
        this.username = username;
        this.role = Role.ROLE_USER;
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
