package com.returdev.gym_exercises_api.config.security;

import com.returdev.gym_exercises_api.manager.security.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Configuration class for Spring Security settings.
 *
 * <p>
 * This class configures the security filters, authentication mechanisms,
 * and session management for the application.
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // Manager for handling JWT token operations
    private final JwtManager jwtManager;

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param httpSecurity the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Disable CSRF protection and HTTP basic authentication
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // Set session management to stateless (no session stored on the server)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Add custom JWT token filter before the basic authentication filter
                .addFilterBefore(new JwtTokenFilter(jwtManager), BasicAuthenticationFilter.class)
                .build();
    }

    /**
     * Provides an AuthenticationManager bean.
     *
     * @param authenticationConfiguration the AuthenticationConfiguration to retrieve the manager
     * @return the AuthenticationManager instance
     * @throws Exception if an error occurs while getting the manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Retrieve and return the authentication manager from the configuration
        return authenticationConfiguration.getAuthenticationManager();
    }

}

