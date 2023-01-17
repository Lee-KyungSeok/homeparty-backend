package com.homeparty.identity.domain.models;

import com.homeparty.identity.domain.aggregates.identity.SocialProvider;
import com.homeparty.identity.domain.aggregates.identity.SocialProviderType;

public interface SocialProviderFetcher {
    SocialProvider fetch(SocialProviderType providerType, String providerToken);
}
