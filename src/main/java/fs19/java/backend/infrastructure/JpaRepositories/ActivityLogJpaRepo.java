package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActivityLogJpaRepo extends JpaRepository<ActivityLog, UUID> {
}