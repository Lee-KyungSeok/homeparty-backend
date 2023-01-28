package com.homeparty.api.security;

import com.homeparty.api.exception.ApiException;
import com.homeparty.api.exception.ApiExceptionCode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthenticationConverter {
    public UUID convertToIdentityId(Authentication authentication) {
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {

            throw new ApiException(ApiExceptionCode.UNAUTHORIZED_ERROR);
        }

        return (UUID) authentication.getPrincipal();
    }
}
