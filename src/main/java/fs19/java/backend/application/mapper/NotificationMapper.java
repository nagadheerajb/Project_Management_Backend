package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.notification.NotificationDTO;
import fs19.java.backend.domain.entity.Notification;

public class NotificationMapper {

    public static Notification toEntity(NotificationDTO notificationDTO) {
        return new Notification(
                notificationDTO.getId(),
                notificationDTO.getContent(),
                notificationDTO.getNotifyType(),
                notificationDTO.getCreatedDate(),
                notificationDTO.isRead(),
                notificationDTO.getProjectId(),
                notificationDTO.getMentionedBy(),
                notificationDTO.getMentionedTo()
        );
    }

    public static NotificationDTO toDTO(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getContent(),
                notification.getNotifyType(),
                notification.getCreatedDate(),
                notification.isRead(),
                notification.getProjectId(),
                notification.getMentionedBy(),
                notification.getMentionedTo()
        );
    }
}