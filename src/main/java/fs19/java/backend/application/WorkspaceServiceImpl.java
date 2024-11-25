package fs19.java.backend.application;

import fs19.java.backend.application.dto.workspace.WorkspaceRequestDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceResponseDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceUpdateDTO;
import fs19.java.backend.application.mapper.WorkspaceMapper;
import fs19.java.backend.application.service.WorkspaceService;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.infrastructure.JpaRepositories.CompanyJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.WorkspaceJpaRepo;
import fs19.java.backend.presentation.shared.exception.CompanyNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import fs19.java.backend.presentation.shared.exception.WorkspaceNotFoundException;
import fs19.java.backend.presentation.shared.exception.InvalidWorkspaceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private static final Logger logger = LogManager.getLogger(WorkspaceServiceImpl.class);

    private static final String WORKSPACE_NOT_FOUND_MESSAGE = "Workspace with ID %s not found";
    private static final String COMPANY_NOT_FOUND_MESSAGE = "Company with ID %s not found";
    private static final String USER_NOT_FOUND_MESSAGE = "User not found with ID %s";

    private final WorkspaceJpaRepo workspaceRepository;
    private final WorkspaceMapper workspaceMapper;
    private final UserJpaRepo userRepository;
    private final CompanyJpaRepo companyRepository;
    private final ActivityLoggerService activityLoggerService;

    public WorkspaceServiceImpl(WorkspaceJpaRepo workspaceRepository, WorkspaceMapper workspaceMapper, UserJpaRepo userRepository, CompanyJpaRepo companyRepository, ActivityLoggerService activityLoggerService) {
        this.workspaceRepository = workspaceRepository;
        this.workspaceMapper = workspaceMapper;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.activityLoggerService = activityLoggerService;
    }

    @Override
    public WorkspaceResponseDTO createWorkspace(WorkspaceRequestDTO workspaceRequestDTO) {
        logger.info("Creating workspace: {}", workspaceRequestDTO);
        if (workspaceRequestDTO == null) {
            throw new InvalidWorkspaceException("WorkspaceRequestDTO cannot be null.");
        }
        User createdBy = userRepository.findById(workspaceRequestDTO.getCreatedBy())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, workspaceRequestDTO.getCreatedBy())));
        Company company = companyRepository.findById(workspaceRequestDTO.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, workspaceRequestDTO.getCompanyId())));

        Workspace workspace = workspaceMapper.toEntity(workspaceRequestDTO, createdBy, company);
        workspace.setCreatedDate(ZonedDateTime.now());
        Workspace savedWorkspace = workspaceRepository.save(workspace);
        activityLoggerService.logActivity(EntityType.WORKSPACE, savedWorkspace.getId(), ActionType.CREATED, createdBy.getId());
        logger.info("Workspace created successfully: {}", savedWorkspace);
        return workspaceMapper.toDTO(savedWorkspace);
    }

    @Override
    public WorkspaceResponseDTO updateWorkspace(UUID id, WorkspaceUpdateDTO workspaceUpdateDTO) {
        logger.info("Updating workspace with ID: {} and DTO: {}", id, workspaceUpdateDTO);
        if (workspaceUpdateDTO == null) {
            throw new InvalidWorkspaceException("WorkspaceUpdateDTO cannot be null.");
        }

        Workspace existingWorkspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException(String.format(WORKSPACE_NOT_FOUND_MESSAGE, id)));

        boolean isUpdateProvided = workspaceUpdateDTO.getName().isPresent() ||
                workspaceUpdateDTO.getDescription().isPresent() ||
                workspaceUpdateDTO.getType().isPresent() ||
                workspaceUpdateDTO.getCompanyId().isPresent();

        if (!isUpdateProvided) {
            throw new InvalidWorkspaceException("At least one field must be provided to update the workspace.");
        }

        workspaceUpdateDTO.getName().ifPresent(name -> {
            if (name.trim().isEmpty()) {
                throw new InvalidWorkspaceException("Workspace name cannot be blank.");
            }
            existingWorkspace.setName(name);
        });

        workspaceUpdateDTO.getDescription().ifPresent(description -> {
            if (description.trim().isEmpty()) {
                throw new InvalidWorkspaceException("Workspace description cannot be blank.");
            }
            existingWorkspace.setDescription(description);
        });

        workspaceUpdateDTO.getType().ifPresent(existingWorkspace::setType);
        workspaceUpdateDTO.getCompanyId().ifPresent(companyId -> {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new CompanyNotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, companyId)));
            existingWorkspace.setCompanyId(company);
        });

        Workspace savedWorkspace = workspaceRepository.save(existingWorkspace);
        // UUID createdBy = SecurityUtils.getCurrentUserId(); // Fetch the current user ID from the security context
        //activityLoggerService.logActivity(EntityType.WORKSPACE, existingWorkspace.getId(), ActionType.UPDATED, createdBy);
        logger.info("Workspace updated successfully: {}", savedWorkspace);
        return workspaceMapper.toDTO(savedWorkspace);
    }

    @Override
    public WorkspaceResponseDTO getWorkspaceById(UUID id) {
        logger.info("Retrieving workspace with ID: {}", id);
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException(String.format(WORKSPACE_NOT_FOUND_MESSAGE, id)));
        logger.info("Workspace retrieved successfully: {}", workspace);
        return workspaceMapper.toDTO(workspace);
    }

    @Override
    public List<WorkspaceResponseDTO> getAllWorkspaces() {
        logger.info("Retrieving all workspaces");
        List<WorkspaceResponseDTO> workspaces = workspaceRepository.findAll().stream()
                .map(workspaceMapper::toDTO)
                .collect(Collectors.toList());
        logger.info("All workspaces retrieved successfully");
        return workspaces;
    }

    @Override
    public void deleteWorkspace(UUID id) {
        logger.info("Deleting workspace with ID: {}", id);
        if (!workspaceRepository.existsById(id)) {
            logger.error("Workspace with ID: {} not found for deletion", id);
            throw new WorkspaceNotFoundException(String.format(WORKSPACE_NOT_FOUND_MESSAGE, id));
        }
        workspaceRepository.deleteById(id);
        logger.info("Workspace with ID: {} deleted successfully", id);
        //User createdBy = SecurityUtils.getCurrentUser();
        //activityLoggerService.logActivity(EntityType.COMPANY, id, ActionType.DELETED, createdBy.getId());
    }
}
