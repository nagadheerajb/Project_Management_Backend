package fs19.java.backend.application.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserCreateDTO {

  @Schema(type = "string", format = "string", description = "First name of the user")
  @Size(max = 45, message = "Field must be at most 45 characters")
  @NotNull(message = "First name is required")
  String firstName;

  @Schema(type = "string", format = "string", description = "Last name of the user")
  @Size(max = 45, message = "Field must be at most 45 characters")
  @NotNull(message = "Last name is required")
  String lastName;

  @Schema(type = "string", format = "string", description = "Email of the user")
  @NotNull(message = "Email is required")
  String email;

  @Schema(type = "string", format = "string", description = "Password of the user")
  @NotNull(message = "Password is required")
  @Size(min = 8, message = "Password should be at least 8 characters long")
  String password;

  @Schema(type = "string", format = "string", description = "Phone number of the user")
  @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters long")
  String phone;

  @Schema(type = "string", format = "string", description = "Profile image of the user")
  @Size(max = 2000, message = "Field must be at most 45 characters")
  String profileImage;

  @Schema(type = "string", format = "string", description = "Invitation url")
  @Size(max = 3000, message = "Field must be at most 3000 characters")
  String invitationUrl = "";
}
