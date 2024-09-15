package com.typanime.typanime_backend.service;

import com.typanime.typanime_backend.model.User;
import com.typanime.typanime_backend.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")  // Utiliser une clé secrète depuis un fichier de configuration
    private String SECRET_KEY;

    @Value("${jwt.expiration}")  // Utiliser une valeur d'expiration depuis un fichier de configuration
    private long EXPIRATION_TIME;

    /**
     * Enregistre un nouvel utilisateur en s'assurant que l'email et le nom
     * d'utilisateur sont uniques
     */
    public String registerUser(User user) {
        // Vérifie si l'utilisateur existe déjà par nom d'utilisateur ou email
        User existingUserByUsername = userRepository.findByUsername(user.getUsername());
        User existingUserByEmail = userRepository.findByEmail(user.getEmail());

        if (existingUserByUsername != null) {
            throw new RuntimeException("Nom d'utilisateur déjà pris");
        }

        if (existingUserByEmail != null) {
            throw new RuntimeException("Email déjà utilisé");
        }
         String p = user.getPassword();
        // Hachage du mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        // Retourne le token JWT généré après l'enregistrement
        return authenticate(user.getUsername(), p, user.getEmail());
    }

    /**
     * Authentifie un utilisateur par nom d'utilisateur ou email
     */
    public String authenticate(String username, String password, String email) {
        User user;

        if (username != null && !username.isEmpty()) {
            user = userRepository.findByUsername(username);
        } else if (email != null && !email.isEmpty()) {
            user = userRepository.findByEmail(email);
        } else {
            throw new RuntimeException("Informations d'utilisateur manquantes");
        }
        System.out.println(user);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Identifiants invalides");
        }

        return generateToken(user);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * Génère un token JWT pour un utilisateur donné
     */
    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole()) // Ajout du rôle dans le JWT
                .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(this.getSigningKey(), SignatureAlgorithm.HS256) // Utilise la méthode non dépréciée
            .compact();
    }
}
