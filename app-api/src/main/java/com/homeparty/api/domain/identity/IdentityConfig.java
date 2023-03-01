package com.homeparty.api.domain.identity;

import com.homeparty.identity.domain.aggregates.identity.SocialProviderType;
import com.homeparty.identity.domain.models.SocialProviderFetcher;
import com.homeparty.identity.jwt.JwtAuthTokenConfig;
import com.homeparty.identity.kakao.KakaoSocialProviderFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@RequiredArgsConstructor
@Configuration
public class IdentityConfig {

    @Value("${homeparty.jwt.secret}")
    String jwtSecretKey;

    @Bean
    public JwtAuthTokenConfig jwtAuthTokenConfig() {
        return new JwtAuthTokenConfig(jwtSecretKey);
    }

    @Bean
    @Primary
    public SocialProviderFetcher compositeSocialProviderFetcher(
            KakaoSocialProviderFetcher kakaoFetcher
    ) {
        CompositeSocialProviderFetcher client = new CompositeSocialProviderFetcher();
        client.addFetcher(SocialProviderType.KAKAO, kakaoFetcher);
        return client;
    }
}
