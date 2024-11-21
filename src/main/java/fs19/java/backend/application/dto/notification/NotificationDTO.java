package fs19.java.backend.application.dto.notification;

import fs19.java.backend.domain.entity.enums.NotificationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private UUID id;

    @NotNull(message = "Content cannot be null")
    @Size(min = 1, max = 255, message = "Content must be between 1 and 255 characters")
    private String content;

    @NotNull(message = "Notification type cannot be null")
    private NotificationType notifyType;

    private ZonedDateTime createdDate;

    private boolean isRead;

    private UUID projectId;

    private UUID mentionedBy;

    private UUID mentionedTo;
}