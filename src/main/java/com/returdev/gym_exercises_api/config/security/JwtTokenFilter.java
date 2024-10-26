package com.returdev.gym_exercises_api.config.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.returdev.gym_exercises_api.manager.security.JwtManager;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;


/**
 * Filter responsible for validating JWT tokens in each HTTP request.
 * <p>
 * This filter intercepts each incoming HTTP request, checks for a JWT in the "Authorization" header,
 * and validates it using {@link JwtManager}. If the token is valid, it extracts the user's role
 * and sets an authentication object in the security context. If the token is invalid or expired,
 * the filter handles the exception and returns an appropriate response.
 */
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final HandlerExceptionResolver handlerExceptionResolver;

    /**
     * Intercepts and filters HTTP requests to validate the JWT token.
     * <p>
     * This method checks the "Authorization" header of the request for a JWT token.
     * If the token exists, it is validated using {@link JwtManager}. Upon successful
     * validation, the user's role is extracted and added to the security context.
     * If the token is invalid or expired, it catches {@link JWTVerificationException}
     * and delegates the exception handling to {@link HandlerExceptionResolver}.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to continue processing the request
     * @throws ServletException if there is a problem with the filtering process
     * @throws IOException if an input or output exception occurs
     */
    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (jwtToken != null) {
                // Validate the JWT token
                DecodedJWT decodedJWT = jwtManager.validateToken(jwtToken);
                String role = jwtManager.getTokenRole(decodedJWT);
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_".concat(role));

                // Set authentication in the security context
                Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, List.of(grantedAuthority));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // Continue with the next filter in the chain
            filterChain.doFilter(request, response);

        } catch (JWTVerificationException ex) {
            // Handle JWT verification failure
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }

    }
    
}

