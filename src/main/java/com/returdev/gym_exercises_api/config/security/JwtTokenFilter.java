package com.returdev.gym_exercises_api.config.security;

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

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null){
            jwtToken = jwtToken.substring(7);

            DecodedJWT decodedJWT = jwtManager.validateToken(jwtToken);
            String role = jwtManager.getTokenRole(decodedJWT);

            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_".concat(role));

            Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, List.of(grantedAuthority));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

            filterChain.doFilter(request, response);

    }
}
