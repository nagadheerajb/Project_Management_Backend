package fs19.java.backend.application.dto.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import fs19.java.backend.domain.entity.enums.PermissionType;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Responsible to handle the permission Responses
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionResponseDTO {

    @Schema(type = "string", format = "uuid", description = "Unique identifier")
    private UUID id;
    @NotNull(message = "permission name cannot be null")
    @Size(min = 1, max = 45, message = "permission name must be between 1 and 45 characters")
    private String name;
    @NotNull(message = "Permission-url cannot be null")
    @Schema(type = "string", format = "string", description = "permission url defines here")
    private String permissionUrl;
    @NotNull(message = "Permission type cannot be null")
    @Schema(type = "enum", format = "enum", description = "permission type defines here")
    private PermissionType permissionType;
    @Schema(type = "String", format = "ResponseStatus", description = "Unique system status")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ResponseStatus status;
}
