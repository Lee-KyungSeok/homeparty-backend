package invitation.domain.commands;

import invitation.domain.aggregates.invitationcard.InvitationCard;
import invitation.domain.aggregates.invitationcard.InvitationCardRepository;
import invitation.domain.aggregates.invitationcard.InvitationCardState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateInvitationCardCommandHandler {

    private final InvitationCardRepository invitationCardRepository;

    public void handle(CreateInvitationCardCommand command) {
        InvitationCard invitationCard = new InvitationCard(
                command.getCardId(),
                command.getUploaderId(),
                InvitationCardState.CREATED,
                InvitationCard.makeNewFilePath(command.getFileExtension()),
                LocalDateTime.now()
        );
        invitationCardRepository.save(invitationCard);
    }
}
