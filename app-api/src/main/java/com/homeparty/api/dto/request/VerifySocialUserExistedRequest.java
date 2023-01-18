package com.homeparty.api.dto.request;

import com.homeparty.identity.domain.aggregates.identity.SocialProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VerifySocialUserExistedRequest {
    SocialProviderType providerType;
    String providerToken;
}
