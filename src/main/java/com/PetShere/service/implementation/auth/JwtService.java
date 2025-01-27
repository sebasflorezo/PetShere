package com.PetShere.service.implementation.auth;

import com.PetShere.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.PetShere.configuration.security.JwtSecretKey;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtSecretKey jwtSecretKey;

    public String getToken(UserDetails user) {
        return generateToken(new HashMap<>(), user);
    }

    public boolean isTokenValid(String token) {
        if (token == null) {
            log.warn(Constants.JWT_NOT_FOUND_MSG);
            return false;
        }

        if (this.isAudienceCorrect(token)) {
            log.warn(Constants.JWT_INVALID_AUDIENCE);
            return false;
        }

        if (this.isEmailValid(token)) {
            log.warn(Constants.JWT_EMAIL_NOT_FOUND_MSG);
            return false;
        }

        if (this.isTokenExpired(token)) {
            log.warn(Constants.JWT_EXPIRED_MSG);
            return false;
        }

        return true;
    }

    private boolean isEmailValid(String token) {
        return this.getUsernameFromToken(token) == null;
    }

    private boolean isTokenExpired(String token) {
        return this.getExpiration(token).before(new Date());
    }

    private boolean isAudienceCorrect(String token) {
        return this.getAllClaims(token).getAudience().equals(Constants.JWT_AUDIENCE);
    }

    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            return claimsResolver.apply(this.getAllClaims(token));
        } catch (Exception e) {
            return null;
        }
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSecretKey())
                .setAllowedClockSkewSeconds(Constants.TOKEN_REFRESH_TIME)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(HashMap<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setAudience(Constants.JWT_AUDIENCE)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date getExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
