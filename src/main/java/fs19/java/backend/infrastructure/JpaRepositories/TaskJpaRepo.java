package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskJpaRepo extends JpaRepository<Task, UUID> {
}
