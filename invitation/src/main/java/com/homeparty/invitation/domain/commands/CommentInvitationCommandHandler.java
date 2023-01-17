package com.homeparty.invitation.domain.commands;

import abstraction.command.CommandHandler;
import com.homeparty.invitation.domain.aggregates.invitation.InvitationRepository;
import com.homeparty.invitation.domain.aggregates.invitationcomment.InvitationComment;
import com.homeparty.invitation.domain.aggregates.invitationcomment.InvitationCommentRepository;
import com.homeparty.invitation.domain.exception.InvitationException;
import com.homeparty.invitation.domain.exception.InvitationExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentInvitationCommandHandler implements CommandHandler<CommentInvitationCommand, UUID> {
    private final InvitationRepository invitationRepository;
    private final InvitationCommentRepository invitationCommentRepository;

    public UUID handle(CommentInvitationCommand command) {
        this.verifyInvitationExisted(command.getInvitationId());

        InvitationComment comment = new InvitationComment(
                command.getCommentId(),
                command.getInvitationId(),
                command.getUserId(),
                command.getName(),
                command.getContent(),
                command.isSecret(),
                LocalDateTime.now()
        );

        invitationCommentRepository.save(comment);
        return comment.getId();
    }

    public void verifyInvitationExisted(UUID invitationId) {
        invitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationException(InvitationExceptionCode.NOT_FOUND_INVITATION));
    }
}
