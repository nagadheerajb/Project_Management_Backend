package fs19.java.backend.application;

import fs19.java.backend.application.dto.project.ProjectCreateDTO;
import fs19.java.backend.application.dto.project.ProjectReadDTO;
import fs19.java.backend.application.dto.project.ProjectUpdateDTO;
import fs19.java.backend.application.mapper.ProjectMapper;
import fs19.java.backend.application.service.ProjectService;
import fs19.java.backend.domain.entity.Project;
import fs19.java.backend.infrastructure.ProjectRepoImpl;
import fs19.java.backend.presentation.shared.exception.ProjectNotFoundException;
import fs19.java.backend.presentation.shared.exception.ProjectValidationException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepoImpl projectRepository;
    private static final String ERROR_MESSAGE = "Project not found with Id ";

    public ProjectServiceImpl(ProjectRepoImpl projectRepository) {
        this.projectRepository = projectRepository;
    }
    @Override
    public ProjectReadDTO createProject(ProjectCreateDTO projectDTO) {
        if (projectDTO.getName() == null || projectDTO.getName().isEmpty()) {
            throw new ProjectValidationException("Project name is required");
        }
        if (projectDTO.getStartDate() == null || projectDTO.getStartDate().isAfter(projectDTO.getEndDate())) {
            throw new ProjectValidationException("Start date is required and must be before end date");
        }
        if (projectDTO.getEndDate() == null || projectDTO.getEndDate().isBefore(projectDTO.getStartDate())) {
            throw new ProjectValidationException("End date is required and must be after start date");
        }

        Project project = ProjectMapper.toEntity(projectDTO);
        project.setId(UUID.randomUUID());
        project.setCreatedDate(ZonedDateTime.now());
        project.setCreatedByUserId(UUID.randomUUID());
        project.setWorkspaceId(UUID.randomUUID());

        projectRepository.saveProject(project);
        return ProjectMapper.toReadDTO(project);
    }

    @Override
    public ProjectReadDTO updateProject(UUID projectId, ProjectUpdateDTO projectDTO) {
        Optional<Project> existingProject = projectRepository.findById(projectId);

        if (existingProject.isPresent()) {
            Project updatedProject = existingProject.get();
            updatedProject.setDescription(projectDTO.getDescription());
            updatedProject.setStartDate(projectDTO.getStartDate());
            updatedProject.setEndDate(projectDTO.getEndDate());
            updatedProject.setStatus(projectDTO.getStatus());

             projectRepository.saveProject(updatedProject);
            return ProjectMapper.toReadDTO(updatedProject);
        }
        else {
            throw new ProjectValidationException(ERROR_MESSAGE + projectId);
        }
    }

    @Override
    public Boolean deleteProject(UUID projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            projectRepository.deleteProject(project.get());
            return true;
        }
        return false;
    }

    @Override
    public ProjectReadDTO findProjectById(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(ERROR_MESSAGE + projectId));
        return ProjectMapper.toReadDTO(project);
    }

    @Override
    public List<ProjectReadDTO> findAllProjects() {
        List<Project> projects = projectRepository.findAllProjects();
        return projects.stream()
                .map(ProjectMapper::toReadDTO)
                .toList();
    }
}
