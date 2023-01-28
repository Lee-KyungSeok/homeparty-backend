package com.homeparty.api.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class JwtAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final Pattern PATTERN_AUTHORIZATION_HEADER = Pattern.compile("[Bb]earer (.+)");

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return extractToken(request);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return extractToken(request);
    }

    private String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null) {
            return null;
        }
        Matcher matcher = PATTERN_AUTHORIZATION_HEADER.matcher(authorizationHeader);
        if (!matcher.matches()) {
            return null;
        }
        return matcher.group(1);
    }
}