package identity.domain;

import identity.domain.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IdentityRepository extends JpaRepository<Identity, Long> {
    Optional<Identity> findById(UUID id);
}
