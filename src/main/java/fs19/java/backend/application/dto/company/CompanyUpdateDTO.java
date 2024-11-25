package fs19.java.backend.application.dto.company;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyUpdateDTO {

    @NotNull(message = "Company name cannot be null")
    @Size(min = 1, max = 255, message = "Company name must be between 1 and 255 characters")
    private String name;

    @NotNull(message = "Created by User ID is required")
    private UUID createdBy;
}