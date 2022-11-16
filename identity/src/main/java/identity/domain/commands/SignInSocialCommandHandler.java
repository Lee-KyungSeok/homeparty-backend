package identity.domain.commands;

import identity.domain.aggregates.authtoken.AuthToken;
import identity.domain.aggregates.identity.Identity;
import identity.domain.aggregates.identity.IdentityRepository;
import identity.domain.aggregates.identity.SocialProviderType;
import identity.domain.exception.IdentityException;
import identity.domain.exception.IdentityExceptionCode;
import identity.domain.models.AuthTokenGenerator;
import identity.domain.models.SocialProviderFetcher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignInSocialCommandHandler {

    private final SocialProviderFetcher socialProviderFetcher;
    private final AuthTokenGenerator authTokenGenerator;
    private final IdentityRepository identityRepository;

    public AuthToken handle(SignInSocialCommand command) {
        var socialProvider = socialProviderFetcher.fetch(command.providerType, command.providerToken);
        Optional<Identity> identity = identityRepository.findByProviderSocialId(socialProvider.getSocialId());
        if (identity.isEmpty()) {
            log.info("this user need signup, socialProvider: {}", socialProvider);
            throw new IdentityException(IdentityExceptionCode.NEED_SIGN_UP);
        }

        return authTokenGenerator.generate(identity.get().getId().toString());

//        checkAlreadySignUp(socialProvider.getSocialId());
//
//        Identity identity = new Identity(
//                command.identityId,
//                socialProvider.getSocialNickname(),
//                socialProvider.getSocialEmail(),
//                socialProvider);
//
//        identityRepository.save(identity);
    }
}
