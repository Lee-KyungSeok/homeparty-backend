package com.homeparty.identity.domain.models;

import com.homeparty.identity.domain.aggregates.authtoken.AuthToken;

public interface AuthTokenGenerator {
    AuthToken generate(String id);
}
