package fs19.java.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import fs19.java.backend.config.StringListConverter;
import fs19.java.backend.domain.entity.enums.ValidPriority;
import fs19.java.backend.domain.entity.enums.ValidTaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Responsible to work as a base model for a task object
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "task", indexes = {
        @Index(name = "idx_task_project_id", columnList = "project_id"),
        @Index(name = "idx_task_created_user", columnList = "createduser_id"),
        @Index(name = "idx_task_assigned_user", columnList = "assigneduser_id")
})
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;
    @NotNull
    @Column(nullable = false, length = 45)
    private String name;
    @Column(length = 500)
    private String description;

    @NotNull
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime createdDate;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime resolvedDate;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime dueDate;

    @Column(name = "attachments")
    @Convert(converter = StringListConverter.class)
    private List<String> attachments;

    @Column(nullable = false)
    @ValidTaskStatus
    private String taskStatus;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_Id", nullable = false)
    private Project project;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "createduser_id", nullable = false)
    private User createdUser;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assigneduser_id", nullable = false)
    private User assignedUser;
    @NotNull
    @Column(nullable = false)
    @ValidPriority
    private String priority;

}


