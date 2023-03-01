package com.homeparty.api.controller.invitation_cards;

import autoparams.AutoSource;
import com.homeparty.api.dto.ApiResponse;
import com.homeparty.api.dto.request.CreateInvitationCardRequest;
import com.homeparty.api.testing.BaseApiTest;
import com.homeparty.identity.jwt.JwtAuthTokenGenerator;
import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCardContentType;
import com.homeparty.invitation.domain.commands.CreateInvitationCardCommandHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Post extends BaseApiTest {

    @Autowired
    private JwtAuthTokenGenerator generator;

    @Override
    public String basePath() {
        return "/api/v1/invitation-cards";
    }

    @DisplayName("업로드할 수 있는 정보를 반환한다.")
    @ParameterizedTest
    @AutoSource()
    public void sut_returns_upload_url_info(
            UUID userId,
            InvitationCardContentType contentType
    ) {
        var request = new CreateInvitationCardRequest(contentType.getContentType());
        var token = generator.generate(userId.toString());

        var result = webTestClient
                .post()
                .uri(basePath())
                .body(Mono.just(request), CreateInvitationCardRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, getBearerHeader(token.getAccessToken()))
                .exchange()
                .expectStatus().isCreated()
                .returnResult(ApiResponse.class)
                .getResponseBody()
                .map(b -> objectMapper.convertValue(b.getData(), CreateInvitationCardCommandHandler.Result.class))
                .blockFirst();

        assertThat(result.cardId()).isNotNull();
        assertThat(result.uploadUrl().toString()).contains("/invitation-cards/" + result.cardId() + "_");
        assertThat(result.httpMethod()).isEqualTo("PUT");
        assertThat(result.uploadData()).usingRecursiveComparison().isEqualTo(new HashMap<>());
    }
}
