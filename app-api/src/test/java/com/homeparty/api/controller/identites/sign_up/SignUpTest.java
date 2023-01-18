package com.homeparty.api.controller.identites.sign_up;

import autoparams.AutoSource;
import com.homeparty.api.dto.ApiResponse;
import com.homeparty.api.dto.request.SignUpRequest;
import com.homeparty.api.testing.BaseApiTest;
import com.homeparty.identity.domain.aggregates.authtoken.AuthToken;
import com.homeparty.identity.domain.aggregates.identity.SocialProvider;
import com.homeparty.identity.domain.models.SocialProviderFetcher;
import com.homeparty.identity.jwt.JwtAuthAccessTokenVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class SignUpTest extends BaseApiTest {

    @Autowired
    private JwtAuthAccessTokenVerifier jwtAuthAccessTokenVerifier;

    @MockBean
    private SocialProviderFetcher socialProviderFetcher;

    @Override
    public String basePath() {
        return "/api/v1/identities/sign-up-social";
    }

    @DisplayName("성공시 AuToken 을 반환한다.")
    @ParameterizedTest
    @AutoSource()
    public void sut_returns_authToken(
            SignUpRequest request,
            SocialProvider provider
    ) {
        given(socialProviderFetcher.fetch(request.getProviderType(), request.getProviderToken()))
                .willReturn(provider);

        AuthToken authToken = webTestClient
                .post()
                .uri(basePath())
                .body(Mono.just(request), SignUpRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(ApiResponse.class)
                .getResponseBody()
                .map(b -> objectMapper.convertValue(b.getData(), AuthToken.class))
                .blockFirst();

        assertThat(authToken.getAccessToken()).isNotNull();
        assertThat(authToken.getAccessTokenExpiredAt()).isNotNull();
        assertThat(authToken.getRefreshToken()).isNotNull();
        assertThat(authToken.getRefreshTokenExpiredAt()).isNotNull();

        // access token 이 제대로 되었는지 확인
        jwtAuthAccessTokenVerifier.verify(authToken.getAccessToken());
    }
}
