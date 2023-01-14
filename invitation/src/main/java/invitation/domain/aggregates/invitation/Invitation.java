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

    @Column(name = "dress_codes")
    private String dressCodes;

    @Column(name = "foods")
    private String foods;

    @Column(name = "partied_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime partiedAt;

    @Embedded
    private InvitationLocation location;

    protected Invitation() {
    }

    public Invitation(
            UUID id,
            UUID hostId,
            String title,
            String description,
            String type,
            String dressCodes,
            String foods,
            LocalDateTime partiedAt,
            InvitationLocation location
    ) {
        this.id = id;
        this.hostId = hostId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.dressCodes = dressCodes;
        this.foods = foods;
        this.partiedAt = partiedAt;
        this.location = location;
    }
}
