package identity.domain.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GenerateAuthTokenCommand {
    UUID identityId;
}
