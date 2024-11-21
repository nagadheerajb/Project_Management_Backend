package fs19.java.backend.application.dto.activitylog;

import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLogDTO {
    private UUID id;
    private EntityType entityType;
    private UUID entityId;
    private ActionType action;
    private ZonedDateTime createdDate;
    private UUID userId;
}
