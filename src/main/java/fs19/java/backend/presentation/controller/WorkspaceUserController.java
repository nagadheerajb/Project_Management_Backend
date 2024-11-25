package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.WorkspaceUserServiceImpl;
import fs19.java.backend.application.dto.workspace_user.WorkspaceUserRequestDTO;
import fs19.java.backend.application.dto.workspace_user.WorkspaceUserResponseDTO;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Tag(name = "Workspace Users", description = "Manage workspace users")
@RestController
@RequestMapping("/api/v1/workspace-users")
public class WorkspaceUserController {

  private static final Logger logger = LogManager.getLogger(WorkspaceUserController.class);
  private final WorkspaceUserServiceImpl workspaceUsersService;

  public WorkspaceUserController(WorkspaceUserServiceImpl workspaceUsersService) {
    this.workspaceUsersService = workspaceUsersService;
  }

  @Operation(summary = "Create a workspace user")
  @PostMapping
  public ResponseEntity<GlobalResponse<WorkspaceUserResponseDTO>> createWorkspaceUser(
      @Valid @RequestBody WorkspaceUserRequestDTO workspaceUsersDTO) {
    logger.info("Received request to create workspace user: {}", workspaceUsersDTO);

    // validate the request
    validateWorkspaceUserRequestDTO(workspaceUsersDTO);
    // create the workspace user
    WorkspaceUserResponseDTO workspaceUsersResponseDTO = workspaceUsersService.createWorkspaceUser(
        workspaceUsersDTO);
    logger.info("Workspace user created successfully: {}", workspaceUsersResponseDTO);
    // return the response
    return new ResponseEntity<>(
        new GlobalResponse<>(HttpStatus.CREATED.value(), workspaceUsersResponseDTO),
        HttpStatus.CREATED);
  }

  @Operation(summary = "Update a workspace user")
  @PutMapping("/{workspaceUserId}")
  public ResponseEntity<GlobalResponse<WorkspaceUserResponseDTO>> updateWorkspaceUser(
      @PathVariable UUID workspaceUserId,
      @Valid @RequestBody WorkspaceUserRequestDTO workspaceUsersDTO) {
    logger.info("Received request to update workspace user with ID: {} and DTO: {}", workspaceUserId,
        workspaceUsersDTO);
    WorkspaceUserResponseDTO workspaceUsers = workspaceUsersService.updateWorkspaceUser(
        workspaceUserId, workspaceUsersDTO);
    logger.info("Workspace user updated successfully: {}", workspaceUsers);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), workspaceUsers),
        HttpStatus.OK);
  }

  @Operation(summary = "Get workspace user by ID")
  @GetMapping("/{workspaceUserId}")
  public ResponseEntity<GlobalResponse<WorkspaceUserResponseDTO>> getWorkspaceUserById(
      @PathVariable UUID workspaceUserId) {
    logger.info("Received request to get workspace user with ID: {}", workspaceUserId);
    WorkspaceUserResponseDTO workspaceUsers = workspaceUsersService.getWorkspaceUserById(
        workspaceUserId);
    logger.info("Workspace user retrieved successfully: {}", workspaceUsers);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), workspaceUsers),
        HttpStatus.OK);
  }

  @Operation(summary = "Get all workspace users")
  @GetMapping
  public ResponseEntity<GlobalResponse<List<WorkspaceUserResponseDTO>>> getAllWorkspaceUsers() {
    logger.info("Received request to get all workspace users");
    List<WorkspaceUserResponseDTO> workspaceUsers = workspaceUsersService.getAllWorkspacesUsers();
    logger.info("Workspace users retrieved successfully: {}", workspaceUsers);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), workspaceUsers),
        HttpStatus.OK);
  }

  @Operation(summary = "Delete a workspace user")
  @DeleteMapping("/{workspaceUserId}")
  public ResponseEntity<GlobalResponse<String>> deleteWorkspaceUser(
      @PathVariable UUID workspaceUserId) {
    logger.info("Received request to delete workspace user with ID: {}", workspaceUserId);
    workspaceUsersService.deleteWorkspace(workspaceUserId);
    logger.info("Workspace user deleted successfully with ID: {}", workspaceUserId);
    return new ResponseEntity<>(
        new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), "Workspace User deleted successfully"),
        HttpStatus.OK);
  }

  private void validateWorkspaceUserRequestDTO(WorkspaceUserRequestDTO workspaceUsersDTO) {
    if (workspaceUsersDTO.getRoleId() == null) {
      throw new IllegalArgumentException("Role ID is required");
    }
    if (workspaceUsersDTO.getWorkspaceId() == null) {
      throw new IllegalArgumentException("Workspace ID is required");
    }
    if (workspaceUsersDTO.getUserId() == null) {
      throw new IllegalArgumentException("User ID is required");
    }
  }
}
