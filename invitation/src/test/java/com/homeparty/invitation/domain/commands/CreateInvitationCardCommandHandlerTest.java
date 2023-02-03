package com.homeparty.invitation.domain.commands;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCardRepository;
import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCardState;
import com.homeparty.invitation.testing.InvitationLocationCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreateInvitationCardCommandHandlerTest {

    @Autowired
    private InvitationCardRepository invitationCardRepository;

    @DisplayName("새로운 초대장 카드를 생성한다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(InvitationLocationCustomizer.class)
    public void sut_create_invitation(
            CreateInvitationCardCommand command
    ) {
        // given
        var sut = new CreateInvitationCardCommandHandler(invitationCardRepository);

        // when
        sut.handle(command);

        // then
        var actual =  invitationCardRepository.findById(command.getCardId());
        assertThat(actual.get().getInvitationId()).isNull();
        assertThat(actual.get().getUploaderId()).isEqualTo(command.getUploaderId());
        assertThat(actual.get().getState()).isEqualTo(InvitationCardState.CREATED);
    }
}