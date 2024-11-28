package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.RedirectServiceImpl;
import fs19.java.backend.application.dto.invitation.InvitationAcceptDTO;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import fs19.java.backend.presentation.shared.response.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Redirect", description = "Handle Invitation Requests")
@RequestMapping("/api/v1/accept-invitation")
@RestController
public class RedirectController {

    private final RedirectServiceImpl redirectService;

    public RedirectController(RedirectServiceImpl redirectService) {
        this.redirectService = redirectService;
    }

    @Operation(summary = "Handle invitations", description = "Handle invitations via coming through the third party APIs")
    @GetMapping("/redirect")
    public ResponseEntity<GlobalResponse<InvitationAcceptDTO>> handleInvitations(
            @Valid @RequestParam @NotNull String email,
            @Valid @RequestParam @NotNull UUID roleId,
            @Valid @RequestParam @NotNull UUID workspaceId) {
        InvitationAcceptDTO invitationAcceptDTO = redirectService.processRedirect(email, roleId, workspaceId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.FOUND, invitationAcceptDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), invitationAcceptDTO, ResponseHandler.convertResponseStatusToError(invitationAcceptDTO.getStatus())), responseCode);

    }
}
