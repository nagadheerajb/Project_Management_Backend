package fs19.java.backend.application.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

/**
 * This DTO will responsible to handle the request level of role related changes
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleRequestDTO {

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @Schema(type = "string", format = "string", description = "System roles name defines here")
    @NotNull(message = "Role-Name cannot be null")
    @Size(max = 45, message = "Field must be at most 45 characters")
    private String name;
    @NotNull(message = "Company-Id cannot be null")
    @Schema(type = "string", format = "string", description = "Company Id defines here")
    private UUID companyId;
    @Schema(type = "uuid", format = "uuid", description = "Role creating user Id")
    @NotNull(message = "User id cannot be null")
    private UUID created_user;
}
