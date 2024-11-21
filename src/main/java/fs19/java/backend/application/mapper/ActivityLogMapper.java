package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.activitylog.ActivityLogDTO;
import fs19.java.backend.domain.entity.ActivityLog;

public class ActivityLogMapper {

    public static ActivityLog toEntity(ActivityLogDTO activityLogDTO) {
        return new ActivityLog(
                activityLogDTO.getId(),
                activityLogDTO.getEntityType(),
                activityLogDTO.getEntityId(),
                activityLogDTO.getAction(),
                activityLogDTO.getCreatedDate(),
                activityLogDTO.getUserId()
        );
    }

    public static ActivityLogDTO toDTO(ActivityLog activityLog) {
        return new ActivityLogDTO(
                activityLog.getId(),
                activityLog.getEntityType(),
                activityLog.getEntityId(),
                activityLog.getAction(),
                activityLog.getCreatedDate(),
                activityLog.getUserId()
        );
    }
}
