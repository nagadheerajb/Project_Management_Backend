package fs19.java.backend.domain.abstraction;

import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.domain.entity.Project;
import fs19.java.backend.domain.entity.Task;
import fs19.java.backend.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface TaskRepository {

    Task save(Task task);
    Task update(UUID taskId, TaskRequestDTO taskRequestDTO, User assignedUser, Project project);
    Task delete(UUID taskId);
    List<Task> findAll();
    Task findById(UUID taskId);
    List<Task> findByAssignedUserId(UUID userId);
    List<Task> findByCreatedUserId(UUID createdUserId);


}
