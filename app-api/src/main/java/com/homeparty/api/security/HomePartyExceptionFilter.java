package com.homeparty.api.security;

import abstraction.exeption.HomePartyException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@RequiredArgsConstructor
public class HomePartyExceptionFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (HomePartyException e){
            resolver.resolveException(request, response, null, e);


//            var exceptionResponse = ApiResponse.failure(e.getExceptionCode());
//            String jsonLoginResponse = objectMapper.writeValueAsString(exceptionResponse);
//
//            response.setContentType("application/json");
//            response.setStatus(e.getExceptionCode().getStatus());
//            response.getWriter().print(jsonLoginResponse);
        }
    }
}
