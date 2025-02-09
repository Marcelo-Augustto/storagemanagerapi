package com.example.storagemanagerapi.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Allow all requests
                )
                .csrf(csrf -> csrf.disable()) // Disable CSRF (for development)
                .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Updated method for Spring Security 6

        return http.build();
    }
}

