package fs19.java.backend.application.dto.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import fs19.java.backend.domain.entity.enums.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Responsible to handle the permission Requests
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PermissionRequestDTO {

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @NotNull(message = "Permission-Name cannot be null")
    @Schema(type = "string", format = "string", description = "permission name defines here")
    private String name;
    @NotNull(message = "Permission-url cannot be null")
    @Schema(type = "string", format = "string", description = "permission url defines here")
    private String permissionUrl;
    @NotNull(message = "Permission type cannot be null")
    @Schema(type = "enum", format = "enum", description = "permission type defines here")
    private PermissionType permissionType;
    @Schema(type = "uuid", format = "uuid", description = "Permission creating user Id")
    @NotNull(message = "User id cannot be null")
    private UUID created_user;

}
