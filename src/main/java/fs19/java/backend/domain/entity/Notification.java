package fs19.java.backend.domain.entity;

import fs19.java.backend.domain.entity.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private UUID id;

    private String content;

    private NotificationType notifyType;

    private ZonedDateTime createdDate;

    private boolean isRead;

    private UUID projectId;

    private UUID mentionedBy;

    private UUID mentionedTo;
}