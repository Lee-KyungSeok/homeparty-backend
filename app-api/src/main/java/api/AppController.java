package api;

import identity.Identity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AppController {

    @GetMapping("/api/identities/{identityId}")
    public Identity getIdentity(
            @PathVariable String identityId,
            @RequestParam String provider
    ) {
        log.info("get identities, id: {}, provider: {}", identityId, provider);
        return new Identity(identityId, provider);
    }
}
