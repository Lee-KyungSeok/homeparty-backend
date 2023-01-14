package invitation.domain.aggregates.invitationcard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "t_invitation_card")
@Entity
@Getter
@Builder
@AllArgsConstructor
@ToString
public class InvitationCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sequence")
    private Long sequence;

    @Column(name = "id", columnDefinition = "varchar(36)", unique = true, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Column(name = "invitation_id", columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID invitationId;

    @Column(name = "uploader_id", columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID uploaderId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "state")
    private InvitationCardState state;

    @Column(name = "file_path")
    private String filePath; // baseUrl 을 제외한 path

    @Column(name = "used_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime usedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    protected InvitationCard() {
    }

    public InvitationCard(
            UUID id,
            UUID uploaderId,
            InvitationCardState state,
            String filePath,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.uploaderId = uploaderId;
        this.state = state;
        this.filePath = filePath;
        this.createdAt = createdAt;
    }

    public static String makeNewFilePath(String extension) {
        return "/invitation-cards/"
                + System.currentTimeMillis()
                + "_"
                + UUID.randomUUID()
                + "."
                + extension;
    }
}