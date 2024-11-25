package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.WorkspaceUser;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceUserJpaRepo extends JpaRepository<WorkspaceUser, UUID> {
}
