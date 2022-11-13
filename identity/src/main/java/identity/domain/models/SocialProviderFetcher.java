package identity.domain.models;

import identity.domain.aggregates.identity.SocialProvider;
import identity.domain.aggregates.identity.SocialProviderType;

public interface SocialProviderFetcher {
    SocialProvider fetch(SocialProviderType providerType, String providerToken);
}
