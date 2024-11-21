package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.workspace_users.WorkspaceUsersRequestDTO;
import fs19.java.backend.application.dto.workspace_users.WorkspaceUsersResponseDTO;

import java.util.List;
import java.util.UUID;

public interface WorkspaceUsersService {
  WorkspaceUsersResponseDTO createWorkspaceUser(WorkspaceUsersRequestDTO workspaceUsersDTO);
  WorkspaceUsersResponseDTO updateWorkspaceUser(UUID id, WorkspaceUsersRequestDTO workspaceUsersDTO);
  WorkspaceUsersResponseDTO getWorkspaceUserById(UUID id);
  List<WorkspaceUsersResponseDTO> getAllWorkspacesUsers();
  void deleteWorkspace(UUID id);
}
