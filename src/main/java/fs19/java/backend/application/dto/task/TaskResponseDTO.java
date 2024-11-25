package fs19.java.backend.application.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import fs19.java.backend.domain.entity.enums.Priority;
import fs19.java.backend.domain.entity.enums.TaskStatus;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @Schema(type = "string", format = "string", description = "Task name defines here")
    @NotNull(message = "Task name cannot be null")
    @Size(min = 1, max = 45, message = "Role name must be between 1 and 45 characters")
    private String name;
    @Schema(type = "string", format = "string", description = "Task description define here")
    private String description;
    @Schema(type = "date", format = "date", description = "Task created date define here")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull(message = "Task created-date cannot be null")
    private ZonedDateTime createdDate;
    @Schema(type = "date", format = "date", description = "Task resolved date define here")
    private ZonedDateTime resolvedDate;
    @Schema(type = "date", format = "date", description = "Task due date define here")
    private ZonedDateTime dueDate;
    @Schema(type = "Attachment", format = "Object", description = "Task attachments can add here")
    private List<String> attachment;
    @Schema(type = "Enum", format = "Enum", description = "TaskStatus define here")
    private String taskStatus;
    @Schema(type = "uuid", format = "uuid", description = "Unique project id define here")
    private UUID projectId;
    @Schema(type = "uuid", format = "uuid", description = "Unique created user id define here")
    @NotNull(message = "Task created-user cannot be null")
    private UUID createdUserId;
    @Schema(type = "uuid", format = "uuid", description = "Unique assign user id define here")
    private UUID assignedUserId;
    @Schema(type = "Enum", format = "Enum", description = "Task priority define here")
    private String priority;
    @Schema(type = "String", format = "ResponseStatus", description = "Unique system status")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ResponseStatus status;
}
