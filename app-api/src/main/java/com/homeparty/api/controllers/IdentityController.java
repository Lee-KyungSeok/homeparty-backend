package com.homeparty.api.controllers;

import com.homeparty.api.dto.ApiResponse;
import com.homeparty.api.dto.request.SignUpRequest;
import com.homeparty.identity.domain.aggregates.authtoken.AuthToken;
import com.homeparty.identity.domain.commands.SignUpSocialCommand;
import com.homeparty.identity.domain.commands.SignUpSocialCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    @PostMapping("sign-up")
    public ResponseEntity<ApiResponse<AuthToken>> getInvitation(
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
}
