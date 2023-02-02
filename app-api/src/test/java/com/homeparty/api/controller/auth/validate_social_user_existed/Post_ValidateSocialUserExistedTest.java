package com.homeparty.api.controller.auth.validate_social_user_existed;

import autoparams.AutoSource;
import com.homeparty.api.dto.ApiResponse;
import com.homeparty.api.dto.request.ValidateSocialUserExistedRequest;
import com.homeparty.api.dto.response.ExistedResponse;
import com.homeparty.api.testing.BaseApiTest;
import com.homeparty.identity.domain.aggregates.identity.Identity;
import com.homeparty.identity.domain.aggregates.identity.IdentityRepository;
import com.homeparty.identity.domain.aggregates.identity.SocialProvider;
import com.homeparty.identity.domain.models.SocialProviderFetcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class Post_ValidateSocialUserExistedTest extends BaseApiTest {

    @MockBean
    private SocialProviderFetcher socialProviderFetcher;

    @Autowired
    private IdentityRepository identityRepository;

    @Override
    public String basePath() {
        return "/api/v1/auth/validate-social-user-existed";
    }

    @DisplayName("유저가 존재하지 않는다면 false 를 반환한다.")
    @ParameterizedTest
    @AutoSource()
    public void sut_returns_false_exist_if_user_not_existed(
            ValidateSocialUserExistedRequest request,
            SocialProvider provider
    ) {
        given(socialProviderFetcher.fetch(request.getProviderType(), request.getProviderToken()))
                .willReturn(provider);

        ExistedResponse response = webTestClient
                .post()
                .uri(basePath())
                .body(Mono.just(request), ValidateSocialUserExistedRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(ApiResponse.class)
                .getResponseBody()
                .map(b -> objectMapper.convertValue(b.getData(), ExistedResponse.class))
                .blockFirst();

        assertThat(response.isExist()).isEqualTo(false);
    }

    @DisplayName("유저가 존재한다면 true 를 반환한다.")
    @ParameterizedTest
    @AutoSource()
    public void sut_returns_true_exist_if_user_existed(
            ValidateSocialUserExistedRequest request,
            Identity identity
    ) {
        given(socialProviderFetcher.fetch(request.getProviderType(), request.getProviderToken()))
                .willReturn(identity.getProvider());
        identityRepository.save(identity);

        ExistedResponse response = webTestClient
                .post()
                .uri(basePath())
                .body(Mono.just(request), ValidateSocialUserExistedRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(ApiResponse.class)
                .getResponseBody()
                .map(b -> objectMapper.convertValue(b.getData(), ExistedResponse.class))
                .blockFirst();

        assertThat(response.isExist()).isEqualTo(true);
    }
}
