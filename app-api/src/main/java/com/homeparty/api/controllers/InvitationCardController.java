package com.homeparty.api.controllers;

import com.homeparty.api.dto.ApiResponse;
import com.homeparty.api.dto.request.CreateInvitationCardRequest;
import com.homeparty.api.dto.request.SignUpRequest;
import com.homeparty.api.security.AuthenticationConverter;
import com.homeparty.identity.domain.aggregates.authtoken.AuthToken;
import com.homeparty.identity.domain.commands.SignUpSocialCommand;
import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCardContentType;
import com.homeparty.invitation.domain.commands.CreateInvitationCardCommand;
import com.homeparty.invitation.domain.commands.CreateInvitationCardCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/invitation-cards")
public class InvitationCardController {

    private final AuthenticationConverter authenticationConverter;
    private final CreateInvitationCardCommandHandler createInvitationCardCommandHandler;

    @PostMapping("")
    public ResponseEntity<ApiResponse<CreateInvitationCardCommandHandler.Result>> crateInvitationCard(
            Authentication authentication,
            @RequestBody CreateInvitationCardRequest request
    ) {
        UUID identityId = authenticationConverter.convertToIdentityId(authentication);
        var command = new CreateInvitationCardCommand(
                UUID.randomUUID(),
                identityId,
                InvitationCardContentType.getInvitationCardContentType(request.contentType())
        );
        var result = createInvitationCardCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result));
    }

}
