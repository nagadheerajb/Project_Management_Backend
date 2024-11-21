package fs19.java.backend.domain.abstraction;

import fs19.java.backend.application.dto.invitation.InvitationRequestDTO;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Invitation;
import fs19.java.backend.domain.entity.Role;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface InvitationRepository {
    Invitation save(Invitation invitation);

    Invitation update(UUID invitationId, @Valid InvitationRequestDTO invitationRequestDTO, Role role, Company company);

    Invitation findById(UUID invitationId);

    List<Invitation> findAll();

    Invitation delete(UUID invitationId);
}
