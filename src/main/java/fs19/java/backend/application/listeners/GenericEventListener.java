package fs19.java.backend.application.listeners;

import fs19.java.backend.application.dto.notification.NotificationDTO;
import fs19.java.backend.application.service.NotificationService;
import fs19.java.backend.domain.entity.Comment;
import fs19.java.backend.domain.entity.Project;
import fs19.java.backend.domain.entity.Task;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.domain.entity.enums.NotificationType;
import fs19.java.backend.application.events.GenericEvent;
import fs19.java.backend.infrastructure.JpaRepositories.ProjectJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.TaskJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class GenericEventListener {

    private final NotificationService notificationService;
    private final RabbitTemplate rabbitTemplate;
    private final TaskJpaRepo taskRepository;
    private final UserJpaRepo userRepository;
    private final ProjectJpaRepo projectRepository;

    @Autowired
    public GenericEventListener(NotificationService notificationService, RabbitTemplate rabbitTemplate, TaskJpaRepo taskRepository, UserJpaRepo userRepository, ProjectJpaRepo projectRepository) {
        this.notificationService = notificationService;
        this.rabbitTemplate = rabbitTemplate;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @EventListener
    public void handleGenericEvent(GenericEvent<?> event) {
        Object entity = event.getEntity();
        EntityType entityType = event.getEntityType();
        String actionType = event.getActionType();

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotifyType(NotificationType.valueOf(entityType.name() + "_" + actionType.toUpperCase()));
        notificationDTO.setCreatedDate(ZonedDateTime.now());
        notificationDTO.setRead(false);

        // Handle different entity types
        if (entity instanceof Comment comment) {
            Task task = taskRepository.findById(comment.getTaskId().getId()).orElseThrow();
            User createdBy = userRepository.findById(comment.getCreatedBy().getId()).orElseThrow();
            String content = String.format(
                    "**New Comment on Task** \n\n**Task Name:** %s\n**Comment:** %s\n**Commented By:** %s %s\n**Project:** %s",
                    task.getName(),
                    comment.getContent(),
                    createdBy.getFirstName(),
                    createdBy.getLastName(),
                    task.getProject().getName()
            );
            notificationDTO.setContent(content);
            notificationDTO.setProjectId(task.getProject().getId());
            notificationDTO.setMentionedBy(createdBy.getId());
            notificationDTO.setMentionedTo(task.getAssignedUser().getId());
        } else if (entity instanceof Project project) {
            User createdBy = userRepository.findById(project.getCreatedByUser().getId()).orElseThrow();
            String content = String.format(
                    "**New Project Created** \n\n**Project Name:** %s\n**Created By:** %s %s",
                    project.getName(),
                    createdBy.getFirstName(),
                    createdBy.getLastName()
            );
            notificationDTO.setContent(content);
            notificationDTO.setProjectId(project.getId());
            notificationDTO.setMentionedBy(createdBy.getId());
            notificationDTO.setMentionedTo(createdBy.getId());
        } else if (entity instanceof Task task) {
            Project project = projectRepository.findById(task.getProject().getId()).orElseThrow();
            User createdBy = userRepository.findById(task.getCreatedUser().getId()).orElseThrow();
            String content = String.format(
                    "**New Task Created** \n\n**Task Name:** %s\n**Created By:** %s %s\n**Project:** %s",
                    task.getName(),
                    createdBy.getFirstName(),
                    createdBy.getLastName(),
                    project.getName()
            );
            notificationDTO.setContent(content);
            notificationDTO.setProjectId(project.getId());
            notificationDTO.setMentionedBy(createdBy.getId());
            notificationDTO.setMentionedTo(task.getAssignedUser().getId());
        }

        // Publish notification to RabbitMQ
        rabbitTemplate.convertAndSend("generalExchange", "generalRoutingKey", notificationDTO);
        notificationService.createNotification(notificationDTO);
    }
}