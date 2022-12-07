package invitation.domain.commands;

import invitation.domain.aggregates.Invitation;
import invitation.domain.aggregates.InvitationLocation;
import invitation.domain.aggregates.InvitationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateInvitationCommandHandler {

    private final InvitationRepository invitationRepository;

    public void handle(CreateInvitationCommand command) {
        Invitation invitation = new Invitation(
                command.getInvitationId(),
                command.getHostId(),
                command.getTitle(),
                command.getDescription(),
                command.getType(),
                command.getPartiedAt(),
                new InvitationLocation(
                        command.getLocationName(),
                        command.getLatitude(),
                        command.getLongitude()
                )
        );
        invitationRepository.save(invitation);
    }

}
