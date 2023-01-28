package com.homeparty.api.controller.identites.me;

import autoparams.AutoSource;
import com.homeparty.api.dto.ApiResponse;
import com.homeparty.api.readmodel.view.IdentityView;
import com.homeparty.api.readmodel.view.ProfileView;
import com.homeparty.api.testing.BaseApiTest;
import com.homeparty.identity.domain.aggregates.authtoken.AuthToken;
import com.homeparty.identity.domain.aggregates.identity.Identity;
import com.homeparty.identity.domain.aggregates.identity.IdentityRepository;
import com.homeparty.identity.domain.commands.SignInSocialCommand;
import com.homeparty.identity.domain.exception.IdentityExceptionCode;
import com.homeparty.identity.domain.models.SocialProviderFetcher;
import com.homeparty.identity.jwt.JwtAuthAccessTokenVerifier;
import com.homeparty.identity.jwt.JwtAuthTokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class Get_Me extends BaseApiTest {
    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private JwtAuthTokenGenerator generator;

    @Override
    public String basePath() {
        return "/api/v1/identities/me";
    }

    @DisplayName("내 identity 정보를 반환한다.")
    @ParameterizedTest
    @AutoSource()
    public void sut_returns_identity(Identity identity) {
        identityRepository.save(identity);
        var token = generator.generate(identity.getId().toString());
        var expected = new IdentityView(
                identity.getId(),
                identity.getNickname(),
                identity.getProfileImageUrl(),
                identity.getEmail(),
                identity.getProvider().getProviderType()
        );

        IdentityView view = webTestClient
                .get()
                .uri(basePath())
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, getBearerHeader(token.getAccessToken()))
                .exchange()
                .expectStatus().isOk()
                .returnResult(ApiResponse.class)
                .getResponseBody()
                .map(b -> objectMapper.convertValue(b.getData(), IdentityView.class))
                .blockFirst();

        assertThat(view).usingRecursiveComparison().isEqualTo(expected);
    }

//    @DisplayName("성공시 AuToken 을 반환한다.")
//    @ParameterizedTest
//    @AutoSource()
//    public void sut_returns_authToken(
//            SignInSocialCommand command,
//            Identity identity
//    ) {
//        given(socialProviderFetcher.fetch(command.getProviderType(), command.getProviderToken()))
//                .willReturn(identity.getProvider());
//        identityRepository.save(identity);
//
//        AuthToken authToken = webTestClient
//                .post()
//                .uri(basePath())
//                .body(Mono.just(command), SignInSocialCommand.class)
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isOk()
//                .returnResult(ApiResponse.class)
//                .getResponseBody()
//                .map(b -> objectMapper.convertValue(b.getData(), AuthToken.class))
//                .blockFirst();
//
//        assertThat(authToken.getAccessToken()).isNotNull();
//        assertThat(authToken.getAccessTokenExpiredAt()).isNotNull();
//        assertThat(authToken.getRefreshToken()).isNotNull();
//        assertThat(authToken.getRefreshTokenExpiredAt()).isNotNull();
//
//        // access token 이 제대로 되었는지 확인
//        Optional<UUID> identityId = jwtAuthAccessTokenVerifier.verify(authToken.getAccessToken());
//        assertThat(identityId).isEqualTo(Optional.of(identity.getId()));
//    }
}
