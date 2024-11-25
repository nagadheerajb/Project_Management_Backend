package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationJpaRepo extends JpaRepository<Notification, UUID> {
}