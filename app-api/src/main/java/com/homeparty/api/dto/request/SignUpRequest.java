package com.homeparty.api.dto.request;

import com.homeparty.identity.domain.aggregates.identity.SocialProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpRequest {
    private SocialProviderType providerType;
    private String providerToken;
}
