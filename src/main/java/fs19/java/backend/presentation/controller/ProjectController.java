package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.ProjectServiceImpl;
import fs19.java.backend.application.dto.project.ProjectCreateDTO;
import fs19.java.backend.application.dto.project.ProjectReadDTO;
import fs19.java.backend.application.dto.project.ProjectUpdateDTO;
import fs19.java.backend.presentation.shared.exception.ProjectValidationException;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "projects", description = "Manage projects")
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

  private static final Logger logger = LogManager.getLogger(ProjectController.class);
  private final ProjectServiceImpl projectService;

  public ProjectController(ProjectServiceImpl projectService) {
    this.projectService = projectService;
  }

  @Operation(summary = "Create a project", description = "Creates a new project with the provided details.")
  @PostMapping
  public ResponseEntity<GlobalResponse<ProjectReadDTO>> createProject(@Valid @RequestBody ProjectCreateDTO projectDTO) {
    logger.info("Received request to create project: {}", projectDTO);
    // validate the request
    validateProjectRequestDTO(projectDTO);
    // create the project
    ProjectReadDTO createdProject = projectService.createProject(projectDTO);
    logger.info("Project created successfully: {}", createdProject);
    // return the response
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdProject),
        HttpStatus.CREATED);
  }

  @Operation(summary = "Update a project", description = "Updates the details of an existing project.")
  @PutMapping("/{projectId}")
  public ResponseEntity<GlobalResponse<ProjectReadDTO>> updateProject(@PathVariable UUID projectId, @Valid @RequestBody ProjectUpdateDTO projectDTO) {
    logger.info("Received request to update project with ID: {} and DTO: {}", projectId, projectDTO);
    ProjectReadDTO updatedProject = projectService.updateProject(projectId, projectDTO);
    logger.info("Project updated successfully: {}", updatedProject);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedProject),
        HttpStatus.OK);
  }

  @Operation(summary = "Get a project by ID", description = "Retrieves the details of a project by its ID.")
  @GetMapping("/{projectId}")
  public ResponseEntity<GlobalResponse<ProjectReadDTO>> getProjectById(@PathVariable UUID projectId) {
    logger.info("Received request to get project with ID: {}", projectId);
    ProjectReadDTO project = projectService.findProjectById(projectId);
    logger.info("Project retrieved successfully: {}", project);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), project),
        HttpStatus.OK);
  }

  @Operation(summary = "Get all projects", description = "Retrieves the details of all projects.")
  @GetMapping
  public ResponseEntity<GlobalResponse<List<ProjectReadDTO>>> getAllProjects() {
    logger.info("Received request to get all projects");
    List<ProjectReadDTO> projects = projectService.findAllProjects();
    logger.info("All projects retrieved successfully");
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), projects),
        HttpStatus.OK);
  }

  @Operation(summary = "Delete a project", description = "Deletes a project by its ID.")
  @DeleteMapping("/{projectId}")
  public ResponseEntity<GlobalResponse<Void>> deleteProject(@PathVariable UUID projectId) {
    logger.info("Received request to delete project with ID: {}", projectId);
    projectService.deleteProject(projectId);
    logger.info("Project deleted successfully with ID: {}", projectId);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null),
        HttpStatus.NO_CONTENT);
  }

  private void validateProjectRequestDTO(ProjectCreateDTO projectDTO) {
    if (projectDTO.getName() == null || projectDTO.getName().isEmpty()) {
      throw new ProjectValidationException("Project name is required");
    }
    if (projectDTO.getStartDate() == null || projectDTO.getStartDate()
        .isAfter(projectDTO.getEndDate())) {
      throw new ProjectValidationException("Start date is required and must be before end date");
    }
    if (projectDTO.getEndDate() == null || projectDTO.getEndDate()
        .isBefore(projectDTO.getStartDate())) {
      throw new ProjectValidationException("End date is required and must be after start date");
    }
  }
}
