package com.PetShere.configuration.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtSecretKey {

    private final String secretKey;

    public JwtSecretKey(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }
}
