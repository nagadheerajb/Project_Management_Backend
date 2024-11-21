package fs19.java.backend.domain.abstraction;

import fs19.java.backend.domain.entity.Notification;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository {
    void save(Notification notification);
    Optional<Notification> findById(UUID id);
    List<Notification> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
}