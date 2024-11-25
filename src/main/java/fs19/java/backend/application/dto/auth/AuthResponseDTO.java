package fs19.java.backend.application.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier")
    private UUID id;

    @Schema(type = "string", format = "string", description = "First name of the user")
    @NotNull(message = "First name is required")
    private String firstName;

    @Schema(type = "string", format = "string", description = "Last name of the user")
    @NotNull(message = "Last name is required")
    private String lastName;

    @Schema(type = "string", format = "string", description = "Email of the user")
    @NotNull(message = "Email is required")
    private String email;

    @Schema(type = "string", format = "string", description = "Password of the user")
    private String phone;

    @Schema(type = "string", format = "string", description = "Phone number of the user")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ZonedDateTime createdDate;

    @Schema(type = "string", format = "string", description = "Profile image of the user")
    private String profileImage;

    @Schema(type = "string", format = "string", description = "System access token for user")
    @NotNull(message = "Access token is required")
    private String accessToken;
}
