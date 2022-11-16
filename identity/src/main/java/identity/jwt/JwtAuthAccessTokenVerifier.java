package identity.jwt;

import identity.domain.models.AuthAccessTokenVerifier;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthAccessTokenVerifier implements AuthAccessTokenVerifier {

    private final JwtAuthTokenConfig config;

    @Override
    public Optional<UUID> verify(String accessToken) {
        return verifyToken(accessToken)
                .map(claims -> verifyClaims(claims, accessToken));
    }

    public Optional<Claims> verifyToken(String accessToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(config.getSecretKeyByte())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            return Optional.ofNullable(claims);

        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 Jwt 서명입니다. accessToken: {}", accessToken, e);
            return Optional.empty();
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다. accessToken: {}", accessToken, e);
            return Optional.empty();
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰입니다. accessToken: {}", accessToken, e);
            return Optional.empty();
        } catch (IllegalArgumentException e) {
            log.error("잘못된 토큰입니다. accessToken: {}", accessToken, e);
            return Optional.empty();
        }
    }

    public UUID verifyClaims(Claims claims, String accessToken) {
        String tokenType = claims.get(config.getTokenType(), String.class);
        if (!config.getAccessTokenType().equals(tokenType)) {
            log.error("tokenType 이 유효하지 않습니다. accessToken: {}", accessToken);
            return null;
        }

        try {
            return UUID.fromString(claims.getSubject());
        } catch (IllegalArgumentException e) {
            log.error("subject 가 uuid 가 아닙니다. accessToken: {}", accessToken, e);
            return null;
        }
    }
}
