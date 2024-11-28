package fs19.java.backend.application.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReadDTO {
    @Schema(type = "uuid", format = "uuid", description = "Unique identifier")
    private UUID id;

    @Schema(type = "string", format = "string", description = "First name of the user")
    @Size(max = 45, message = "Field must be at most 45 characters")
    @NotNull(message = "First name is required")
    private String firstName;

    @Schema(type = "string", format = "string", description = "Last name of the user")
    @Size(max = 45, message = "Field must be at most 45 characters")
    @NotNull(message = "Last name is required")
    private String lastName;

    @Schema(type = "string", format = "string", description = "Email of the user")
    @NotNull(message = "Email is required")
    private String email;

    @Schema(type = "string", format = "string", description = "Phone of the user")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters long")
    private String phone;

   @Schema(type = "string", format = "string", description = "Created date of the user")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ZonedDateTime createdDate;

    @Schema(type = "string", format = "string", description = "Profile image of the user")
    @Size(max = 2000, message = "Field must be at most 45 characters")
    private String profileImage;
}
