package api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppController {

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
