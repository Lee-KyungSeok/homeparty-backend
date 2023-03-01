package com.homeparty.invitation.domain.commands;

import abstraction.command.CommandHandler;
import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCard;
import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCardRepository;
import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCardState;
import com.homeparty.invitation.domain.models.InvitationCardUploadUrlFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateInvitationCardCommandHandler implements CommandHandler<CreateInvitationCardCommand, CreateInvitationCardCommandHandler.Result> {

    private final InvitationCardUploadUrlFetcher invitationCardUploadUrlFetcher;
    private final InvitationCardRepository invitationCardRepository;

    public CreateInvitationCardCommandHandler.Result handle(CreateInvitationCardCommand command) {

        var invitationCard = new InvitationCard(
                command.getCardId(),
                command.getUploaderId(),
                InvitationCardState.CREATED,
                LocalDateTime.now()
        );

        var uploadUrl = invitationCardUploadUrlFetcher.fetch(
                new InvitationCardUploadUrlFetcher.InvitationCardUploadUrlParams(
                        invitationCard.getFilePath(),
                        command.getContentType()
                )
        );

        invitationCardRepository.save(invitationCard);
        return new Result(
                command.getCardId(),
                uploadUrl.uploadUrl(),
                uploadUrl.httpMethod(),
                uploadUrl.uploadData()
        );
    }

    public record Result(
            UUID cardId,
            URL uploadUrl,
            String httpMethod,
            Object uploadData
    ) {}
}
