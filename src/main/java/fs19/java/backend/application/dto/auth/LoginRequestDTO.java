package fs19.java.backend.application.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Using for login actions
 * @param email
 * @param password
 */
public record LoginRequestDTO(
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 1, max = 40, message = "Password must be between 6 and 40 characters")
        String password
) {
}