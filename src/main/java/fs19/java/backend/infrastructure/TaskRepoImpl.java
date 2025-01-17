package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.domain.abstraction.TaskRepository;
import fs19.java.backend.domain.entity.Project;
import fs19.java.backend.domain.entity.Task;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.JpaRepositories.ProjectJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.TaskJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.exception.PermissionLevelException;
import fs19.java.backend.presentation.shared.exception.TaskLevelException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TaskRepoImpl implements TaskRepository {

    private final TaskJpaRepo taskJpaRepo;
    private final UserJpaRepo userJpaRepo;
    private final ProjectJpaRepo projectJpaRepo;

    public TaskRepoImpl(TaskJpaRepo taskJpaRepo, UserJpaRepo userJpaRepo, ProjectJpaRepo projectJpaRepo) {
        this.taskJpaRepo = taskJpaRepo;
        this.userJpaRepo = userJpaRepo;
        this.projectJpaRepo = projectJpaRepo;
    }

    @Override
    public Task save(Task task) {
        try {
            return taskJpaRepo.save(task);
        } catch (Exception e) {
            throw new PermissionLevelException(e.getLocalizedMessage() + " : " + TaskLevelException.TASK_CREATE);
        }
    }

    @Override
    public Task update(UUID taskId, TaskRequestDTO taskRequestDTO, User assignedUser, Project project) {
        Task task = findById(taskId);
        if (task != null) {
            task.setName(taskRequestDTO.getName());
            task.setDescription(taskRequestDTO.getDescription());

            // Convert LocalDate to ZonedDateTime
            if (taskRequestDTO.getResolvedDate() != null) {
                task.setResolvedDate(taskRequestDTO.getResolvedDate().atStartOfDay(ZoneId.systemDefault()));
            }
            if (taskRequestDTO.getDueDate() != null) {
                task.setDueDate(taskRequestDTO.getDueDate().atTime(23, 59, 59).atZone(ZoneId.systemDefault()));
            }

            task.setAttachments(taskRequestDTO.getAttachments());
            task.setPriority(taskRequestDTO.getPriority());
            task.setProject(project);
            task.setAssignedUser(assignedUser);
            return taskJpaRepo.save(task);
        } else {
            throw new PermissionLevelException(" DB is empty: " + TaskLevelException.TASK_UPDATE);
        }
    }


    @Override
    public Task delete(UUID taskId) {
        Task task = findById(taskId);
        if (task != null) {
            taskJpaRepo.delete(task);
            return task;
        } else {
            throw new PermissionLevelException(" DB is empty : " + TaskLevelException.TASK_DELETE);
        }
    }

    @Override
    public List<Task> findAll() {
        return taskJpaRepo.findAll();
    }

    @Override
    public Task findById(UUID taskId) {
        Optional<Task> byId = taskJpaRepo.findById(taskId);
        return byId.orElse(null);
    }

    @Override
    public List<Task> findByAssignedUserId(UUID userId) {

        if (userId == null) {
            return null;
        }
        Task task = new Task();
        task.setAssignedUser(userJpaRepo.findById(userId).orElse(null));

        Example<Task> example = Example.of(task,
                ExampleMatcher.matchingAll()
                        .withIgnoreNullValues()
                        .withMatcher("assigneduser_id", ExampleMatcher.GenericPropertyMatchers.exact()));

        return taskJpaRepo.findAll(example);
    }

    @Override
    public List<Task> findByCreatedUserId(UUID userId) {

        if (userId == null) {
            return null;
        }
        Task task = new Task();
        task.setCreatedUser(userJpaRepo.findById(userId).orElse(null));

        Example<Task> example = Example.of(task,
                ExampleMatcher.matchingAll()
                        .withIgnoreNullValues()
                        .withMatcher("createduser_id", ExampleMatcher.GenericPropertyMatchers.exact()));

        return taskJpaRepo.findAll(example);
    }

    /**
     * Responsible to return user according to user Id
     * @param userId
     * @return
     */
    public Optional<User> findTaskUserByUserId(UUID userId) {
        return userJpaRepo.findById(userId);
    }

    public Optional<Project> findProjectById(UUID companyId) {
        return projectJpaRepo.findById(companyId);

    }
    @Override
    public List<Task> findTasksByProjectId(UUID projectId) {
        return taskJpaRepo.findByProjectId(projectId);
    }

}
