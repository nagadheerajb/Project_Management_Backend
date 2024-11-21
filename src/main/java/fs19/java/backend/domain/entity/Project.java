package fs19.java.backend.domain.entity;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Project {
  private UUID id;
  private String name;
  private String description;
  private ZonedDateTime createdDate;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private UUID createdByUserId;
  private UUID workspaceId;
  private Boolean status;
}
