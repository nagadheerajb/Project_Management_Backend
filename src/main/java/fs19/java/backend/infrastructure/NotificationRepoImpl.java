package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.abstraction.NotificationRepository;
import fs19.java.backend.domain.entity.Notification;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class NotificationRepoImpl implements NotificationRepository {

    private final Map<UUID, Notification> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public void save(Notification notification) {
        inMemoryDatabase.put(notification.getId(), notification);
    }

    @Override
    public Optional<Notification> findById(UUID id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }

    @Override
    public List<Notification> findAll() {
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