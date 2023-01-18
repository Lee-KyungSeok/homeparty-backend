package com.homeparty.api.controllers;

import com.homeparty.api.dto.ApiResponse;
import com.homeparty.api.dto.request.SignUpRequest;
import com.homeparty.identity.domain.aggregates.authtoken.AuthToken;
import com.homeparty.identity.domain.commands.SignInSocialCommand;
import com.homeparty.identity.domain.commands.SignInSocialCommandHandler;
import com.homeparty.identity.domain.commands.SignUpSocialCommand;
import com.homeparty.identity.domain.commands.SignUpSocialCommandHandler;
import com.homeparty.identity.domain.models.SocialProviderFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/identities")
public class IdentityController {

    private final SignUpSocialCommandHandler signUpSocialCommandHandler;
    private final SignInSocialCommandHandler signInSocialCommandHandler;
    private final SocialProviderFetcher fetcher;

    @PostMapping("sign-up-social")
    public ResponseEntity<ApiResponse<AuthToken>> signUp(
            @RequestBody SignUpRequest request
    ) {
        var command = new SignUpSocialCommand(
                UUID.randomUUID(),
                request.getProviderType(),
                request.getProviderToken()
        );
        var result = signUpSocialCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result));
    }

    @PostMapping("sign-in-social")
    public ResponseEntity<ApiResponse<AuthToken>> signIn(
            @RequestBody SignInSocialCommand command
    ) {
        var result = signInSocialCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(result));
    }
}
