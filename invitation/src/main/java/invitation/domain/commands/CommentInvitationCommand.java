package invitation.domain.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class CommentInvitationCommand {
    private UUID commentId;
    private UUID invitationId;
    private UUID userId;
    private String name;
    private String content;
    private boolean isSecret;
}
