package com.homeparty.api.security;

import com.homeparty.api.exception.ApiException;
import com.homeparty.api.exception.ApiExceptionCode;
import com.homeparty.identity.jwt.JwtAuthAccessTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JwtAuthProvider implements AuthenticationProvider {

    private final JwtAuthAccessTokenVerifier tokenVerifier;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof PreAuthenticatedAuthenticationToken)) {
            throw new ApiException(ApiExceptionCode.INVALID_ACCESS_TOKEN);
        }

        UUID identityId = Optional.ofNullable(authentication.getPrincipal())
                .map(String.class::cast)
                .flatMap(tokenVerifier::verify)
                .orElseThrow(() -> new ApiException(ApiExceptionCode.INVALID_ACCESS_TOKEN));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new PreAuthenticatedAuthenticationToken(
                identityId,
                null,
                Collections.singleton(new SimpleGrantedAuthority("USER"))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}