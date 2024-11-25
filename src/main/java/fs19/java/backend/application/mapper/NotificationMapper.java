package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.notification.NotificationDTO;
import fs19.java.backend.domain.entity.Notification;
import fs19.java.backend.domain.entity.Project;
import fs19.java.backend.domain.entity.User;

public class NotificationMapper {

    public static Notification toEntity(NotificationDTO notificationDTO, Project project, User mentionedBy, User mentionedTo) {
        Notification notification = new Notification();
        notification.setId(notificationDTO.getId());
        notification.setContent(notificationDTO.getContent());
        notification.setNotifyType(notificationDTO.getNotifyType());
        notification.setCreatedDate(notificationDTO.getCreatedDate());
        notification.setRead(notificationDTO.isRead());
        notification.setProjectId(project);
        notification.setMentionedBy(mentionedBy);
        notification.setMentionedTo(mentionedTo);
        return notification;
    }

    public static NotificationDTO toDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setContent(notification.getContent());
        dto.setNotifyType(notification.getNotifyType());
        dto.setCreatedDate(notification.getCreatedDate());
        dto.setRead(notification.isRead());
        dto.setProjectId(notification.getProjectId().getId());
        dto.setMentionedBy(notification.getMentionedBy().getId());
        dto.setMentionedTo(notification.getMentionedTo().getId());
        return dto;
    }
}
