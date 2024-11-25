package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.workspace.WorkspaceRequestDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceResponseDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceUpdateDTO;
import fs19.java.backend.application.service.WorkspaceService;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "Workspaces", description = "Manage workspaces")
@RestController
@RequestMapping("/api/v1/workspaces")
public class WorkspaceController {

    private static final Logger logger = LogManager.getLogger(WorkspaceController.class);
    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @Operation(summary = "Create a workspace", description = "Creates a new workspace with the provided details.")
    @PostMapping
    public ResponseEntity<GlobalResponse<WorkspaceResponseDTO>> createWorkspace(@Valid @RequestBody WorkspaceRequestDTO workspaceRequestDTO) {
        logger.info("Received create workspace request: {}", workspaceRequestDTO);
        WorkspaceResponseDTO createdWorkspace = workspaceService.createWorkspace(workspaceRequestDTO);
        logger.info("Returning created workspace with ID: {}", createdWorkspace.getId());
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdWorkspace), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a workspace", description = "Updates the details of an existing workspace.")
    @PutMapping("/{workspaceId}")
    public ResponseEntity<GlobalResponse<WorkspaceResponseDTO>> updateWorkspace(@PathVariable UUID workspaceId, @Valid @RequestBody WorkspaceUpdateDTO workspaceUpdateDTO) {
        logger.info("Received update workspace request for ID: {} and DTO: {}", workspaceId, workspaceUpdateDTO);
        WorkspaceResponseDTO updatedWorkspace = workspaceService.updateWorkspace(workspaceId, workspaceUpdateDTO);
        logger.info("Returning updated workspace with ID: {}", updatedWorkspace.getId());
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedWorkspace), HttpStatus.OK);
    }

    @Operation(summary = "Get a workspace by ID", description = "Retrieves the details of a workspace by its ID.")
    @GetMapping("/{workspaceId}")
    public ResponseEntity<GlobalResponse<WorkspaceResponseDTO>> getWorkspaceById(@PathVariable UUID workspaceId) {
        logger.info("Received request to get workspace with ID: {}", workspaceId);
        WorkspaceResponseDTO workspace = workspaceService.getWorkspaceById(workspaceId);
        logger.info("Workspace retrieved successfully: {}", workspace);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), workspace), HttpStatus.OK);
    }

    @Operation(summary = "Get all workspaces", description = "Retrieves the details of all workspaces.")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<WorkspaceResponseDTO>>> getAllWorkspaces() {
        logger.info("Received request to get all workspaces");
        List<WorkspaceResponseDTO> workspaces = workspaceService.getAllWorkspaces();
        logger.info("All workspaces retrieved successfully");
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), workspaces), HttpStatus.OK);
    }

    @Operation(summary = "Delete a workspace", description = "Deletes a workspace by its ID.")
    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<GlobalResponse<Void>> deleteWorkspace(@PathVariable UUID workspaceId) {
        logger.info("Received request to delete workspace with ID: {}", workspaceId);
        workspaceService.deleteWorkspace(workspaceId);
        logger.info("Workspace deleted successfully with ID: {}", workspaceId);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null), HttpStatus.NO_CONTENT);
    }
}