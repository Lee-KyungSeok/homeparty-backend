package identity.domain.commands;

import identity.domain.aggregates.identity.SocialProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class SignInSocialCommand {
    SocialProviderType providerType;
    String providerToken;
}
