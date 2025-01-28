package fs19.java.backend.application.dto.workspace;

import com.fasterxml.jackson.annotation.JsonProperty;
import fs19.java.backend.domain.entity.enums.WorkspaceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceResponseDTO {

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier of the workspace")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Schema(type = "string", format = "string", description = "Name of the workspace")
    private String name;

    @Schema(type = "string", format = "string", description = "Description of the workspace")
    private String description;

    @Schema(type = "string", format = "string", description = "Type of the workspace")
    private WorkspaceType type;

    @Schema(type = "string", format = "string", description = "Creation date of the workspace")
    private ZonedDateTime createdDate;

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier of the user who created the workspace")
    private UUID createdBy;

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier of the company")
    private UUID companyId;

    @Schema(type = "string", format = "string", description = "Name of the company")
    private String companyName; // Add company name

    @Schema(type = "string", format = "string", description = "First name of the user who created the workspace")
    private String firstName; // Add first name

    @Schema(type = "string", format = "string", description = "Last name of the user who created the workspace")
    private String lastName; // Add last name
}
