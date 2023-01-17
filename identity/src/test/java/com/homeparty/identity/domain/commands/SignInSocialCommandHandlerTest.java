package com.homeparty.identity.domain.commands;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import com.homeparty.identity.domain.aggregates.identity.Identity;
import com.homeparty.identity.domain.aggregates.identity.IdentityRepository;
import com.homeparty.identity.domain.aggregates.identity.SocialProvider;
import com.homeparty.identity.domain.exception.IdentityException;
import com.homeparty.identity.domain.exception.IdentityExceptionCode;
import com.homeparty.identity.domain.models.AuthTokenGenerator;
import com.homeparty.identity.domain.models.SocialProviderFetcher;
import com.homeparty.identity.testing.CommandsCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SignInSocialCommandHandlerTest {

    @Autowired
    private IdentityRepository identityRepository;

    @DisplayName("가입되어 있는 유저가 없다면 에러를 반환한다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(CommandsCustomizer.class)
    public void sut_fails_if_not_sign_up(
            String socialId,
            AuthTokenGenerator authTokenGenerator,
            SignInSocialCommand command
    ) {
        // given
        SocialProviderFetcher fetcher = socialProviderFetcherTestDouble(socialId);
        var sut = new SignInSocialCommandHandler(fetcher, authTokenGenerator, identityRepository);

        // when
        IdentityException actual = assertThrows(IdentityException.class, () -> sut.handle(command));

        // then
        assertThat(actual).isEqualTo(new IdentityException(IdentityExceptionCode.NEED_SIGN_UP));
    }

    @DisplayName("AuthToken 을 반환한다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(CommandsCustomizer.class)
    public void sut_return_authToken(
            Identity identity,
            AuthTokenGenerator authTokenGenerator,
            SignInSocialCommand command
    ) {
        // given
        SocialProviderFetcher fetcher = socialProviderFetcherTestDouble(identity.getProvider().getSocialId());
        identityRepository.save(identity);
        var sut = new SignInSocialCommandHandler(fetcher, authTokenGenerator, identityRepository);

        // when
        var actual = sut.handle(command);

        // then
        assertThat(actual.getAccessToken()).isNotNull();
        assertThat(actual.getAccessTokenExpiredAt()).isNotNull();
        assertThat(actual.getRefreshToken()).isNotNull();
        assertThat(actual.getRefreshTokenExpiredAt()).isNotNull();
    }

    private static SocialProviderFetcher socialProviderFetcherTestDouble(String socialId) {
        return (type, token) -> new SocialProvider(
                type,
                socialId,
                "nickname",
                "email",
                "imageUrl"
        );
    }
}
