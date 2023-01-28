package com.homeparty.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeparty.api.dto.ApiResponse;
import com.homeparty.api.exception.ApiExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        var exceptionCode = ApiExceptionCode.FORBIDDEN_ERROR;

        var unauthorizedResponse = ApiResponse.failure(exceptionCode);
        String jsonLoginResponse = objectMapper.writeValueAsString(unauthorizedResponse);

        response.setContentType("application/json");
        response.setStatus(exceptionCode.getStatus());
        response.getWriter().print(jsonLoginResponse);
    }
}