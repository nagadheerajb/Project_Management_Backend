package fs19.java.backend.application.dto.project;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectUpdateDTO {

  private String description;
  @Schema(type = "string", format = "date", description = "Start date of the project in YYYY-MM-DD format")
  @NotNull(message = "Start date is required")
  @FutureOrPresent(message = "Start date must be in the present or future")
  private LocalDate startDate;

  @Schema(type = "string", format = "date", description = "End date of the project in YYYY-MM-DD format")
  @FutureOrPresent(message = "End date must be in the present or future")
  private LocalDate endDate;
  //private Boolean status;
}
