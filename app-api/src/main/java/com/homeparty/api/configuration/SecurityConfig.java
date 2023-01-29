package com.homeparty.api.configuration;

import com.homeparty.api.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

//@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    private final DelegatedAuthenticationEntry authenticationEntryPoint;
//    private final DelegatedAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthProvider jwtAuthProvider;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public SecurityConfig(
            JwtAuthProvider jwtAuthProvider,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtAuthProvider = jwtAuthProvider;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .headers().frameOptions().sameOrigin()
                .and().cors()
                .and().csrf().disable();
        http
                .formLogin().disable()
                .httpBasic().disable();
        http
                .exceptionHandling()
                .accessDeniedPage("/api/")
                .accessDeniedHandler(new DelegatedAccessDeniedHandler(handlerExceptionResolver))
                .authenticationEntryPoint(new DelegatedAuthenticationEntry(handlerExceptionResolver));
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers("/h2-console/**").permitAll() // h2 console
                .requestMatchers(
                        "/error",
                        "/health",
                        "/actuator",
                        "/actuator/**"
                ).permitAll()
                .requestMatchers(
                        "/api/v1/auth/sign-up-social",
                        "/api/v1/auth/sign-in-social",
                        "/api/v1/auth/verify-social-user-existed"
                ).permitAll()
//                .antMatchers("/api/auth/validate-access-token/**").hasAnyRole("USER") // -> USER_ROLE 설정됨
                .requestMatchers("/api/v1/**").authenticated()
                .anyRequest().denyAll();

        http
                .addFilterAt(jwtAuthFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterBefore(new HomePartyExceptionFilter(handlerExceptionResolver), JwtAuthFilter.class);

        return http.build();
    }

    public JwtAuthFilter jwtAuthFilter() {
        JwtAuthFilter filter = new JwtAuthFilter();
        filter.setAuthenticationManager(new ProviderManager(jwtAuthProvider));
        return filter;
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//
//                );
//    }
}