package fs19.java.backend.application.dto.project;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectCreateDTO {
  UUID id;

  @Schema(type = "string", format = "string", description = "Name of the project", example = "Test Project")
  @Size(min = 3, max = 45, message = "Project name must be between 3 and 50 characters")
  @NotNull(message = "Project name is required")
  private String name;

  @Schema(type = "string", format = "string", description = "Description of the project", example = "Test Description")
  @Size(max = 100, message = "Project description must be less than 100 characters")
  private String description;

  @Schema(type = "string", format = "string", description = "Start date of the project")
  @NotNull(message = "Start date is required")
  @FutureOrPresent(message = "Start date must be in the present or future")
  private ZonedDateTime startDate;

  @Schema(type = "string", format = "string", description = "End date of the project")
  @FutureOrPresent(message = "End date must be in the present or future")
  private ZonedDateTime endDate;

  @Schema(type = "uuid", format = "string", description = "User ID of the project creator")
  private UUID createdByUserId;

  @Schema(type = "uuid", format = "string", description = "Workspace ID of the project")
  private UUID workspaceId;

  private Boolean status = true;
}
