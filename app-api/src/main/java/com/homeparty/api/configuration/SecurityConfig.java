package com.homeparty.api.configuration;

import com.homeparty.api.security.AuthExceptionEntryPoint;
import com.homeparty.api.security.CustomAccessDeniedHandler;
import com.homeparty.api.security.JwtAuthFilter;
import com.homeparty.api.security.JwtAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthExceptionEntryPoint authenticationExceptionEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthProvider jwtAuthProvider;

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
                .authenticationEntryPoint(authenticationExceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers("/h2-console/**").permitAll() // h2 console
                .requestMatchers(
                        "/api/v1/auth/sign-up-social",
                        "/api/v1/auth/sign-in-social",
                        "/api/v1/auth/verify-social-user-existed"
                ).permitAll()
//                .antMatchers("/api/auth/validate-access-token/**").hasAnyRole("USER") // -> USER_ROLE 설정됨
                .requestMatchers("/api/v1/**").authenticated()
                .anyRequest().denyAll();

        http.addFilterAt(
                jwtAuthFilter(),
                AbstractPreAuthenticatedProcessingFilter.class
        );

        return http.build();
    }

    public JwtAuthFilter jwtAuthFilter() {
        JwtAuthFilter filter = new JwtAuthFilter();
        filter.setAuthenticationManager(new ProviderManager(jwtAuthProvider));
        return filter;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/health",
                        "/actuator",
                        "/actuator/**"
                );
    }
}