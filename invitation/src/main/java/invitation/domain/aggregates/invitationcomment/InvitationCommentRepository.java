package invitation.domain.aggregates.invitationcomment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvitationCommentRepository extends JpaRepository<InvitationComment, Long> {
    Optional<InvitationComment> findById(UUID id);
}
