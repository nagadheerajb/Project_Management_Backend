package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.project.ProjectCreateDTO;
import fs19.java.backend.application.dto.project.ProjectReadDTO;
import fs19.java.backend.application.dto.project.ProjectUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    ProjectReadDTO createProject(ProjectCreateDTO projectDTO);
    ProjectReadDTO updateProject(UUID projectId, ProjectUpdateDTO projectDTO);
    Boolean deleteProject(UUID projectId);
    ProjectReadDTO findProjectById(UUID projectId);
    List<ProjectReadDTO> findAllProjects();
}
