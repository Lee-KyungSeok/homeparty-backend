package invitation.domain.commands;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import invitation.domain.aggregates.InvitationLocation;
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

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreateInvitationCommandHandlerTest {

    @Autowired
    private InvitationRepository invitationRepository;

    @DisplayName("새로운 초대장을 생성한다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(InvitationLocationCustomizer.class)
    public void sut_create_invitation(
            InvitationLocation location,
            CreateInvitationCommand command
    ) {
        // given
        setCommandLocationInfo(command, location);
        var sut = new CreateInvitationCommandHandler(invitationRepository);

        // when
        sut.handle(command);

        // then
        var actual =  invitationRepository.findById(command.getInvitationId());
        assertThat(actual.get().getHostId()).isEqualTo(command.getHostId());
        assertThat(actual.get().getTitle()).isEqualTo(command.getTitle());
        assertThat(actual.get().getDescription()).isEqualTo(command.getDescription());
        assertThat(actual.get().getType()).isEqualTo(command.getType());
        assertThat(actual.get().getPartiedAt()).isEqualTo(command.getPartiedAt());
        assertThat(actual.get().getLocation())
                .usingRecursiveComparison()
                .isEqualTo(location);
    }

    private static void setCommandLocationInfo(CreateInvitationCommand command, InvitationLocation location) {
        try {
            Field latitude = command.getClass().getDeclaredField("latitude");
            latitude.setAccessible(true);
            latitude.set(command, location.getLatitude());

            Field longitude = command.getClass().getDeclaredField("longitude");
            longitude.setAccessible(true);
            longitude.set(command, location.getLongitude());

            Field locationName = command.getClass().getDeclaredField("locationName");
            locationName.setAccessible(true);
            locationName.set(command, location.getName());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}