package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.workspace_user.WorkspaceUserRequestDTO;
import fs19.java.backend.application.dto.workspace_user.WorkspaceUserResponseDTO;
import java.util.List;
import java.util.UUID;

public interface WorkspaceUserService {
  WorkspaceUserResponseDTO createWorkspaceUser(WorkspaceUserRequestDTO workspaceUsersDTO);
  WorkspaceUserResponseDTO updateWorkspaceUser(UUID id, WorkspaceUserRequestDTO workspaceUsersDTO);
  WorkspaceUserResponseDTO getWorkspaceUserById(UUID id);
  List<WorkspaceUserResponseDTO> getAllWorkspacesUsers();
  void deleteWorkspace(UUID id);
}
