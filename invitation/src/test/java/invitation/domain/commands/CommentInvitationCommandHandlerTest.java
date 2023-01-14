package invitation.domain.commands;

import autoparams.AutoSource;
import autoparams.CsvAutoSource;
import autoparams.customization.Customization;
import invitation.domain.aggregates.invitation.Invitation;
import invitation.domain.aggregates.invitation.InvitationRepository;
import invitation.domain.aggregates.invitationcomment.InvitationCommentRepository;
import invitation.domain.exception.InvitationException;
import invitation.domain.exception.InvitationExceptionCode;
import invitation.testing.InvitationLocationCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentInvitationCommandHandlerTest {

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private InvitationCommentRepository invitationCommentRepository;

    @DisplayName("초대장이 존재하지 않으면 에러를 반환한다.")
    @ParameterizedTest
    @AutoSource()
    public void sut_fails_invitation_not_found(CommentInvitationCommand command) {
        // given
        var sut = new CommentInvitationCommandHandler(invitationRepository, invitationCommentRepository);

        // when
        InvitationException actual = assertThrows(InvitationException.class, () -> sut.handle(command));

        // then
        assertThat(actual).isEqualTo(new InvitationException(InvitationExceptionCode.NOT_FOUND_INVITATION));
    }

    // Todo: 캐시 때문에 잘못된 entity 인데도 통과 되었다.
    //  repository 를 바꿔줘야 할 듯 싶다.
    // https://stackoverflow.com/questions/65011057/my-jpa-repository-request-is-returning-with-cache-without-me-enabling-it-in-spri
    @DisplayName("초대장 에 댓글을 단다.")
    @ParameterizedTest
    @CsvAutoSource({
            "false",
            "true"
    })
    @Customization(InvitationLocationCustomizer.class)
    public void sut_create_invitation(
            boolean isSecret,
            UUID commentId,
            UUID userId,
            String name,
            String content,
            Invitation invitation
    ) {
        // given
        var command = new CommentInvitationCommand(commentId, invitation.getId(), userId, name, content, isSecret);
        invitationRepository.save(invitation);
        var sut = new CommentInvitationCommandHandler(invitationRepository, invitationCommentRepository);

        // when
        sut.handle(command);

        // then
        var actual =  invitationCommentRepository.findById(command.getCommentId()).get();

        assertThat(actual.getUserId()).isEqualTo(command.getUserId());
        assertThat(actual.getInvitationId()).isEqualTo(command.getInvitationId());
        assertThat(actual.getName()).isEqualTo(command.getName());
        assertThat(actual.getContent()).isEqualTo(command.getContent());
        assertThat(actual.getIsSecret()).isEqualTo(command.isSecret());
        assertThat(actual.getCommentedAt()).isNotNull();
        assertThat(actual.getIsDeleted()).isEqualTo(false);
        assertThat(actual.getDeletedAt()).isNull();
    }
}