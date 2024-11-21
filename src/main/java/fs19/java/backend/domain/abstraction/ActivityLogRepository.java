package fs19.java.backend.domain.abstraction;

import fs19.java.backend.domain.entity.ActivityLog;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActivityLogRepository {
    void save(ActivityLog activityLog);
    Optional<ActivityLog> findById(UUID id);
    List<ActivityLog> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
}