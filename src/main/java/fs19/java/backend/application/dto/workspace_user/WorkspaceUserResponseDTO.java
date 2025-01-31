package fs19.java.backend.application.dto.workspace_user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkspaceUserResponseDTO {
  @Schema(type = "uuid", format = "uuid", description = "Unique identifier")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UUID id;

  @Schema(type = "uuid", format = "uuid", description = "Unique identifier for role-Id")
  @NotNull
  private UUID roleId;

  @Schema(type = "uuid", format = "uuid", description = "Unique identifier for workspace-Id")
  @NotNull
  private UUID workspaceId;

  @Schema(type = "uuid", format = "uuid", description = "Unique identifier for user-Id")
  @NotNull
  private UUID userId;

  @Schema(description = "Name of the workspace")
  private String workspaceName;

  @Schema(type = "uuid", format = "uuid", description = "Unique identifier for company-Id")
  private UUID companyId;

  @Schema(description = "Name of the company")
  private String companyName;

 }
