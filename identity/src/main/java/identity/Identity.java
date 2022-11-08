package identity;

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


    @Column(name = "provider", columnDefinition = "varchar(50)")
    private String provider;

    protected Identity() {
    }

    public Identity(UUID id, String provider) {
        this.id = id;
        this.provider = provider;
    }
}
