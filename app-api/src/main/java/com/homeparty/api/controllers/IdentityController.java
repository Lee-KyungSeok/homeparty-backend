package com.homeparty.api.controllers;

import com.homeparty.api.dto.ApiResponse;
import com.homeparty.api.exception.ApiException;
import com.homeparty.api.exception.ApiExceptionCode;
import com.homeparty.api.readmodel.reader.IdentityReader;
import com.homeparty.api.readmodel.view.IdentityView;
import com.homeparty.api.security.AuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/identities")
public class IdentityController {

    private final AuthenticationConverter authenticationConverter;
    private final IdentityReader reader;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<IdentityView>> getMe(
            Authentication authentication
    ) {
        UUID identityId = authenticationConverter.convertToIdentityId(authentication);

        IdentityView view = reader.getIdentityById(identityId)
                .orElseThrow(() -> new ApiException(ApiExceptionCode.NOT_FOUND_ERROR));

        return ResponseEntity.ok().body(ApiResponse.success(view));
    }
}
