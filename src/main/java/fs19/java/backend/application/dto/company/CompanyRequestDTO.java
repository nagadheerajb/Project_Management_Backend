package fs19.java.backend.application.dto.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDTO {

    @NotNull(message = "Company name cannot be null")
    @Size(min = 1, max = 255, message = "Company name must be between 1 and 255 characters")
    private String name;

    @NotNull(message = "Created by User ID is required")
    private UUID createdBy;
}