package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.notification.NotificationDTO;
import java.util.List;
import java.util.UUID;

public interface NotificationService {
    NotificationDTO createNotification(NotificationDTO notificationDTO);
    NotificationDTO updateNotification(UUID id, NotificationDTO notificationDTO);
    NotificationDTO getNotificationById(UUID id);
    List<NotificationDTO> getAllNotifications();
    void deleteNotification(UUID id);
}