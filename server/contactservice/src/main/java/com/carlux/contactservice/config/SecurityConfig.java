package com.carlux.contactservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for API-based communication
            .cors().and() // Enable CORS support
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/contacts").permitAll() // Allow public access to contact API
                .anyRequest().authenticated() // Secure other endpoints
            )
            .formLogin().disable(); // Disable form login for API calls

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Match all endpoints
                    .allowedOrigins("http://localhost:5173") // Allow requests from your frontend
                    .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify HTTP methods
                    .allowedHeaders("*") // Allow all headers
                    .allowCredentials(true); // Allow credentials if needed
            }
        };
    }
}