package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.invitation.InvitationRequestDTO;
import fs19.java.backend.application.dto.invitation.InvitationResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface InvitationService {
    InvitationResponseDTO save(@Valid InvitationRequestDTO invitationRequestDTO);
    InvitationResponseDTO update(UUID invitationId, @Valid InvitationRequestDTO invitationRequestDTO);
    InvitationResponseDTO delete(UUID invitationId);
    List<InvitationResponseDTO> findAll();
    InvitationResponseDTO findById(UUID invitationId);
}
