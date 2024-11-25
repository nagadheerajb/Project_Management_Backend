package fs19.java.backend.application;

import fs19.java.backend.application.dto.activitylog.ActivityLogDTO;
import fs19.java.backend.application.mapper.ActivityLogMapper;
import fs19.java.backend.application.service.ActivityLogService;
import fs19.java.backend.domain.entity.ActivityLog;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.JpaRepositories.ActivityLogJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.exception.ActivityLogNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    private static final Logger logger = LogManager.getLogger(ActivityLogServiceImpl.class);

    private static final String ACTIVITY_LOG_NOT_FOUND_MESSAGE = "ActivityLog with ID %s not found";
    private static final String USER_NOT_FOUND_MESSAGE = "User with ID %s not found";

    private final ActivityLogJpaRepo activityLogRepository;
    private final UserJpaRepo userRepository;

    public ActivityLogServiceImpl(ActivityLogJpaRepo activityLogRepository, UserJpaRepo userRepository) {
        this.activityLogRepository = activityLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ActivityLogDTO createActivityLog(ActivityLogDTO activityLogDTO) {
        logger.info("Creating activity log: {}", activityLogDTO);
        User user = userRepository.findById(activityLogDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, activityLogDTO.getUserId())));
        ActivityLog activityLog = ActivityLogMapper.toEntity(activityLogDTO, user);
        activityLog.setCreatedDate(ZonedDateTime.now());
        ActivityLog savedActivityLog = activityLogRepository.save(activityLog);
        logger.info("Activity log created successfully: {}", savedActivityLog);
        return ActivityLogMapper.toDTO(savedActivityLog);
    }

    @Override
    public ActivityLogDTO updateActivityLog(UUID id, ActivityLogDTO activityLogDTO) {
        logger.info("Updating activity log with ID: {} and DTO: {}", id, activityLogDTO);
        ActivityLog existingActivityLog = activityLogRepository.findById(id)
                .orElseThrow(() -> new ActivityLogNotFoundException(String.format(ACTIVITY_LOG_NOT_FOUND_MESSAGE, id)));

        boolean isUpdateProvided = activityLogDTO.getEntityType() != null ||
                activityLogDTO.getEntityId() != null ||
                activityLogDTO.getAction() != null ||
                activityLogDTO.getUserId() != null;

        if (!isUpdateProvided) {
            throw new IllegalArgumentException("At least one field must be provided to update the activity log.");
        }

        if (activityLogDTO.getEntityType() != null) {
            existingActivityLog.setEntityType(activityLogDTO.getEntityType());
        }

        if (activityLogDTO.getEntityId() != null) {
            existingActivityLog.setEntityId(activityLogDTO.getEntityId());
        }

        if (activityLogDTO.getAction() != null) {
            existingActivityLog.setAction(activityLogDTO.getAction());
        }

        if (activityLogDTO.getUserId() != null) {
            User user = userRepository.findById(activityLogDTO.getUserId())
                    .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, activityLogDTO.getUserId())));
            existingActivityLog.setUserId(user);
        }

        ActivityLog savedActivityLog = activityLogRepository.save(existingActivityLog);
        logger.info("Activity log updated successfully: {}", savedActivityLog);
        return ActivityLogMapper.toDTO(savedActivityLog);
    }

    @Override
    public ActivityLogDTO getActivityLogById(UUID id) {
        logger.info("Retrieving activity log with ID: {}", id);
        ActivityLog activityLog = activityLogRepository.findById(id)
                .orElseThrow(() -> new ActivityLogNotFoundException(String.format(ACTIVITY_LOG_NOT_FOUND_MESSAGE, id)));
        logger.info("Activity log retrieved successfully: {}", activityLog);
        return ActivityLogMapper.toDTO(activityLog);
    }

    @Override
    public List<ActivityLogDTO> getAllActivityLogs() {
        logger.info("Retrieving all activity logs");
        List<ActivityLogDTO> activityLogs = activityLogRepository.findAll().stream()
                .map(ActivityLogMapper::toDTO)
                .collect(Collectors.toList());
        logger.info("All activity logs retrieved successfully");
        return activityLogs;
    }

    @Override
    public void deleteActivityLog(UUID id) {
        logger.info("Deleting activity log with ID: {}", id);
        if (!activityLogRepository.existsById(id)) {
            logger.error("Activity log with ID: {} not found for deletion", id);
            throw new ActivityLogNotFoundException(String.format(ACTIVITY_LOG_NOT_FOUND_MESSAGE, id));
        }
        activityLogRepository.deleteById(id);
        logger.info("Activity log with ID: {} deleted successfully", id);
        //User createdBy = SecurityUtils.getCurrentUser();
        //activityLoggerService.logActivity(EntityType.COMPANY, id, ActionType.DELETED, createdBy.getId());
    }
}
