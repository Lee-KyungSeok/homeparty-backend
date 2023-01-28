package com.homeparty.api.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseApiTest {

    public static String AUTHORIZATION_HEADER = "Authorization";

    @LocalServerPort
    public int port;

    @Autowired
    public WebTestClient webTestClient;

    @Autowired
    public ObjectMapper objectMapper;

    public String getBearerHeader(String token) {
        return "Bearer " + token;
    }

    public String basePath() {
        return "/";
    }

    @BeforeEach
    public void setUp() {
        this.webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }
}
