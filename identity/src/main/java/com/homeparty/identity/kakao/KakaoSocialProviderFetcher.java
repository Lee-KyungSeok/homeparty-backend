package com.homeparty.identity.kakao;

import com.homeparty.identity.domain.aggregates.identity.SocialProvider;
import com.homeparty.identity.domain.aggregates.identity.SocialProviderType;
import com.homeparty.identity.domain.models.SocialProviderFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component(value = "kakao_provider_fetcher")
public class KakaoSocialProviderFetcher implements SocialProviderFetcher {

    private final KakaoClient kakaoClient;

    @Override
    public SocialProvider fetch(SocialProviderType providerType, String providerToken) {
        // Todo: 에러 발생시 서버 에러로 나오므로 exception 처리를 따로 해주자.
        var kakaoUser = this.kakaoClient.getMe(providerToken);
        return new SocialProvider(
                providerType,
                kakaoUser.getId(),
                kakaoUser.getProperties().getNickname(),
                kakaoUser.getKakaoAccount().getEmail(),
                kakaoUser.getProperties().getProfileImage()
        );
    }
}
