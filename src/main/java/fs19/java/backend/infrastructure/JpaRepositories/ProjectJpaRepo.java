package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.Project;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectJpaRepo extends JpaRepository<Project, UUID> {
    List<Project> findByWorkspaceId(UUID workspaceId);
}

