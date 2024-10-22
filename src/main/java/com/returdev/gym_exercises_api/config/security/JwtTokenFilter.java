package com.returdev.gym_exercises_api.config.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.returdev.gym_exercises_api.dto.response.error.ErrorResponseDTO;
import com.returdev.gym_exercises_api.manager.security.JwtManager;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Filter that processes JWT tokens for authentication in incoming HTTP requests.
 *
 * <p>
 * This filter checks for the presence of a JWT in the Authorization header of the request.
 * If a valid token is found, it extracts the user's role and creates an authentication token,
 * which is then set in the SecurityContext for the current request.
 * </p>
 *
 * <p>
 * In case of an invalid token, it generates a JSON response indicating the error and sets
 * the HTTP status to 401 Unauthorized.
 * </p>
 */
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;

    /**
     * Filters the incoming request to check for JWT authentication.
     *
     * @param request The HTTP request object.
     * @param response The HTTP response object.
     * @param filterChain The filter chain to proceed with.
     * @throws ServletException if an error occurs during the filtering process.
     * @throws IOException if an I/O error occurs.
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
                // Remove "Bearer " prefix from the token
                jwtToken = jwtToken.substring(7);
                DecodedJWT decodedJWT = jwtManager.validateToken(jwtToken);
                String role = jwtManager.getTokenRole(decodedJWT);
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_".concat(role));

                // Create authentication object
                Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, List.of(grantedAuthority));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // Continue the filter chain
            filterChain.doFilter(request, response);

        } catch (JWTVerificationException ex) {
            // Handle invalid JWT token
            String json = generateJsonResponse(ex, request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(json);
        }

    }

    /**
     * Generates a JSON response indicating the error related to JWT verification.
     *
     * @param ex The exception thrown during JWT verification.
     * @param requestUri The URI of the request that failed authentication.
     * @return A JSON string representing the error details.
     * @throws JsonProcessingException if an error occurs during JSON serialization.
     */
    private String generateJsonResponse(JWTVerificationException ex, String requestUri) throws JsonProcessingException {
        ProblemDetail problemDetail = ErrorResponseDTO.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setInstance(URI.create(requestUri));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(problemDetail);
    }
}
