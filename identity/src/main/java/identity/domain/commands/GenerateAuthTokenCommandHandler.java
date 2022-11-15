package identity.domain.commands;

import identity.domain.aggregates.authtoken.AuthToken;
import identity.domain.models.AuthTokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerateAuthTokenCommandHandler {

    private final AuthTokenGenerator authTokenGenerator;

    public AuthToken handle(GenerateAuthTokenCommand command) {
        return authTokenGenerator.generate(command.identityId.toString());
    }

}
