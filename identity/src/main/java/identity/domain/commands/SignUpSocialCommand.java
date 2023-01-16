package identity.domain.commands;

import abstraction.command.Command;
import identity.domain.aggregates.identity.SocialProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class SignUpSocialCommand implements Command {
    UUID identityId;
    SocialProviderType providerType;
    String providerToken;
}
