package fs19.java.backend.application.dto.project;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectUpdateDTO {

  private String description;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private Boolean status;
}
