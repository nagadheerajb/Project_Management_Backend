package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkspaceJpaRepo extends JpaRepository<Workspace, UUID> {
}