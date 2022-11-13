package identity.domain.aggregates.identity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Table(name = "t_identity")
@Entity
@Getter
@Builder
@AllArgsConstructor
@ToString
@Slf4j
public class Identity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sequence")
    private Long sequence;

    @Column(name = "id", columnDefinition = "varchar(36)", unique = true, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Column(name = "nickname", columnDefinition = "VARCHAR(50)")
    private String nickname;

    @Column(name = "email", columnDefinition = "VARCHAR(128)")
    private String email;

    @Embedded
    private SocialProvider provider;

    protected Identity() {
    }

    public Identity(UUID id, String nickname, String email, SocialProvider provider) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.provider = provider;
    }
}
