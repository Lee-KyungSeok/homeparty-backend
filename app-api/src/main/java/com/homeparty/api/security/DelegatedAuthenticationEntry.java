package com.homeparty.api.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

public class DelegatedAuthenticationEntry implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public DelegatedAuthenticationEntry(
            HandlerExceptionResolver resolver
    ) {
        this.resolver = resolver;
    }


    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) {
        resolver.resolveException(request, response, null, authException);
    }
}