package com.homeparty.identity.domain.commands;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import com.homeparty.identity.testing.CommandsCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GenerateAuthTokenCommandHandlerTest {

    @DisplayName("AuthToken 을 반환한다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(CommandsCustomizer.class)
    public void sut_return_authToken(
            GenerateAuthTokenCommand command,
            GenerateAuthTokenCommandHandler sut
    ) {
        // when
        var actual = sut.handle(command);

        // then
        assertThat(actual.getAccessToken()).isNotNull();
        assertThat(actual.getAccessTokenExpiredAt()).isNotNull();
        assertThat(actual.getRefreshToken()).isNotNull();
        assertThat(actual.getRefreshTokenExpiredAt()).isNotNull();
    }
}
