package com.homeparty.invitation.domain.commands;

import abstraction.command.CommandHandler;
import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCardRepository;
import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCard;
import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCardState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateInvitationCardCommandHandler implements CommandHandler<CreateInvitationCardCommand, UUID> {

    private final InvitationCardRepository invitationCardRepository;

    public UUID handle(CreateInvitationCardCommand command) {
        InvitationCard invitationCard = new InvitationCard(
                command.getCardId(),
                command.getUploaderId(),
                InvitationCardState.CREATED,
                InvitationCard.makeNewFilePath(command.getFileExtension()),
                LocalDateTime.now()
        );
        invitationCardRepository.save(invitationCard);
        return invitationCard.getId();
    }
}
