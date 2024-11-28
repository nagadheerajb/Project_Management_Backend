package fs19.java.backend.application;

import fs19.java.backend.application.dto.project.ProjectCreateDTO;
import fs19.java.backend.application.dto.project.ProjectReadDTO;
import fs19.java.backend.application.dto.project.ProjectUpdateDTO;
import fs19.java.backend.application.events.GenericEvent;
import fs19.java.backend.application.mapper.ProjectMapper;
import fs19.java.backend.application.service.ProjectService;
import fs19.java.backend.domain.entity.Project;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.infrastructure.JpaRepositories.ProjectJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.WorkspaceJpaRepo;
import fs19.java.backend.presentation.shared.exception.ProjectNotFoundException;
import fs19.java.backend.presentation.shared.exception.ProjectValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger logger = LogManager.getLogger(ProjectServiceImpl.class);
    private static final String ERROR_MESSAGE = "Project not found with Id ";

    @Autowired
    private final ProjectJpaRepo projectRepository;

    @Autowired
    private final WorkspaceJpaRepo workspaceRepository;

    @Autowired
    private final UserJpaRepo userRepository;
    private final ActivityLoggerService activityLoggerService;
    private final ApplicationEventPublisher eventPublisher;

    public ProjectServiceImpl(
            ProjectJpaRepo projectRepository,
            UserJpaRepo userRepository,
            WorkspaceJpaRepo workspaceRepository,
            ActivityLoggerService activityLoggerService, ApplicationEventPublisher eventPublisher) {

        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
        this.activityLoggerService = activityLoggerService;
        this.eventPublisher = eventPublisher;
    }
    @Override
    public ProjectReadDTO createProject(ProjectCreateDTO projectDTO) {
        logger.info("Creating project with DTO: {}", projectDTO);

        User createdBy = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (createdBy.getId() == null) {
             createdBy = userRepository.findById(projectDTO.getCreatedByUserId())
            .orElseThrow(() -> new ProjectValidationException("User not found with ID " + projectDTO.getCreatedByUserId()));
        }

        logger.info("User found for project creation: {}", createdBy);

        Workspace workspace = workspaceRepository.findById(projectDTO.getWorkspaceId())
            .orElseThrow(() -> new ProjectValidationException(
                "Workspace not found with ID " + projectDTO.getWorkspaceId()));
        logger.info("Workspace found for project creation: {}", workspace);

        Project project = ProjectMapper.toEntity(projectDTO);

        project.setCreatedDate(ZonedDateTime.now());
        project.setCreatedByUser(createdBy);
        project.setWorkspace(workspace);

        project = projectRepository.save(project);
        logger.info("Project created and saved: {}", project);

        logger.info("EntityType: {}", EntityType.PROJECT);
        logger.info("Entity ID: {}", project.getId());
        logger.info("Action: {}", ActionType.CREATED);
        logger.info("User ID: {}", createdBy.getId());

        activityLoggerService.logActivity(EntityType.PROJECT, project.getId(), ActionType.CREATED, createdBy.getId());
        logger.info("Activity logged for project creation");
        eventPublisher.publishEvent(new GenericEvent<>(this, project, EntityType.PROJECT, "Created"));
        return ProjectMapper.toReadDTO(project);
    }

    @Override
    public ProjectReadDTO updateProject(UUID projectId, ProjectUpdateDTO projectDTO) {
        logger.info("Updating project with ID: {} and DTO: {}", projectId, projectDTO);
        Optional<Project> existingProject = projectRepository.findById(projectId);

        if (existingProject.isPresent()) {
            logger.info("Existing project found: {}", existingProject);
            Project updatedProject = existingProject.get();
            updatedProject.setDescription(projectDTO.getDescription());
            updatedProject.setStartDate(projectDTO.getStartDate());
            updatedProject.setEndDate(projectDTO.getEndDate());
            updatedProject.setStatus(projectDTO.getStatus());

            updatedProject = projectRepository.save(updatedProject);
            logger.info("Project updated and saved: {}", updatedProject);

            activityLoggerService.logActivity(EntityType.PROJECT, updatedProject.getId(), ActionType.UPDATED, updatedProject.getCreatedByUser().getId());
            logger.info("Activity logged for project update");
            eventPublisher.publishEvent(new GenericEvent<>(this, updatedProject, EntityType.PROJECT, "Updated"));
            return ProjectMapper.toReadDTO(updatedProject);
        }
        else {
            throw new ProjectValidationException(ERROR_MESSAGE + projectId);
        }
    }

    @Override
    public ProjectReadDTO findProjectById(UUID projectId) {
        logger.info("Retrieving project with ID: {}", projectId);

        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(ERROR_MESSAGE + projectId));
        logger.info("Project retrieved: {}", project);

        return ProjectMapper.toReadDTO(project);
    }

    @Override
    public List<ProjectReadDTO> findAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
            .map(ProjectMapper::toReadDTO)
            .toList();
    }

    @Override
    public Boolean deleteProject(UUID projectId) {
        logger.info("Deleting project with ID: {}", projectId);

        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            logger.info("Project found: {}", project);
            projectRepository.delete(project.get());

            logger.info("Project deleted successfully");
            eventPublisher.publishEvent(new GenericEvent<>(this, project, EntityType.PROJECT, "Deleted"));
            return true;
        }
        return false;
    }
}
