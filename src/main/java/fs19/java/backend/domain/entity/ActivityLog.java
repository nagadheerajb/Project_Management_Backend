package fs19.java.backend.domain.entity;

import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activity_log")
@Builder
public class ActivityLog {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false, length = 45)
    private EntityType entityType;
    @Column
    private UUID entityId;
    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private ActionType action;
    @Column(name = "created_date",length = 45)
    private ZonedDateTime createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User userId;
}