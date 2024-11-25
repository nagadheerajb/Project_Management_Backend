package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvitationJpaRepo extends JpaRepository<Invitation, UUID> {
}
