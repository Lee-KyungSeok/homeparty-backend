package com.homeparty.identity.domain.models;

import autoparams.AutoSource;
import com.homeparty.identity.domain.aggregates.identity.Identity;
import com.homeparty.identity.domain.aggregates.identity.IdentityRepository;
import com.homeparty.identity.domain.aggregates.identity.SocialProvider;
import com.homeparty.identity.domain.aggregates.identity.SocialProviderType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SocialUserExistCheckerTest {

    @Autowired
    private IdentityRepository identityRepository;

    @DisplayName("소셜 유저가 존재하지 않느다면 false 를 반환한다.")
    @ParameterizedTest
    @AutoSource()
    public void sut_returns_false_if_user_not_existed(
            SocialProviderType providerType,
            String socialId,
            String providerToken
    ) {
        // given
        SocialProviderFetcher fetcher = socialProviderFetcherTestDouble(socialId);
        var sut = new SocialUserExistChecker(fetcher, identityRepository);


        // when
        var actual = sut.check(providerType, providerToken);

        // then
        assertThat(actual).isEqualTo(false);
    }

    @DisplayName("소셜 유저가 존재한다면 true 를 반환한다.")
    @ParameterizedTest
    @AutoSource()
    public void sut_returns_true_if_user_existed(
            String providerToken,
            Identity identity
    ) {
        // given
        SocialProviderFetcher fetcher = socialProviderFetcherTestDouble(identity.getProvider().getSocialId());
        var sut = new SocialUserExistChecker(fetcher, identityRepository);
        identityRepository.save(identity);


        // when
        var actual = sut.check(identity.getProvider().getProviderType(), providerToken);

        // then
        assertThat(actual).isEqualTo(true);
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
