package invitation.domain.commands;

import invitation.domain.aggregates.invitationcard.InvitationCardState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class CreateInvitationCardCommand {
    private UUID cardId;
    private UUID uploaderId;
    private String fileExtension;
}
