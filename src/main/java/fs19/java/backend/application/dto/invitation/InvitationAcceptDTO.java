package fs19.java.backend.application.dto.invitation;

import com.fasterxml.jackson.annotation.JsonProperty;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvitationAcceptDTO {

    @Schema(type = "String", format = "String", description = "invitation sending url")
    @NotNull
    private String url;
    @Schema(type = "String", format = "ResponseStatus", description = "Unique system status")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ResponseStatus status;
}
