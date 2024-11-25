package fs19.java.backend.application.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * This DTO will responsible to handle the request level of role-permission related changes
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolePermissionRequestDTO {

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @Schema(type = "uuid", format = "uuid", description = "Unique identifier for permission-Id")
    @NotNull
    private UUID permissionId;
    @Schema(type = "uuid", format = "uuid", description = "Unique identifier for role-Id")
    @NotNull
    private UUID roleId;
    @Schema(type = "uuid", format = "uuid", description = "Role creating user Id")
    @NotNull(message = "User id cannot be null")
    private UUID created_user;
}
