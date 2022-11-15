package identity.jwt;

import io.jsonwebtoken.io.Decoders;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtAuthTokenConfig {

    private String secretKey;

    private byte[] secretKeyByte;

    public static final String ISSUER = "accounts.homeparty.com";

    public static final Long ACCESS_TOKEN_EXPIRATION_TIME = 24 * 3600 * 1000L; // 1 days

    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 14 * 24 * 3600 * 1000L; // 2 weeks

    public JwtAuthTokenConfig(@Value("${homeparty.jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
        this.secretKeyByte = Decoders.BASE64.decode(secretKey);
    }
}
