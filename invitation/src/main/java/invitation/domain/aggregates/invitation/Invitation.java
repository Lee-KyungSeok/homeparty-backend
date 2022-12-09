package invitation.domain.aggregates.invitation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "t_invitation")
@Entity
@Getter
@Builder
@AllArgsConstructor
@ToString
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sequence")
    private Long sequence;

    @Column(name = "id", columnDefinition = "varchar(36)", unique = true, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Column(name = "host_id", columnDefinition = "varchar(36)", nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID hostId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "partied_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime partiedAt;

    @Embedded
    private InvitationLocation location;

    // Todo 추후에 card 관련사항 추가 + 아래는 기획 보고 결정할 것
//    @Column(name = "dress_codes")
//    private List<String> dressCodes;
//
//    @Column(name = "foods")
//    private List<String> foods;

    protected Invitation() {
    }

    public Invitation(UUID id, UUID hostId, String title, String description, String type, LocalDateTime partiedAt, InvitationLocation location) {
        this.id = id;
        this.hostId = hostId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.partiedAt = partiedAt;
        this.location = location;
    }
}
