package identity.kakao;

import identity.domain.aggregates.identity.SocialProvider;
import identity.domain.aggregates.identity.SocialProviderType;
import identity.domain.models.SocialProviderFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class KakaoSocialProviderFetcher implements SocialProviderFetcher {

    private final KakaoClient kakaoClient;

    @Override
    public SocialProvider fetch(SocialProviderType providerType, String providerToken) {
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
