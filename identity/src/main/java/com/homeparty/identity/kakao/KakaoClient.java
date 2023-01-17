package com.homeparty.identity.kakao;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoClient {

    private final WebClient client;

    public KakaoClient() {
        client = WebClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .build();
    }

    public KakaoUser getMe(String accessToken) {
        return client.get()
                .uri("/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUser.class)
                .block();
    }
}

// Person project = client.get().uri("/project/{id}", i).retrieve()
//    .bodyToMono(Project.class)
//    .block();