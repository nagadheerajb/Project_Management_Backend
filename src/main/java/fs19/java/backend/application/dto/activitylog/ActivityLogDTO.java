package fs19.java.backend.application.dto.activitylog;

import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Entity type cannot be null")
    private EntityType entityType;

    @NotNull(message = "Entity ID cannot be null")
    private UUID entityId;

    @NotNull(message = "Action cannot be null")
    private ActionType action;

    private ZonedDateTime createdDate;

    @NotNull(message = "User ID cannot be null")
    private UUID userId;
}
