package com.homeparty.invitation.domain.aggregates.invitationcard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvitationCardRepository extends JpaRepository<InvitationCard, Long> {
    Optional<InvitationCard> findById(UUID id);
}
