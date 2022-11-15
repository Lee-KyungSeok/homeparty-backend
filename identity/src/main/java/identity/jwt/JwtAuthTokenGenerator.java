package identity.jwt;

import identity.domain.aggregates.authtoken.AuthToken;
import identity.domain.models.AuthTokenGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
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

        long now = Timestamp.valueOf(LocalDateTime.now()).getTime();
        long accessTokenExpiredTime = now + JwtAuthTokenConfig.ACCESS_TOKEN_EXPIRATION_TIME;
        long refreshTokenExpiredTime = now + JwtAuthTokenConfig.REFRESH_TOKEN_EXPIRATION_TIME;

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);

        Key key = Keys.hmacShaKeyFor(config.getSecretKeyByte());

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(id)
                .setIssuer(JwtAuthTokenConfig.ISSUER)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(accessTokenExpiredTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(id)
                .setIssuer(JwtAuthTokenConfig.ISSUER)
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
