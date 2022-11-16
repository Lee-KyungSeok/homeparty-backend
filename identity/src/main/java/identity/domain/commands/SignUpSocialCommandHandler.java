package identity.domain.commands;

import identity.domain.aggregates.authtoken.AuthToken;
import identity.domain.aggregates.identity.Identity;
import identity.domain.aggregates.identity.IdentityRepository;
import identity.domain.exception.IdentityException;
import identity.domain.exception.IdentityExceptionCode;
import identity.domain.models.AuthTokenGenerator;
import identity.domain.models.SocialProviderFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpSocialCommandHandler {

    private final SocialProviderFetcher socialProviderFetcher;
    private final AuthTokenGenerator authTokenGenerator;
    private final IdentityRepository identityRepository;

    public AuthToken handle(SignUpSocialCommand command) {
        var socialProvider = socialProviderFetcher.fetch(command.providerType, command.providerToken);
        checkAlreadySignUp(socialProvider.getSocialId());

        Identity identity = new Identity(
                command.identityId,
                socialProvider.getSocialNickname(),
                socialProvider.getSocialEmail(),
                socialProvider);

        identityRepository.save(identity);

        return authTokenGenerator.generate(identity.getId().toString());
    }

    public void checkAlreadySignUp(String socialId) {
        Optional<Identity> existedIdentity = this.identityRepository.findByProviderSocialId(socialId);
        if (existedIdentity.isPresent()) {
            log.info("this user already signup, socialId: {}", socialId);
            throw new IdentityException(IdentityExceptionCode.ALREADY_SIGN_UP);
        }
    }
}
