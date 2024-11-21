package fs19.java.backend.application;

import fs19.java.backend.application.dto.workspace_users.WorkspaceUsersRequestDTO;
import fs19.java.backend.application.dto.workspace_users.WorkspaceUsersResponseDTO;
import fs19.java.backend.application.mapper.WorkspaceUsersMapper;
import fs19.java.backend.application.service.WorkspaceUsersService;
import fs19.java.backend.domain.entity.WorkspaceUsers;
import fs19.java.backend.infrastructure.WorkspaceUsersRepoImpl;
import fs19.java.backend.presentation.shared.exception.WorkspaceUserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkspaceUsersServiceImpl implements WorkspaceUsersService {
  private final WorkspaceUsersRepoImpl workspaceUsersRepository;
  private static final String ERROR_MESSAGE = "Workspace User not found with ID ";

  public WorkspaceUsersServiceImpl(WorkspaceUsersRepoImpl workspaceUsersRepository) {
    this.workspaceUsersRepository = workspaceUsersRepository;
  }

  public WorkspaceUsersResponseDTO createWorkspaceUser(WorkspaceUsersRequestDTO workspaceUsersDTO) {
    //exception handling
    if (workspaceUsersDTO.getRoleId() == null) {
      throw new IllegalArgumentException("Role ID is required");
    }
    if (workspaceUsersDTO.getWorkspaceId() == null) {
      throw new IllegalArgumentException("Workspace ID is required");
    }
    if (workspaceUsersDTO.getUserId() == null) {
      throw new IllegalArgumentException("User ID is required");
    }
    WorkspaceUsers workspaceUsers = WorkspaceUsersMapper.toEntity(workspaceUsersDTO);
    workspaceUsers.setId(UUID.randomUUID());
    workspaceUsers.setWorkspaceId(UUID.randomUUID());
    workspaceUsers.setUserId(UUID.randomUUID());
    workspaceUsers.setRoleId(UUID.randomUUID());

    workspaceUsersRepository.save(workspaceUsers);
    return WorkspaceUsersMapper.toDTO(workspaceUsers);
  }


  public WorkspaceUsersResponseDTO updateWorkspaceUser(UUID id, WorkspaceUsersRequestDTO workspaceUsersDTO) {
    Optional<WorkspaceUsers> existingWorkspaceUsers = workspaceUsersRepository.findById(id);
    if (existingWorkspaceUsers.isPresent()) {
      WorkspaceUsers workspaceUsers = existingWorkspaceUsers.get();
      workspaceUsers.setRoleId(workspaceUsersDTO.getRoleId());
      workspaceUsers.setUserId(workspaceUsersDTO.getUserId());
      workspaceUsers.setWorkspaceId(workspaceUsersDTO.getWorkspaceId());
      workspaceUsersRepository.save(workspaceUsers);
      return WorkspaceUsersMapper.toDTO(workspaceUsers);
    }
    throw  new WorkspaceUserNotFoundException(ERROR_MESSAGE + id);
  }

  public WorkspaceUsersResponseDTO getWorkspaceUserById(UUID id) {
    Optional<WorkspaceUsers> workspaceUsers = workspaceUsersRepository.findById(id);
    return workspaceUsers.map(WorkspaceUsersMapper::toDTO)
        .orElseThrow(() -> new WorkspaceUserNotFoundException(ERROR_MESSAGE + id));
  }

  public List<WorkspaceUsersResponseDTO> getAllWorkspacesUsers() {
    return workspaceUsersRepository.findAll().stream()
        .map(WorkspaceUsersMapper::toDTO)
        .toList();
  }

  public void deleteWorkspace(UUID id) {
    if (!workspaceUsersRepository.existsById(id)) {
      throw new WorkspaceUserNotFoundException(ERROR_MESSAGE + id);
    }
    workspaceUsersRepository.deleteById(id);
  }

  public boolean existsById(UUID id) {
    if (workspaceUsersRepository.existsById(id)) {
      workspaceUsersRepository.deleteById(id);
    }
    return false;
  }
}
