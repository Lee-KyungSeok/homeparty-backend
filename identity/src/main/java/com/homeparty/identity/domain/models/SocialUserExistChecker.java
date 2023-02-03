package com.homeparty.identity.domain.models;

import com.homeparty.identity.domain.aggregates.identity.Identity;
import com.homeparty.identity.domain.aggregates.identity.IdentityRepository;
import com.homeparty.identity.domain.aggregates.identity.SocialProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialUserExistChecker {

    private final SocialProviderFetcher socialProviderFetcher;
    private final IdentityRepository identityRepository;

    public boolean check(SocialProviderType providerType, String providerToken) {
        var socialProvider = socialProviderFetcher.fetch(providerType, providerToken);
        Optional<Identity> identity = identityRepository.findByProviderSocialId(socialProvider.getSocialId());
        return identity.isPresent();
    }
}
