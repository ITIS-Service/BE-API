package com.itis.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTProvider {

    public String createToken(String authorities, String email) {
        return JWT.create()
                .withSubject(email)
                .withClaim(SecurityConstants.AUTHORITIES_KEY, authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
    }

}
