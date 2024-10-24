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
import org.springframework.web.servlet.HandlerExceptionResolver;


/**
 * Configuration class for security settings in the application.
 * <p>
 * This class configures web security settings, including JWT-based authentication,
 * exception handling, and method security. It utilizes Spring Security to define
 * a filter chain that secures endpoints with JWT tokens.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtManager jwtManager;
    private final HandlerExceptionResolver handlerExceptionResolver;

    /**
     * Configures the security filter chain for the application.
     * <p>
     * This method sets up the security configurations such as disabling CSRF protection,
     * basic HTTP authentication, and session management. It adds a custom JWT filter
     * to the filter chain and configures exception handling for authentication
     * and access-denied scenarios.
     *
     * @param httpSecurity the HttpSecurity object to configure security settings
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        new JwtTokenFilter(jwtManager, handlerExceptionResolver),
                        BasicAuthenticationFilter.class
                )
//                .exceptionHandling(exceptionHandling ->
//                        exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint())
//                                .accessDeniedHandler(customAccessDeniedHandler())
//                )
                .build();
    }

    /**
     * Provides the authentication manager for handling authentication processes.
     * <p>
     * This bean is necessary for performing authentication operations in the application.
     *
     * @param authenticationConfiguration the configuration for authentication management
     * @return the AuthenticationManager instance
     * @throws Exception if an error occurs during the retrieval of the authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    /**
//     * Customizes the entry point for handling authentication failures.
//     * <p>
//     * This bean is responsible for resolving exceptions that occur when authentication fails.
//     * It delegates the resolution to a handler defined in the handlerExceptionResolver.
//     *
//     * @return a custom AuthenticationEntryPoint instance
//     */
//    @Bean
//    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
//        return (request, response, authException) -> {
//            handlerExceptionResolver.resolveException(request, response, null, authException);
//        };
//    }
//
//    /**
//     * Customizes the handler for access-denied scenarios.
//     * <p>
//     * This bean is responsible for resolving exceptions when access to a resource is denied.
//     * It creates a new {@link InsufficientPermissionsException} to be handled appropriately.
//     *
//     * @return a custom AccessDeniedHandler instance
//     */
//    @Bean
//    public AccessDeniedHandler customAccessDeniedHandler() {
//        return (request, response, accessDeniedException) -> {
//            handlerExceptionResolver.resolveException(request, response, null, new InsufficientPermissionsException());
//        };
//    }
}


