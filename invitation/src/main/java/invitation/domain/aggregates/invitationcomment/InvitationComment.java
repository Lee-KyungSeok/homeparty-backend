package invitation.domain.aggregates.invitationcomment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "t_invitation_comment")
@Entity
@Getter
@Builder
@AllArgsConstructor
@ToString
public class InvitationComment {
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

    @Column(name = "user_id", columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID userId;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "is_secret")
    private Boolean isSecret;

    @Column(name = "commented_at")
    private LocalDateTime commentedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    protected InvitationComment() {
    }

    @ConstructorProperties({"id", "userId", "name"})
    public InvitationComment(
            UUID id,
            UUID invitationId,
            UUID userId,
            String name,
            String content,
            Boolean isSecret,
            LocalDateTime commentedAt
    ) {
        this.id = id;
        this.invitationId = invitationId;
        this.userId = userId;
        this.name = name;
        this.content = content;
        this.isSecret = isSecret;
        this.commentedAt = commentedAt;
        this.isDeleted = false;
    }
}