package invitation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class Invitation {
    private UUID id;
    private String nickname;

    protected Invitation() {
    }
}
