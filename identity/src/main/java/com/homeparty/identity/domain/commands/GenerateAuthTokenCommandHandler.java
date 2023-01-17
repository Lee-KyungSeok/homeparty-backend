package com.homeparty.identity.domain.commands;

import abstraction.command.CommandHandler;
import com.homeparty.identity.domain.aggregates.authtoken.AuthToken;
import com.homeparty.identity.domain.models.AuthTokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerateAuthTokenCommandHandler implements CommandHandler<GenerateAuthTokenCommand, AuthToken> {

    private final AuthTokenGenerator authTokenGenerator;

    public AuthToken handle(GenerateAuthTokenCommand command) {
        return authTokenGenerator.generate(command.identityId.toString());
    }
}
