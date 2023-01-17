package com.homeparty.api.domain.identity;

import com.homeparty.api.exception.ApiException;
import com.homeparty.api.exception.ApiExceptionCode;
import com.homeparty.identity.domain.aggregates.identity.SocialProvider;
import com.homeparty.identity.domain.aggregates.identity.SocialProviderType;
import com.homeparty.identity.domain.models.SocialProviderFetcher;

import java.util.EnumMap;

public class CompositeSocialProviderFetcher implements SocialProviderFetcher {

    private final EnumMap<SocialProviderType, SocialProviderFetcher> fetchers = new EnumMap<>(SocialProviderType.class);

    @Override
    public SocialProvider fetch(SocialProviderType providerType, String providerToken) {
        if (fetchers.get(providerType) != null) {
            return fetchers.get(providerType).fetch(providerType, providerToken);
        }
        throw new ApiException(ApiExceptionCode.FAIL_TO_LOGIN);
    }

    public void addFetcher(SocialProviderType providerType, SocialProviderFetcher fetcher) {
        this.fetchers.put(providerType, fetcher);
    }
}
