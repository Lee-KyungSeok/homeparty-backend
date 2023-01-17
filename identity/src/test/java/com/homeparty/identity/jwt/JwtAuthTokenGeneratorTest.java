package com.homeparty.identity.jwt;


import autoparams.AutoSource;
import autoparams.customization.Customization;
import com.homeparty.identity.domain.aggregates.authtoken.AuthToken;
import com.homeparty.identity.testing.JwtAuthTokenConfigCustomizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JwtAuthTokenGeneratorTest {

    @DisplayName("AuthToken 을 반환한다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(JwtAuthTokenConfigCustomizer.class)
    public void sut_return_authToken(
            UUID identityId,
            JwtAuthTokenGenerator sut
    ) {

        // when
        AuthToken actual = sut.generate(identityId.toString());

        // then
        assertThat(actual.getAccessToken()).startsWith("ey");
        assertThat(actual.getAccessTokenExpiredAt()).isAfterOrEqualTo(LocalDateTime.now());
        assertThat(actual.getRefreshToken()).startsWith("ey");
        assertThat(actual.getRefreshTokenExpiredAt()).isAfterOrEqualTo(LocalDateTime.now());
    }

    @DisplayName("accessToken 으로 jwt 토큰을 생성한다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(JwtAuthTokenConfigCustomizer.class)
    public void sut_return_jwt_access_token(
            UUID identityId,
            JwtAuthTokenConfig config,
            JwtAuthTokenGenerator sut
    ) {

        // when
        AuthToken authToken = sut.generate(identityId.toString());
        Optional<UUID> actual = new JwtAuthAccessTokenVerifier(config).verify(authToken.getAccessToken());

        // then
        assertThat(actual.get()).isEqualTo(identityId);
    }
}
