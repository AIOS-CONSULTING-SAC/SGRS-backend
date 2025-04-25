package com.aios.sgrs.config;

import com.aios.sgrs.security.CustomAuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final CustomAuthProvider authProvider;
    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(CustomAuthProvider authProvider, JwtAuthenticationFilter jwtFilter) {
        this.authProvider = authProvider;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // disable CSRF with the new lambda style
                .csrf(csrf -> csrf.disable())

                // stateless session management
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // your custom AuthenticationProvider
                .authenticationProvider(authProvider)

                // authorization rules
                .authorizeHttpRequests(auth -> auth
                        // allow unauthenticated access to /api/usuario/**
                        .requestMatchers("/api/v1/usuarios/login").permitAll()
                        // everything else requires authentication
                        .anyRequest().authenticated()
                )

                // JWT filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12); // Factor de trabajo de 12
    }
}
