package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.activitylog.ActivityLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ActivityLogService {
    ActivityLogDTO createActivityLog(ActivityLogDTO activityLogDTO);
    ActivityLogDTO updateActivityLog(UUID id, ActivityLogDTO activityLogDTO);
    ActivityLogDTO getActivityLogById(UUID id);
    List<ActivityLogDTO> getAllActivityLogs();
    void deleteActivityLog(UUID id);
    List<ActivityLogDTO> getActivityLogsByEntity(UUID entityId);
}
