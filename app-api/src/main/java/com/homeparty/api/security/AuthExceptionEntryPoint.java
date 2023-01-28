package com.homeparty.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeparty.api.dto.ApiResponse;
import com.homeparty.api.exception.ApiExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e
    ) throws IOException {

        var exceptionCode = ApiExceptionCode.UNAUTHORIZED_ERROR;

        var unauthorizedResponse = ApiResponse.failure(exceptionCode);
        String jsonLoginResponse = objectMapper.writeValueAsString(unauthorizedResponse);

        response.setContentType("application/json");
        response.setStatus(exceptionCode.getStatus());
        response.getWriter().print(jsonLoginResponse);
    }
}