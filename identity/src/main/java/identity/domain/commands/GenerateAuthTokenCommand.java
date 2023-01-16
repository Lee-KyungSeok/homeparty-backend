package identity.domain.commands;

import abstraction.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GenerateAuthTokenCommand implements Command {
    UUID identityId;
}
