package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.application.dto.task.TaskResponseDTO;
import fs19.java.backend.domain.entity.Project;
import fs19.java.backend.domain.entity.Task;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import fs19.java.backend.presentation.shared.status.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskMapper {


    /**
     * Convert task into task response dto
     *
     * @param task   Task
     * @param status ResponseStatus
     * @return TaskResponseDTO
     */
    public static TaskResponseDTO toTaskResponseDTO(Task task, ResponseStatus status) {
        return new TaskResponseDTO(task.getId(), task.getName(), task.getDescription(),
                task.getCreatedDate(), task.getResolvedDate(), task.getDueDate(),
                task.getAttachments(), task.getTaskStatus(), task.getProject() == null ? null : task.getProject().getId(),
                task.getCreatedUser() == null ? null : task.getCreatedUser().getId(),
                task.getAssignedUser() == null ? null : task.getAssignedUser().getId(),
                task.getPriority(), status);
    }

    /**
     * * Convert task into task response dt list
     *
     * @param tasks  List<Task>
     * @param status ResponseStatus
     * @return List<TaskResponseDTO>
     */
    public static List<TaskResponseDTO> toTaskResponseDTOs(List<Task> tasks, ResponseStatus status) {
        List<TaskResponseDTO> responseDTOS = new ArrayList<>();
        tasks.forEach(role -> responseDTOS.add(toTaskResponseDTO(role, status)));
        return responseDTOS;
    }

    /**
     * Covert to a Task entity object
     * @param taskRequestDTO TaskRequestDTO
     * @param createUser    User
     * @param assignedUser   User
     * @param project        Project
     * @return Task
     */
    public static Task toTask(TaskRequestDTO taskRequestDTO, User createUser, User assignedUser, Project project) {
        return new Task(UUID.randomUUID(), taskRequestDTO.getName(), taskRequestDTO.getDescription(),
                DateAndTime.getDateAndTime(), taskRequestDTO.getResolvedDate(),
                taskRequestDTO.getDueDate(), taskRequestDTO.getAttachments(),
                taskRequestDTO.getTaskStatus(), project,
                createUser, assignedUser, taskRequestDTO.getPriority());
    }
}
