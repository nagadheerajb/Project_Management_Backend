package fs19.java.backend.application.dto.invitation;

import com.fasterxml.jackson.annotation.JsonProperty;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvitationResponseDTO {
    @Schema(type = "uuid", format = "uuid", description = "Unique identifier")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @Schema(type = "boolean", format = "boolean", description = "check is accepted or not")
    private boolean isAccepted;
    @Schema(type = "date", format = "date", description = "invitation expired date define here")
    private ZonedDateTime expiredAt;
    @Schema(type = "String", format = "String", description = "invitation sending email")
    @NotNull
    private String email;
    @Schema(type = "uuid", format = "uuid", description = "invitation creating user Id")
    @NotNull(message = "User id cannot be null")
    private UUID created_user;
    @Schema(type = "uuid", format = "uuid", description = "invitation sending role Id")
    @NotNull
    private UUID roleId;
    @Schema(type = "uuid", format = "uuid", description = "invitation sending company Id")
    @NotNull
    private UUID companyId;
    @Schema(type = "String", format = "ResponseStatus", description = "Unique system status")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ResponseStatus status;
}
