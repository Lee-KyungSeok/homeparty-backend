package api;

import invitation.domain.Invitation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppController {
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
