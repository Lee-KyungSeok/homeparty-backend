package com.homeparty.api.dto.request;

import com.homeparty.identity.domain.aggregates.identity.SocialProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidateSocialUserExistedRequest {
    private SocialProviderType providerType;
    private String providerToken;
}
