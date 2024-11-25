package fs19.java.backend.application;

import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.application.dto.task.TaskResponseDTO;
import fs19.java.backend.application.mapper.TaskMapper;
import fs19.java.backend.application.service.TaskService;
import fs19.java.backend.domain.entity.Task;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.infrastructure.TaskRepoImpl;
import fs19.java.backend.presentation.controller.ActivityLogController;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LogManager.getLogger(ActivityLogController.class);
    private final TaskRepoImpl taskRepo;
    private final ActivityLoggerService activityLoggerService;


    public TaskServiceImpl(TaskRepoImpl taskRepo, ActivityLoggerService activityLoggerService) {
        this.taskRepo = taskRepo;
        this.activityLoggerService = activityLoggerService;
    }

    @Override
    public TaskResponseDTO create(TaskRequestDTO taskRequestDTO) {
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
                    logger.info("Assigned User-Not Found {}", taskRequestDTO);
                    return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_LEVEL_ASSIGNED_USER_NOT_FOUND);
                }
                assignedUser = assignedUserById.get();
            }
            Task task = TaskMapper.toTask(taskRequestDTO, createdUserById.get(), assignedUser);
            Task saveTask = taskRepo.save(task);
            activityLoggerService.logActivity(EntityType.TASK, saveTask.getId(), ActionType.CREATED, saveTask.getCreatedUser().getId());
            return TaskMapper.toTaskResponseDTO(saveTask, ResponseStatus.SUCCESSFULLY_CREATED);

        } else {
            logger.info("Created User-Not Found {}", taskRequestDTO);
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
            Task task = taskRepo.update(taskId, taskRequestDTO, assignedUser);
            activityLoggerService.logActivity(EntityType.TASK, task.getId(), ActionType.UPDATED, task.getCreatedUser().getId());
            return TaskMapper.toTaskResponseDTO(task, ResponseStatus.SUCCESSFULLY_UPDATED);

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
        activityLoggerService.logActivity(EntityType.TASK, myTask.getId(), ActionType.DELETED, myTask.getCreatedUser().getId());
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
