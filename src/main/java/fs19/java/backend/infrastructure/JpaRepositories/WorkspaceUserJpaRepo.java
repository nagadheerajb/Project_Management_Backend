package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.WorkspaceUser;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceUserJpaRepo extends JpaRepository<WorkspaceUser, UUID> {
    List<WorkspaceUser> findByUser(User user);
}
