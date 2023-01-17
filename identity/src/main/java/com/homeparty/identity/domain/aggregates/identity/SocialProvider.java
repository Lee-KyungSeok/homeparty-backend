package com.homeparty.identity.domain.aggregates.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@ToString
@Slf4j
public class SocialProvider {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "social_provider_type", columnDefinition = "varchar(50)")
    private SocialProviderType providerType;

    @Column(name = "social_id", columnDefinition = "VARCHAR(255)")
    private String socialId;

    @Column(name = "social_nickname", columnDefinition = "VARCHAR(255)")
    private String socialNickname;

    @Column(name = "social_email", columnDefinition = "VARCHAR(255)")
    private String socialEmail;

    @Column(name = "social_image_url", columnDefinition = "VARCHAR(510)")
    private String socialImageUrl;

    protected SocialProvider() {
    }

}
