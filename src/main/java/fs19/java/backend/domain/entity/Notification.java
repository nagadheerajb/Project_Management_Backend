package fs19.java.backend.domain.entity;

import fs19.java.backend.domain.entity.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String content;

    @Column
    private NotificationType notifyType;

    @Column
    private ZonedDateTime createdDate;

    @Column
    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentioned_by_user_id")
    private User mentionedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentioned_to_user_id")
    private User mentionedTo;
}
