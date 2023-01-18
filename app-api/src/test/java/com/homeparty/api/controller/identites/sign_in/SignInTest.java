package com.homeparty.api.controller.identites.sign_in;

import autoparams.AutoSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeparty.api.dto.ApiResponse;
import com.homeparty.identity.domain.aggregates.authtoken.AuthToken;
import com.homeparty.identity.domain.aggregates.identity.Identity;
import com.homeparty.identity.domain.aggregates.identity.IdentityRepository;
import com.homeparty.identity.domain.commands.SignInSocialCommand;
import com.homeparty.identity.domain.exception.IdentityExceptionCode;
import com.homeparty.identity.domain.models.SocialProviderFetcher;
import com.homeparty.identity.jwt.JwtAuthAccessTokenVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SignInTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JwtAuthAccessTokenVerifier jwtAuthAccessTokenVerifier;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IdentityRepository identityRepository;

    @MockBean
    private SocialProviderFetcher socialProviderFetcher;

    @BeforeEach
    public void setUp() {
        this.webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    private String basePath() {
        return "/api/v1/identities/sign-in-social";
    }

    @DisplayName("회원가입되어 있지 않다면 에러를 반환한다.")
    @ParameterizedTest
    @AutoSource()
    public void sut_fails_if_not_sign_up(
            SignInSocialCommand command,
            Identity identity
    ) {
        given(socialProviderFetcher.fetch(command.getProviderType(), command.getProviderToken()))
                .willReturn(identity.getProvider());
        var exceptionCode = IdentityExceptionCode.NEED_SIGN_UP;

        ApiResponse apiResponse = webTestClient
                .post()
                .uri(basePath())
                .body(Mono.just(command), SignInSocialCommand.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(exceptionCode.getStatus())
                .returnResult(ApiResponse.class)
                .getResponseBody()
                .blockFirst();

        assertThat(apiResponse.getCode()).isEqualTo(exceptionCode.getCode());
    }

    @DisplayName("성공시 AuToken 을 반환한다.")
    @ParameterizedTest
    @AutoSource()
    public void sut_returns_authToken(
            SignInSocialCommand command,
            Identity identity
    ) {
        given(socialProviderFetcher.fetch(command.getProviderType(), command.getProviderToken()))
                .willReturn(identity.getProvider());
        identityRepository.save(identity);

        AuthToken authToken = webTestClient
                .post()
                .uri(basePath())
                .body(Mono.just(command), SignInSocialCommand.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(ApiResponse.class)
                .getResponseBody()
                .map(b -> objectMapper.convertValue(b.getData(), AuthToken.class))
                .blockFirst();

        assertThat(authToken.getAccessToken()).isNotNull();
        assertThat(authToken.getAccessTokenExpiredAt()).isNotNull();
        assertThat(authToken.getRefreshToken()).isNotNull();
        assertThat(authToken.getRefreshTokenExpiredAt()).isNotNull();

        // access token 이 제대로 되었는지 확인
        Optional<UUID> identityId = jwtAuthAccessTokenVerifier.verify(authToken.getAccessToken());
        assertThat(identityId).isEqualTo(Optional.of(identity.getId()));
    }
}
