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

@Component
@RequiredArgsConstructor
public class JwtManagerImpl implements JwtManager{

    private final String CLAIM_ROLE_NAME = "role";

    @Value("${spring.jwt.private-key}")
    private String privateKey;

    @Value("${spring.jwt.user-generator}")
    private String userGenerator;

    @Value("${spring.jwt.token-expiration-millis}")
    private Long tokenExpirationMillis;

    private final MessageManager messageManager;

    @Override
    public AuthToken generateUserToken() {

        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        Instant currentTime = Instant.now();
        Instant expirationTime = currentTime.plusMillis(tokenExpirationMillis);
        String token = JWT.create().withIssuer(userGenerator)
                .withIssuedAt(currentTime)
                .withExpiresAt(currentTime.plusMillis(tokenExpirationMillis))
                .withClaim(CLAIM_ROLE_NAME, "USER")
                .sign(algorithm);


        return new AuthToken(
                token,
                expirationTime
        );
    }

    @Override
    public DecodedJWT validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();

            return verifier.verify(token);

        } catch (TokenExpiredException e) {
            throw new JWTVerificationException(
                    messageManager.getMessage("exception.token.expired")
            );
        } catch (JWTVerificationException e){
            throw new JWTVerificationException(
                    messageManager.getMessage("exception.token.invalid")
            );
        }
    }

    @Override
    public String getTokenRole(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim(CLAIM_ROLE_NAME).asString();
    }
}
