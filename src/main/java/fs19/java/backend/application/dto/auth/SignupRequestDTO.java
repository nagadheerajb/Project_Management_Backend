package fs19.java.backend.application.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Using for SignUp actions
 */
public record SignupRequestDTO(
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password should be at least 8 characters long")
        String password,
        @NotBlank(message = "FirstName is required")
        String firstName,
        @NotBlank(message = "LastName is required")
        String lastName

) {
}