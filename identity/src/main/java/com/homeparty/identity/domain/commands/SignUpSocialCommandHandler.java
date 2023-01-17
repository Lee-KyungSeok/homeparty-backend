package com.homeparty.identity.domain.commands;

import abstraction.command.CommandHandler;
import com.homeparty.identity.domain.aggregates.authtoken.AuthToken;
import com.homeparty.identity.domain.aggregates.identity.Identity;
import com.homeparty.identity.domain.aggregates.identity.IdentityRepository;
import com.homeparty.identity.domain.exception.IdentityException;
import com.homeparty.identity.domain.exception.IdentityExceptionCode;
import com.homeparty.identity.domain.models.AuthTokenGenerator;
import com.homeparty.identity.domain.models.SocialProviderFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpSocialCommandHandler implements CommandHandler<SignUpSocialCommand, AuthToken> {

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
