package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.abstraction.ActivityLogRepository;
import fs19.java.backend.domain.entity.ActivityLog;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ActivityLogRepoImpl implements ActivityLogRepository {

    private final Map<UUID, ActivityLog> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public void save(ActivityLog activityLog) {
        inMemoryDatabase.put(activityLog.getId(), activityLog);
    }

    @Override
    public Optional<ActivityLog> findById(UUID id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }

    @Override
    public List<ActivityLog> findAll() {
        return new ArrayList<>(inMemoryDatabase.values());
    }

    @Override
    public void deleteById(UUID id) {
        inMemoryDatabase.remove(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return inMemoryDatabase.containsKey(id);
    }
}
