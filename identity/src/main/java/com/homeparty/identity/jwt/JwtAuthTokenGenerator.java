package com.homeparty.identity.jwt;

import com.homeparty.identity.domain.aggregates.authtoken.AuthToken;
import com.homeparty.identity.domain.models.AuthTokenGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@RequiredArgsConstructor
@Component
public class JwtAuthTokenGenerator implements AuthTokenGenerator {

    private final JwtAuthTokenConfig config;

    @Override
    public AuthToken generate(String id) {

        Key key = Keys.hmacShaKeyFor(config.getSecretKeyByte());

        long now = Timestamp.valueOf(LocalDateTime.now()).getTime();
        long accessTokenExpiredTime = now + config.getAccessTokenExpirationTime();
        long refreshTokenExpiredTime = now + config.getRefreshTokenExpirationTime();

        Map<String, Object> accessTokenClaims = new HashMap<>();
        accessTokenClaims.put(config.getTokenType(), config.getAccessTokenType());

        String accessToken = Jwts.builder()
                .setClaims(accessTokenClaims)
                .setSubject(id)
                .setIssuer(config.getIssuer())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(accessTokenExpiredTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        Map<String, Object> refreshTokenClaims = new HashMap<>();
        accessTokenClaims.put(config.getTokenType(), config.getRefreshTokenType());

        String refreshToken = Jwts.builder()
                .setClaims(refreshTokenClaims)
                .setSubject(id)
                .setIssuer(config.getIssuer())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(refreshTokenExpiredTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new AuthToken(
                accessToken,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(accessTokenExpiredTime), TimeZone.getDefault().toZoneId()),
                refreshToken,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(refreshTokenExpiredTime), TimeZone.getDefault().toZoneId())
        );
    }
}
