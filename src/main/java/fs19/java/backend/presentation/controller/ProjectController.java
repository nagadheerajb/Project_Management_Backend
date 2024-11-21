package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.ProjectServiceImpl;
import fs19.java.backend.application.dto.project.ProjectCreateDTO;
import fs19.java.backend.application.dto.project.ProjectReadDTO;
import fs19.java.backend.application.dto.project.ProjectUpdateDTO;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

  private final ProjectServiceImpl projectService;

  public ProjectController(ProjectServiceImpl projectService) {
    this.projectService = projectService;
  }

  @PostMapping
  public ResponseEntity<GlobalResponse<ProjectReadDTO>> createProject(@Valid @RequestBody ProjectCreateDTO projectDTO) {
    ProjectReadDTO createdProject = projectService.createProject(projectDTO);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdProject),
        HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<GlobalResponse<List<ProjectReadDTO>>> getAllProjects() {
    List<ProjectReadDTO> projects = projectService.findAllProjects();
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), projects),
        HttpStatus.OK);
  }

  @GetMapping("/{projectId}")
  public ResponseEntity<GlobalResponse<ProjectReadDTO>> getProjectById(@PathVariable UUID projectId) {
    ProjectReadDTO project = projectService.findProjectById(projectId);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), project),
        HttpStatus.OK);
  }

  @PutMapping("/{projectId}")
  public ResponseEntity<GlobalResponse<ProjectReadDTO>> updateProject(@PathVariable UUID projectId, @Valid @RequestBody ProjectUpdateDTO projectDTO) {
    ProjectReadDTO updatedProject = projectService.updateProject(projectId, projectDTO);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedProject),
        HttpStatus.OK);
  }

  @DeleteMapping("/{projectId}")
  public ResponseEntity<GlobalResponse<Void>> deleteProject(@PathVariable UUID projectId) {
    projectService.deleteProject(projectId);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null),
        HttpStatus.NO_CONTENT);
  }
}
