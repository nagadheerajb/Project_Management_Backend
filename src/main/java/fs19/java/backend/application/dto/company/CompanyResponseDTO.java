package fs19.java.backend.application.dto.company;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CompanyResponseDTO {
    private UUID id;
    private String name;
    private ZonedDateTime createdDate;
    private UUID createdBy;
}