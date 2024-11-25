package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.application.dto.task.TaskResponseDTO;
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
     * @param task
     * @param status
     * @return
     */
    public static TaskResponseDTO toTaskResponseDTO(Task task, ResponseStatus status) {
        return new TaskResponseDTO(task.getId(), task.getName(), task.getDescription(),
                task.getCreatedDate(), task.getResolvedDate(), task.getDueDate(),
                task.getAttachments(), task.getTaskStatus(), task.getProjectId(),
                task.getCreatedUser() == null ? null : task.getCreatedUser().getId(),
                task.getAssignedUser() == null ? null : task.getAssignedUser().getId(),
                task.getPriority(), status);
    }

    /**
     *      * Convert task into task response dt list
     * @param tasks
     * @param status
     * @return
     */
    public static List<TaskResponseDTO> toTaskResponseDTOs(List<Task> tasks, ResponseStatus status) {
        List<TaskResponseDTO> responseDTOS = new ArrayList<>();
        tasks.forEach(role -> {
            responseDTOS.add(toTaskResponseDTO(role, status));
        });
        return responseDTOS;
    }

    public static Task toTask(TaskRequestDTO taskRequestDTO, User createduser, User assignedUser) {
        return new Task(UUID.randomUUID(), taskRequestDTO.getName(), taskRequestDTO.getDescription(),
                DateAndTime.getDateAndTime(), taskRequestDTO.getResolvedDate(),
                taskRequestDTO.getDueDate(), taskRequestDTO.getAttachments(),
                taskRequestDTO.getTaskStatus(), taskRequestDTO.getProjectId(),
                createduser, assignedUser, taskRequestDTO.getPriority());
    }
}
