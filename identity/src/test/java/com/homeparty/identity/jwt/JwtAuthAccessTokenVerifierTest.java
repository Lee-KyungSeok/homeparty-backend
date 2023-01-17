package com.homeparty.identity.jwt;


import autoparams.AutoSource;
import autoparams.customization.Customization;
import com.homeparty.identity.domain.aggregates.authtoken.AuthToken;
import com.homeparty.identity.testing.JwtAuthTokenConfigCustomizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JwtAuthAccessTokenVerifierTest {

    @DisplayName("uuid 형식으로 저장되어 있지 않다면 빈값을 반환한다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(JwtAuthTokenConfigCustomizer.class)
    public void sut_return_empty_if_subject_not_uuid(
            JwtAuthTokenGenerator authTokenGenerator,
            JwtAuthAccessTokenVerifier sut
    ) {

        // given
        AuthToken token = authTokenGenerator.generate("123");

        // when
        Optional<UUID> actual = sut.verify(token.getAccessToken());

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("jwt format 이 이상하다면 빈값을 반환한다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(JwtAuthTokenConfigCustomizer.class)
    public void sut_return_empty_if_jwt_format_different(
            JwtAuthAccessTokenVerifier sut
    ) {

        // given
        String accessToken = "123hbhjsabhfjbj13";

        // when
        Optional<UUID> actual = sut.verify(accessToken);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("tokenType 이 일치하지 않으면 빈값을 반환한다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(JwtAuthTokenConfigCustomizer.class)
    public void sut_return_empty_if_token_type_different(
            UUID identityId,
            JwtAuthTokenConfig config,
            JwtAuthAccessTokenVerifier sut
    ) throws NoSuchFieldException {
        // given
        JwtAuthTokenConfig wrongConfig = new JwtAuthTokenConfig(config.getSecretKey());
        setAccessTokenType(wrongConfig, UUID.randomUUID().toString());
        JwtAuthTokenGenerator authTokenGenerator = new JwtAuthTokenGenerator(wrongConfig);
        AuthToken token = authTokenGenerator.generate(identityId.toString());

        // when
        Optional<UUID> actual = sut.verify(token.getAccessToken());

        // then
        assertThat(actual).isEmpty();
    }

    private static void setAccessTokenType(JwtAuthTokenConfig config, String tokenType) {
        try {
            Field accessTokenTypeField = config.getClass().getDeclaredField("accessTokenType");
            accessTokenTypeField.setAccessible(true);
            accessTokenTypeField.set(config, tokenType);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("유저의 uuid 를 반환한다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(JwtAuthTokenConfigCustomizer.class)
    public void sut_return_user_uuid(
            UUID identityId,
            JwtAuthTokenGenerator authTokenGenerator,
            JwtAuthAccessTokenVerifier sut
    ) {

        // given
        AuthToken token = authTokenGenerator.generate(identityId.toString());

        // when
        Optional<UUID> actual = sut.verify(token.getAccessToken());

        // then
        assertThat(actual).isEqualTo(Optional.of(identityId));
    }
}
