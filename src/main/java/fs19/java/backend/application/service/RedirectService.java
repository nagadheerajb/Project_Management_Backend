package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.invitation.InvitationAcceptDTO;

import java.util.UUID;

public interface RedirectService {

    InvitationAcceptDTO processRedirect(String email, UUID roleId, UUID workspaceId);
}
