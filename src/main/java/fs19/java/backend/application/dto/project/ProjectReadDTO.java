package fs19.java.backend.application.dto.project;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectReadDTO {
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
