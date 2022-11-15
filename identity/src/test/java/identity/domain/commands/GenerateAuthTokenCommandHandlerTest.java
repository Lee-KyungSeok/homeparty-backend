package identity.domain.commands;

import autoparams.AutoSource;
import identity.domain.aggregates.identity.Identity;
import identity.domain.aggregates.identity.IdentityRepository;
import identity.domain.exception.IdentityException;
import identity.domain.exception.IdentityExceptionCode;
import identity.domain.models.SocialProviderFetcher;
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
public class GenerateAuthTokenCommandHandlerTest {

    @DisplayName("이미 가입되어 있다면 에러를 반환한다.")
    @ParameterizedTest
    @AutoSource()
    public void sut_fails_if_already_sign_up(
            Identity identity,
            String socialId,
            String socialNickname,
            String socialEmail,
            String socialImageUrl,
            SignUpSocialCommand command
    ) {
        // given
        setSocialIdAndType(identity, socialId, command.providerType);
        SocialProviderFetcher fetcher = socialProviderFetcherTestDouble(socialId, socialNickname, socialEmail, socialImageUrl);
        identityRepository.save(identity);

        var sut = new SignUpSocialCommandHandler(fetcher, identityRepository);

        // when
        // then
        IdentityException actual = assertThrows(IdentityException.class, () -> sut.handle(command));
        assertThat(actual).isEqualTo(new IdentityException(IdentityExceptionCode.ALREADY_SIGN_UP));
    }
}
