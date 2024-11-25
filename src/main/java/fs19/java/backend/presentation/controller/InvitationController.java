package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.InvitationServiceImpl;
import fs19.java.backend.application.dto.invitation.InvitationRequestDTO;
import fs19.java.backend.application.dto.invitation.InvitationResponseDTO;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import fs19.java.backend.presentation.shared.response.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * This class will work as the main invitation entity controller and each method will work as endpoints
 */

@RestController
@RequestMapping("app/v1/invitations")
@Tag(name = "Invitation", description = "Manage invitations")
public class InvitationController {

    @Autowired
    private InvitationServiceImpl invitationService;

    /**
     * Create an invitation object using the define information
     *
     * @param invitationRequestDTO
     * @return
     */
    @Operation(summary = "Create an invitation", description = "Creates a new invitation with the provided details.")
    @PostMapping
    public ResponseEntity<GlobalResponse<InvitationResponseDTO>> createInvitation(@RequestBody @Valid InvitationRequestDTO invitationRequestDTO) {
        InvitationResponseDTO theInvitationResponseDTO = invitationService.save(invitationRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.CREATED, theInvitationResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theInvitationResponseDTO, ResponseHandler.convertResponseStatusToError(theInvitationResponseDTO.getStatus())), responseCode);
    }

    /**
     * Update a Invitation by id
     *
     * @param invitationRequestDTO
     * @return
     */
    @Operation(summary = "Update an invitation", description = "Updates the details of an existing  invitation.")
    @PutMapping("/{invitationId}")
    public ResponseEntity<GlobalResponse<InvitationResponseDTO>> updateRole(@PathVariable UUID invitationId, @RequestBody InvitationRequestDTO invitationRequestDTO) {
        InvitationResponseDTO theInvitationResponseDTO = invitationService.update(invitationId, invitationRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theInvitationResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theInvitationResponseDTO, ResponseHandler.convertResponseStatusToError(theInvitationResponseDTO.getStatus())), responseCode);
    }


    /**
     * Delete Invitation By invitation-Id
     *
     * @param invitationId
     * @return
     */
    @Operation(summary = "Delete an invitation", description = "Deletes an invitation by its ID.")
    @DeleteMapping("/{invitationId}")
    public ResponseEntity<GlobalResponse<InvitationResponseDTO>> deleteInvitationById(@PathVariable UUID invitationId) {
        InvitationResponseDTO theInvitationResponseDTO = invitationService.delete(invitationId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theInvitationResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theInvitationResponseDTO, ResponseHandler.convertResponseStatusToError(theInvitationResponseDTO.getStatus())), responseCode);
    }

    /**
     * Return the invitation list
     *
     * @return
     */
    @Operation(summary = "Get all invitations", description = "Retrieves the details of all invitations.")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<InvitationResponseDTO>>> getInvitations() {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), invitationService.findAll()), HttpStatus.OK);
    }

    /**
     * Return the role according to given id
     *
     * @return
     */
    @Operation(summary = "Get an invitation by ID", description = "Retrieves the details of an invitation by its ID.")
    @GetMapping("/{invitationId}")
    public ResponseEntity<GlobalResponse<InvitationResponseDTO>> getInvitationById(@PathVariable UUID invitationId) {
        InvitationResponseDTO theInvitationResponseDTO = invitationService.findById(invitationId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theInvitationResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theInvitationResponseDTO, ResponseHandler.convertResponseStatusToError(theInvitationResponseDTO.getStatus())), responseCode);
    }

}
