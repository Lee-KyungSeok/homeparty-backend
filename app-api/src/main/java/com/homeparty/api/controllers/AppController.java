package com.homeparty.api.controllers;

import com.homeparty.api.exception.ApiException;
import com.homeparty.api.exception.ApiExceptionCode;
import com.homeparty.identity.domain.exception.IdentityException;
import com.homeparty.identity.domain.exception.IdentityExceptionCode;
import com.homeparty.invitation.domain.exception.InvitationException;
import com.homeparty.invitation.domain.exception.InvitationExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppController {
    @GetMapping("/api/sample")
    public void throwSample() {
        throw new ApiException(ApiExceptionCode.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/api/sample/identity")
    public void throwSampleIdentity() {
        throw new IdentityException(IdentityExceptionCode.NEED_SIGN_UP);
    }

    @GetMapping("/api/sample/invitation")
    public void throwSampleInvitation() {
        throw new InvitationException(InvitationExceptionCode.NOT_FOUND_INVITATION);
    }

//    @GetMapping("/api/invitation/{invitationId}")
//    public Invitation getInvitation(
//            @PathVariable String invitationId
//    ) {
//        return new Invitation(UUID.fromString(invitationId), "hi");
//    }

//    private final IdentityRepository identityRepository;
//
//    @GetMapping("/api/identities/{identityId}")
//    public Identity getIdentity(
//            @PathVariable String identityId,
//            @RequestParam String provider
//    ) {
//        log.info("get identities, id: {}, provider: {}", identityId, provider);
//        return new Identity(UUID.fromString(identityId), provider);
//    }
//
//    static class CreateIdentity {
//        String provider;
//
//        protected CreateIdentity() {
//
//        }
//
//        public CreateIdentity(String provider) {
//            this.provider = provider;
//        }
//
//        public String getProvider() {
//            return provider;
//        }
//    }
//
//    @PostMapping("/api/identities/{identityId}")
//    public Identity createIdentity(
//            @PathVariable String identityId,
//            @RequestBody CreateIdentity createIdentity
//    ) {
//        UUID id = UUID.fromString(identityId);
//        identityRepository.save(new Identity(id, createIdentity.provider));
//
//        Optional<Identity> result = identityRepository.findById(id);
//
//        // 임시로 설정
//        return result.get();
//    }
}
