package identity.kakao;

import identity.domain.aggregates.identity.SocialProvider;
import identity.domain.aggregates.identity.SocialProviderType;
import identity.domain.models.SocialProviderFetcher;

public class KakaoSocialProviderFetcher implements SocialProviderFetcher {

    @Override
    public SocialProvider fetch(SocialProviderType providerType, String providerToken) {
        return null;
    }
}
