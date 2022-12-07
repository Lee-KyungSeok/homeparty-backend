package invitation.domain.repositories;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import invitation.domain.aggregates.Invitation;
import invitation.domain.aggregates.InvitationRepository;
import invitation.testing.InvitationLocationCustomizer;
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
class InvitationRepositoryTest {

    @Autowired
    private InvitationRepository invitationRepository;

    @DisplayName("invitation 를 저장한다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(InvitationLocationCustomizer.class)
    public void Sut_save_Invitation(Invitation invitation) {

        // when
        invitationRepository.save(invitation);

        // then
        var actual = invitationRepository.findById(invitation.getId());
        assertThat(actual.get())
                .usingRecursiveComparison()
                .ignoringFields("sequence")
                .isEqualTo(invitation);
    }
}