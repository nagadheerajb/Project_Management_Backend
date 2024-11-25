package fs19.java.backend.application;

import fs19.java.backend.application.dto.workspace_user.WorkspaceUserRequestDTO;
import fs19.java.backend.application.dto.workspace_user.WorkspaceUserResponseDTO;
import fs19.java.backend.application.mapper.WorkspaceUserMapper;
import fs19.java.backend.application.service.WorkspaceUserService;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.domain.entity.WorkspaceUser;

import fs19.java.backend.infrastructure.JpaRepositories.RoleJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.WorkspaceJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.WorkspaceUserJpaRepo;
import fs19.java.backend.presentation.shared.exception.WorkspaceUserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkspaceUserServiceImpl implements WorkspaceUserService {

  private static final Logger logger = LogManager.getLogger(WorkspaceUserServiceImpl.class);
  private static final String ERROR_MESSAGE = "Workspace User not found with ID ";

  private final WorkspaceUserJpaRepo workspaceUserRepository;
  @Autowired
  private UserJpaRepo userRepository;
  @Autowired
  private RoleJpaRepo roleRepository;
  @Autowired
  private WorkspaceJpaRepo workspaceRepository;


  public WorkspaceUserServiceImpl(WorkspaceUserJpaRepo workspaceUsersRepository,
      UserJpaRepo userRepository, RoleJpaRepo roleRepository, WorkspaceJpaRepo workspaceRepository) {
    this.workspaceUserRepository = workspaceUsersRepository;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.workspaceRepository = workspaceRepository;
  }

  @Override
  public WorkspaceUserResponseDTO createWorkspaceUser(WorkspaceUserRequestDTO workspaceUsersDTO) {
    logger.info("Creating workspace user with DTO: {}", workspaceUsersDTO);

    User user = findUserById(workspaceUsersDTO.getUserId());
    Role role = findRoleById(workspaceUsersDTO.getRoleId());
    Workspace workspace = findWorkspaceById(workspaceUsersDTO.getWorkspaceId());

    WorkspaceUser workspaceUser = WorkspaceUserMapper.toEntity(workspaceUsersDTO, user, role, workspace);
    workspaceUser.setId(UUID.randomUUID());
    workspaceUser.setUser(user);
    workspaceUser.setRole(role);
    workspaceUser.setWorkspace(workspace);

    workspaceUser = workspaceUserRepository.save(workspaceUser);
    logger.info("Workspace user created and saved: {}", workspaceUser);
    return WorkspaceUserMapper.toDTO(workspaceUser);
  }

  @Override
  public WorkspaceUserResponseDTO updateWorkspaceUser(UUID id, WorkspaceUserRequestDTO workspaceUsersDTO) {

    logger.info("Updating workspace user with ID: {}", id);
    Optional<WorkspaceUser> existingWorkspaceUser = workspaceUserRepository.findById(id);
    if (existingWorkspaceUser.isPresent()) {
      logger.info("Workspace user found for update: {}", existingWorkspaceUser.get());
      WorkspaceUser workspaceUser = existingWorkspaceUser.get();

      updateWorkspaceUserFields(workspaceUsersDTO, workspaceUser);

      workspaceUser = workspaceUserRepository.save(workspaceUser);
      logger.info("Workspace user updated and saved: {}", workspaceUser);
      return WorkspaceUserMapper.toDTO(workspaceUser);
    }
    throw new WorkspaceUserNotFoundException(ERROR_MESSAGE + id);
  }

  @Override
  public WorkspaceUserResponseDTO getWorkspaceUserById(UUID id) {
    logger.info("Getting workspace user with ID: {}", id);
    Optional<WorkspaceUser> workspaceUser = workspaceUserRepository.findById(id);
    logger.info("Workspace user found: {}", workspaceUser);
    return workspaceUser.map(WorkspaceUserMapper::toDTO)
        .orElseThrow(() -> new WorkspaceUserNotFoundException(ERROR_MESSAGE + id));
  }

  @Override
  public List<WorkspaceUserResponseDTO> getAllWorkspacesUsers() {
    logger.info("Getting all workspace users");
    return workspaceUserRepository.findAll().stream()
        .map(WorkspaceUserMapper::toDTO)
        .toList();
  }

  @Override
  public void deleteWorkspace(UUID id) {
    logger.info("Deleting workspace user with ID: {}", id);
    if (!workspaceUserRepository.existsById(id)) {
      throw new WorkspaceUserNotFoundException(ERROR_MESSAGE + id);
    }
    logger.info("Workspace user found for deletion: {}", id);
    workspaceUserRepository.deleteById(id);
  }

  private void updateWorkspaceUserFields(WorkspaceUserRequestDTO workspaceUsersDTO,
      WorkspaceUser workspaceUser) {
    logger.info("Updating workspace user fields with DTO: {}", workspaceUsersDTO);
    if (workspaceUsersDTO.getRoleId() != null) {
      Role role = findRoleById(workspaceUsersDTO.getRoleId());
      logger.info("Role found: {}", role);

      workspaceUser.setRole(role);
    }
    if (workspaceUsersDTO.getWorkspaceId() != null) {
      Workspace workspace = findWorkspaceById(workspaceUsersDTO.getWorkspaceId());
      logger.info("Workspace found: {}", workspace);

      workspaceUser.setWorkspace(workspace);
    }
    if (workspaceUsersDTO.getUserId() != null) {
      User user = findUserById(workspaceUsersDTO.getUserId());
      logger.info("User found: {}", user);

      workspaceUser.setUser(user);
    }
  }


  private User findUserById(UUID userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
  }

  private Role findRoleById(UUID roleId) {
    return roleRepository.findById(roleId)
        .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + roleId));
  }

  private Workspace findWorkspaceById(UUID workspaceId) {
    return workspaceRepository.findById(workspaceId)
        .orElseThrow(() -> new IllegalArgumentException("Workspace not found with ID: " + workspaceId));
  }
}