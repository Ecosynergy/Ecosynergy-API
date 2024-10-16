package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.Invite;
import app.ecosynergy.api.models.InviteStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    List<Invite> findBySenderId(Long senderId);

    List<Invite> findByRecipientId(Long recipientId);

    List<Invite> findByTeamId(Long teamId);

    List<Invite> findByStatus(InviteStatus status);

    List<Invite> findByRecipientIdAndStatus(Long recipientId, InviteStatus status);

    Optional<Invite> findByTeamIdAndRecipientIdAndStatus(Long teamId, Long recipientId, InviteStatus status);

    boolean existsBySenderIdAndRecipientIdAndTeamIdAndStatus(Long senderId, Long recipientId, Long teamId, InviteStatus status);
}
