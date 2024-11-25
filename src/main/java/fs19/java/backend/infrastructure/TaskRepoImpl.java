package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.domain.abstraction.TaskRepository;
import fs19.java.backend.domain.entity.Task;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.JpaRepositories.TaskJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.exception.PermissionLevelException;
import fs19.java.backend.presentation.shared.exception.TaskLevelException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TaskRepoImpl implements TaskRepository {

    private final TaskJpaRepo taskJpaRepo;
    private final UserJpaRepo userJpaRepo;

    public TaskRepoImpl(TaskJpaRepo taskJpaRepo, UserJpaRepo userJpaRepo) {
        this.taskJpaRepo = taskJpaRepo;
        this.userJpaRepo = userJpaRepo;
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
    public Task update(UUID taskId, TaskRequestDTO taskRequestDTO, User assignedUser) {
        Task task = findById(taskId);
        if (task != null) {
            task.setName(taskRequestDTO.getName());
            task.setDescription(taskRequestDTO.getDescription());
            task.setResolvedDate(taskRequestDTO.getResolvedDate());
            task.setDueDate(taskRequestDTO.getDueDate());
            task.setAttachments(taskRequestDTO.getAttachments());
            task.setPriority(taskRequestDTO.getPriority());
            task.setProjectId(taskRequestDTO.getProjectId());
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
}
