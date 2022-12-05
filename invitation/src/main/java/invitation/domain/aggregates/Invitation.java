package invitation.domain.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class Invitation {
    private Long sequence;

    private UUID id;g

    private String title;

    private String type;

    private LocalDateTime partiedAt;

    private String location;

    private String description;

    private List<String> dressCodes;

    private List<String> foods;

    // Todo 추후에 card 관련사항 추가되어야 함.

    protected Invitation() {
    }
}
