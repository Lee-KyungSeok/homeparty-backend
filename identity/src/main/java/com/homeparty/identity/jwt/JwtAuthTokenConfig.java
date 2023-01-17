package com.homeparty.identity.jwt;

import io.jsonwebtoken.io.Decoders;
import lombok.Getter;

@Getter
public class JwtAuthTokenConfig {

    private final String secretKey;

    private final byte[] secretKeyByte;

    private final String issuer;
    private final String tokenType;
    private final String accessTokenType;
    private final String refreshTokenType;
    private final Long accessTokenExpirationTime;
    private final Long refreshTokenExpirationTime;

    public JwtAuthTokenConfig(String secretKey) {
        this.secretKey = secretKey;

        this.secretKeyByte = Decoders.BASE64.decode(secretKey);
        this.issuer = "accounts.homeparty.com";
        this.tokenType = "tokenType";
        this.accessTokenType = "access";
        this.refreshTokenType = "refresh";
        this.accessTokenExpirationTime = 24 * 3600 * 1000L; // 1 days
        this.refreshTokenExpirationTime = 14 * 24 * 3600 * 1000L; // 2 weeks
    }
}
