package fs19.java.backend.application;

import fs19.java.backend.application.dto.activitylog.ActivityLogDTO;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.application.service.ActivityLogService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class ActivityLoggerService {

    private final ActivityLogService activityLogService;

    public ActivityLoggerService(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    public void logActivity(EntityType entityType, UUID entityId, ActionType action, UUID userId) {
        ActivityLogDTO activityLogDTO = new ActivityLogDTO();
        activityLogDTO.setEntityType(entityType);
        activityLogDTO.setEntityId(entityId);
        activityLogDTO.setAction(action);
        activityLogDTO.setUserId(userId);
        activityLogDTO.setCreatedDate(ZonedDateTime.now());

        activityLogService.createActivityLog(activityLogDTO);
    }
}
