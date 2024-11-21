package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.WorkspaceUsersServiceImpl;
import fs19.java.backend.application.dto.workspace_users.WorkspaceUsersRequestDTO;
import fs19.java.backend.application.dto.workspace_users.WorkspaceUsersResponseDTO;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/workspace-users")
public class WorkspaceUsersController {

  private final WorkspaceUsersServiceImpl workspaceUsersService;

  public WorkspaceUsersController(WorkspaceUsersServiceImpl workspaceUsersService) {
    this.workspaceUsersService = workspaceUsersService;
  }

  @PostMapping
  public ResponseEntity<GlobalResponse<WorkspaceUsersResponseDTO>> createWorkspaceUser(@Valid @RequestBody WorkspaceUsersRequestDTO workspaceUsersDTO) {
     WorkspaceUsersResponseDTO  workspaceUsersResponseDTO = workspaceUsersService.createWorkspaceUser(workspaceUsersDTO);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), workspaceUsersResponseDTO), HttpStatus.CREATED);
  }

  //
  @GetMapping
  public ResponseEntity<GlobalResponse<List<WorkspaceUsersResponseDTO>>> getAllWorkspaceUsers() {
    List<WorkspaceUsersResponseDTO> workspaceUsers = workspaceUsersService.getAllWorkspacesUsers();
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), workspaceUsers), HttpStatus.OK);
  }

  @GetMapping("/{workspaceUserId}")
  public ResponseEntity<GlobalResponse<WorkspaceUsersResponseDTO>> getWorkspaceUserById(@PathVariable UUID workspaceUserId) {
    WorkspaceUsersResponseDTO workspaceUsers = workspaceUsersService.getWorkspaceUserById(workspaceUserId);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), workspaceUsers), HttpStatus.OK);
  }

  @PutMapping("/{workspaceUserId}")
  public ResponseEntity<GlobalResponse<WorkspaceUsersResponseDTO>> updateWorkspaceUser(@PathVariable UUID workspaceUserId, @Valid @RequestBody WorkspaceUsersRequestDTO workspaceUsersDTO) {
    WorkspaceUsersResponseDTO workspaceUsers = workspaceUsersService.updateWorkspaceUser(workspaceUserId, workspaceUsersDTO);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), workspaceUsers), HttpStatus.OK);
  }

  @DeleteMapping("/{workspaceUserId}")
  public ResponseEntity<GlobalResponse<String>> deleteWorkspaceUser(@PathVariable UUID workspaceUserId) {
    workspaceUsersService.deleteWorkspace(workspaceUserId);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), "Workspace User deleted successfully"), HttpStatus.OK);
  }
}
