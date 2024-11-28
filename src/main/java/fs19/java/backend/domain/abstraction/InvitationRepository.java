package fs19.java.backend.domain.abstraction;

import fs19.java.backend.application.dto.invitation.InvitationRequestDTO;
import fs19.java.backend.domain.entity.*;

import java.util.List;
import java.util.UUID;

public interface InvitationRepository {
    Invitation save(Invitation invitation);
    Invitation findById(UUID invitationId);
    List<Invitation> findAll();
    Invitation delete(UUID invitationId);
    Invitation update(UUID invitationId, InvitationRequestDTO invitationRequestDTO, Role role, User user, Workspace workspace, String url);
}
