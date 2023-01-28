package com.homeparty.api.readmodel.view;

import com.homeparty.identity.domain.aggregates.identity.SocialProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class IdentityView {
    private UUID id;
    private String nickname;
    private String profileImageUrl;
    private String email;
    private SocialProviderType providerType;
}
