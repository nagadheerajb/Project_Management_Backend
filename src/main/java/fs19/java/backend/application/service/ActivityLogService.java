package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.activitylog.ActivityLogDTO;
import java.util.List;
import java.util.UUID;

public interface ActivityLogService {
    ActivityLogDTO createActivityLog(ActivityLogDTO activityLogDTO);
    ActivityLogDTO updateActivityLog(UUID id, ActivityLogDTO activityLogDTO);
    ActivityLogDTO getActivityLogById(UUID id);
    List<ActivityLogDTO> getAllActivityLogs();
    void deleteActivityLog(UUID id);
}
