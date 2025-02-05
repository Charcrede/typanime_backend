// package com.typanime.typanime_backend;

// import com.typanime.typanime_backend.security.JwtAuthenticationFilter;
// import com.typanime.typanime_backend.security.JwtTokenProvider;
// import com.typanime.typanime_backend.service.CustomUserDetailsService;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Lazy;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import jakarta.servlet.http.HttpServletResponse;

// import java.util.List;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     private final JwtTokenProvider jwtTokenProvider;
//     private final CustomUserDetailsService userDetailsService;

//     public SecurityConfig(@Lazy JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService) {
//         this.jwtTokenProvider = jwtTokenProvider;
//         this.userDetailsService = userDetailsService;
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider);

//         http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                 .csrf(csrf -> csrf.disable())
//                 .authorizeHttpRequests(authz -> authz
//                 // Routes publiques
//                 .requestMatchers("/api/synopsis/all", "/api/citations/all", "/api/stats/all", "/api/challenges/all", "/api/auth/register", "/api/auth/login/", "/api/citations/{id}", "/api/synopsis/{id}", "/api/synopsis/all").permitAll()
//                 // Routes protégées par le rôle ADMIN
//                 // // Routes accessibles aux utilisateurs ayant les rôles USER ou ADMIN
//                 .requestMatchers("/api/stats/create", "/api/stats/update", "/api/auth/user").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                 .requestMatchers("/api/challenges/create", "/api/challenges/update").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")


//                 .requestMatchers("/api/synopsis/create", "/api/synopsis/update/**", "/api/synopsis/delete/**").hasAuthority("ROLE_ADMIN")
//                 .requestMatchers("/api/citations/create", "/api/citations/update/**", "/api/citations/delete/**").hasAuthority("ROLE_ADMIN")
//                 .requestMatchers("/api/stats/delete/**").hasAuthority("ROLE_ADMIN")
//                 .requestMatchers("/api/challenges/delete/**").hasAuthority("ROLE_ADMIN")
                
//                 // Toute autre requête nécessite une authentification
//                 .anyRequest().authenticated()
//                 )
//                 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                 .exceptionHandling(exceptionHandling -> exceptionHandling
//                 .authenticationEntryPoint((request, response, authException)
//                         -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
//                 .accessDeniedHandler((request, response, accessDeniedException)
//                         -> response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"))
//                 );

//         return http.build();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public UserDetailsService userDetailsService() {
//         return userDetailsService;
//     }

//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration configuration = new CorsConfiguration();
//         configuration.setAllowedOrigins(List.of("http://localhost:3000"));
//         configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//         configuration.setAllowedHeaders(List.of("*"));
//         configuration.setAllowCredentials(true);

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", configuration);
//         return source;
//     }
// }



package com.typanime.typanime_backend;

import com.typanime.typanime_backend.security.JwtAuthenticationFilter;
import com.typanime.typanime_backend.security.JwtTokenProvider;
import com.typanime.typanime_backend.service.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(@Lazy JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider);

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                // Routes publiques
                .requestMatchers("/api/synopsis/all", "/api/citations/all", "/api/stats/all", "/api/challenges/all",
                                 "/api/auth/register", "/api/auth/login", "/api/citations/{id}", "/api/synopsis/{id}", "/api/synopsis/all")
                .permitAll()
                
                // Routes accessibles aux utilisateurs connectés
                // .requestMatchers("/api/stats/create/", "/api/stats/update").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                // .requestMatchers("/api/challenges/create", "/api/challenges/update").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                
                // Routes protégées par le rôle ADMIN
                .requestMatchers("/api/synopsis/create", "/api/synopsis/update/**", "/api/synopsis/delete/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/citations/create", "/api/citations/update/**", "/api/citations/delete/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/stats/delete/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/challenges/delete/**").hasAuthority("ROLE_ADMIN")
                
                // Toute autre requête nécessite une authentification
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint((request, response, authException) ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                .accessDeniedHandler((request, response, accessDeniedException) ->
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"))
            )
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout") // Définition de l'URL de logout
                .addLogoutHandler(customLogoutHandler())
                .logoutSuccessHandler(logoutSuccessHandler())
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Handler personnalisé pour la gestion du logout
     */
    @Bean
    public LogoutHandler customLogoutHandler() {
        return (request, response, authentication) -> {
            // Vous pouvez ajouter ici la suppression du token du côté serveur si nécessaire
            System.out.println("Utilisateur déconnecté !");
        };
    }

    /**
     * Handler de succès du logout qui renvoie une réponse HTTP 200
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Déconnexion réussie\"}");
            response.getWriter().flush();
        };
    }
}
