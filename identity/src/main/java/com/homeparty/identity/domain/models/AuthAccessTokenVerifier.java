package com.homeparty.identity.domain.models;

import java.util.Optional;
import java.util.UUID;

public interface AuthAccessTokenVerifier {
    Optional<UUID> verify(String accessToken);
}
