package fs19.java.backend.application;

import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.application.dto.task.TaskResponseDTO;
import fs19.java.backend.application.events.GenericEvent;
import fs19.java.backend.application.mapper.TaskMapper;
import fs19.java.backend.application.service.TaskService;
import fs19.java.backend.config.SecurityConfig;
import fs19.java.backend.domain.entity.Project;
import fs19.java.backend.domain.entity.Task;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.infrastructure.TaskRepoImpl;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);
    private final TaskRepoImpl taskRepo;
    private final ActivityLoggerService activityLoggerService;
    private final ApplicationEventPublisher eventPublisher;


    public TaskServiceImpl(TaskRepoImpl taskRepo, ActivityLoggerService activityLoggerService, ApplicationEventPublisher eventPublisher) {
        this.taskRepo = taskRepo;
        this.activityLoggerService = activityLoggerService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public TaskResponseDTO create(TaskRequestDTO taskRequestDTO) {
        if (taskRequestDTO.getName().isEmpty()) { // expected valid name only and that validation is enough
            logger.info("Task Name is null, cannot proceed with Task creation. {}", taskRequestDTO);
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_NAME_NOT_FOUND);
        }
        Optional<User> createdUserById = taskRepo.findTaskUserByUserId(taskRequestDTO.getCreatedUserId());
        if (createdUserById.isPresent()) {
            User assignedUser = null;
            if (taskRequestDTO.getAssignedUserId() != null) {
                Optional<User> assignedUserById = taskRepo.findTaskUserByUserId(taskRequestDTO.getCreatedUserId());
                if (assignedUserById.isEmpty()) {
                    logger.info("Assigned-User information not found {}", taskRequestDTO);
                    return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_LEVEL_ASSIGNED_USER_NOT_FOUND);
                }
                assignedUser = assignedUserById.get();
            }
            Optional<Project> projectById = taskRepo.findProjectById(taskRequestDTO.getProjectId());
            if (projectById.isPresent()) {
                Task task = TaskMapper.toTask(taskRequestDTO, createdUserById.get(), assignedUser, projectById.get());
                Task saveTask = taskRepo.save(task);
                activityLoggerService.logActivity(EntityType.TASK, saveTask.getId(), ActionType.CREATED, saveTask.getCreatedUser().getId());
                eventPublisher.publishEvent(new GenericEvent<>(this, saveTask, EntityType.TASK, "Created"));
                return TaskMapper.toTaskResponseDTO(saveTask, ResponseStatus.SUCCESSFULLY_CREATED);
            } else {
                logger.info("Project information Found {}", taskRequestDTO);
                return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.PROJECT_ID_NOT_FOUND);
            }
        } else {
            logger.info("Created User information not Found {}", taskRequestDTO);
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_LEVEL_CREATED_USER_NOT_FOUND);
        }
    }

    @Override
    public TaskResponseDTO update(UUID taskId, TaskRequestDTO taskRequestDTO) {
        if (taskId == null) {
            logger.info("Task Id is null, cannot proceed with Task update. {}", taskRequestDTO);
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_ID_NOT_FOUND);
        }
        if (taskRequestDTO.getName().isEmpty()) { // expected valid name only and that validation is enough
            logger.info("Task Name from DTO is null, cannot proceed with Task creation. {}", taskRequestDTO);
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_NAME_NOT_FOUND);
        }
        Optional<User> createdUserById = taskRepo.findTaskUserByUserId(taskRequestDTO.getCreatedUserId());
        if (createdUserById.isPresent()) {
            User assignedUser = null;
            if (taskRequestDTO.getAssignedUserId() != null) {
                Optional<User> assignedUserById = taskRepo.findTaskUserByUserId(taskRequestDTO.getCreatedUserId());
                if (assignedUserById.isEmpty()) {
                    logger.info("Assigned User-Not Found  {}", taskRequestDTO);
                    return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_LEVEL_ASSIGNED_USER_NOT_FOUND);
                }
                assignedUser = assignedUserById.get();
            }
            Optional<Project> projectById = taskRepo.findProjectById(taskRequestDTO.getProjectId());
            if (projectById.isPresent()) {
                Task task = taskRepo.update(taskId, taskRequestDTO, assignedUser, projectById.get());
                activityLoggerService.logActivity(EntityType.TASK, task.getId(), ActionType.UPDATED, task.getCreatedUser().getId());
                eventPublisher.publishEvent(new GenericEvent<>(this, task, EntityType.TASK, "Updated"));
                return TaskMapper.toTaskResponseDTO(task, ResponseStatus.SUCCESSFULLY_UPDATED);
            } else {
                logger.info("Project-Not Found {}", taskRequestDTO);
                return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.PROJECT_ID_NOT_FOUND);
            }

        } else {
            logger.info("Created User-Not Found  {}", taskRequestDTO);
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_LEVEL_CREATED_USER_NOT_FOUND);
        }
    }

    @Override
    public TaskResponseDTO delete(UUID taskId) {
        if (taskId == null) {
            logger.info("Task ID is null, cannot proceed with delete.");
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_ID_NOT_FOUND);
        }
        Task myTask = this.taskRepo.delete(taskId);
        if (myTask == null) {
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.INVALID_INFORMATION_TASK_DETAILS_NOT_FOUND);
        }
        activityLoggerService.logActivity(EntityType.TASK, myTask.getId(), ActionType.DELETED, SecurityConfig.getCurrentUser().getId());
        eventPublisher.publishEvent(new GenericEvent<>(this, myTask, EntityType.TASK, "Deleted"));
        return TaskMapper.toTaskResponseDTO(myTask, ResponseStatus.SUCCESSFULLY_DELETED);
    }

    @Override
    public List<TaskResponseDTO> findAll() {
        return TaskMapper.toTaskResponseDTOs(taskRepo.findAll(), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    @Override
    public TaskResponseDTO getById(UUID taskId) {
        if (taskId == null) {
            logger.info("Task ID is null, cannot proceed with search.");
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_ID_NOT_FOUND);
        }
        Task myTask = taskRepo.findById(taskId);
        if (myTask == null) {
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.INVALID_INFORMATION_TASK_DETAILS_NOT_FOUND);
        }
        return TaskMapper.toTaskResponseDTO(myTask, ResponseStatus.SUCCESSFULLY_FOUND);
    }

    @Override
    public List<TaskResponseDTO> getByAssignedId(UUID userId) {
        return TaskMapper.toTaskResponseDTOs(taskRepo.findByAssignedUserId(userId), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    @Override
    public List<TaskResponseDTO> getByCreatedUserId(UUID createdUserId) {
        return TaskMapper.toTaskResponseDTOs(taskRepo.findByCreatedUserId(createdUserId), ResponseStatus.SUCCESSFULLY_FOUND);
    }
}
