package com.returdev.gym_exercises_api.manager.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.returdev.gym_exercises_api.manager.message.MessageManager;
import com.returdev.gym_exercises_api.model.auth.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Implementation of the {@link JwtManager} interface for handling JSON Web Tokens (JWT).
 *
 * <p>
 * This class provides methods to generate and validate JWTs for user authentication,
 * as well as extracting role information from the token.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class JwtManagerImpl implements JwtManager {

    private final String ROLE_CLAIM_NAME = "role";

    @Value("${spring.jwt.private-key}")
    private String privateKey;

    @Value("${spring.jwt.user-generator}")
    private String issuer;

    @Value("${spring.jwt.token-expiration-millis}")
    private Long tokenExpirationDurationMillis;

    private final MessageManager messageManager;

    /**
     * Generates a new JWT for a user with a specified expiration time and role claim.
     *
     * @return an {@link AuthToken} containing the generated token and its expiration time
     */
    @Override
    public AuthToken generateUserToken() {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        Instant currentTime = Instant.now();
        Instant expirationTime = currentTime.plusMillis(tokenExpirationDurationMillis);
        String token = JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(currentTime)
                .withExpiresAt(expirationTime)
                .withClaim(ROLE_CLAIM_NAME, "USER")
                .sign(algorithm);

        return new AuthToken(
                token,
                expirationTime
        );
    }

    /**
     * Validates a given JWT and returns its decoded form if valid.
     *
     * @param token the JWT to validate
     * @return the decoded JWT if validation is successful
     * @throws JWTVerificationException if the token is invalid or expired
     */
    @Override
    public DecodedJWT validateToken(String token) throws JWTVerificationException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();

            return verifier.verify(token);

        } catch (TokenExpiredException e) {
            throw new JWTVerificationException(
                    messageManager.getMessage("exception.token.expired")
            );
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(
                    messageManager.getMessage("exception.token.invalid")
            );
        }
    }

    /**
     * Extracts the role claim from the decoded JWT.
     *
     * @param decodedJWT the decoded JWT from which to extract the role
     * @return the role associated with the token
     */
    @Override
    public String getTokenRole(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim(ROLE_CLAIM_NAME).asString();
    }
}

