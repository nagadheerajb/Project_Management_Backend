package fs19.java.backend.application;

import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.application.dto.permission.PermissionResponseDTO;
import fs19.java.backend.application.mapper.PermissionMapper;
import fs19.java.backend.application.service.PermissionService;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.infrastructure.InvitationRepoImpl;
import fs19.java.backend.infrastructure.PermissionRepoImpl;
import fs19.java.backend.presentation.controller.ActivityLogController;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Permission entity service layer
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private static final Logger logger = LogManager.getLogger(ActivityLogController.class);

    private final PermissionRepoImpl permissionRepo;
    private final UserServiceImpl userService;
    private final ActivityLoggerService activityLoggerService;


    public PermissionServiceImpl(PermissionRepoImpl PermissionRepoImpl,
                                 InvitationRepoImpl invitationRepo,
                                 UserServiceImpl userService, ActivityLoggerService activityLoggerService) {
        this.permissionRepo = PermissionRepoImpl;
        this.userService = userService;
        this.activityLoggerService = activityLoggerService;
    }

    /**
     * Create a new permission
     *
     * @param permissionRequestDTO
     * @return
     */
    @Override
    public PermissionResponseDTO save(@Valid PermissionRequestDTO permissionRequestDTO) {
        Permission myPermission;
        if (permissionRequestDTO.getName().isEmpty()) { // expected valid name only and that validation is enough
            logger.info("Permission Name from DTO is null, cannot proceed with Permission creation. {}", permissionRequestDTO);
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.PERMISSION_NAME_NOT_FOUND);
        }
        if (this.permissionRepo.findByName(permissionRequestDTO.getName()) == null) {
            myPermission = this.permissionRepo.save(PermissionMapper.toPermission(permissionRequestDTO));
            if (myPermission == null) {
                return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND);
            }
            activityLoggerService.logActivity(EntityType.PERMISSION, myPermission.getId(), ActionType.CREATED, userService.findUserById(permissionRequestDTO.getCreated_user()).getId());
            return PermissionMapper.toPermissionResponseDTO(myPermission, ResponseStatus.SUCCESSFULLY_CREATED);
        } else {
            logger.info("RoleModel-Already Found {}", permissionRequestDTO);
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.RECORD_ALREADY_CREATED);
        }
    }


    /**
     * Update a Permission according to user data
     *
     * @param permissionId
     * @param permissionRequestDTO
     * @return
     */
    @Override
    public PermissionResponseDTO update(UUID permissionId, @Valid PermissionRequestDTO permissionRequestDTO) {
        Permission myPermission;
        if (permissionId == null) {
            logger.info("Permission ID is null, cannot proceed with update. {}", permissionRequestDTO);
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        } else if (permissionRequestDTO.getName().isEmpty()) {
            logger.info("Define Permission name from DTO is null, cannot proceed with update. {}", permissionRequestDTO);
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        if (this.permissionRepo.findByName(permissionRequestDTO.getName()) == null) {
            myPermission = this.permissionRepo.update(permissionId, permissionRequestDTO);
            if (myPermission == null) {
                return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND);
            }
            activityLoggerService.logActivity(EntityType.PERMISSION, myPermission.getId(), ActionType.UPDATED, userService.findUserById(permissionRequestDTO.getCreated_user()).getId());
            return PermissionMapper.toPermissionResponseDTO(myPermission, ResponseStatus.SUCCESSFULLY_UPDATED);
        } else {
            logger.info("Permission-Already Found. {}", permissionRequestDTO);
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.RECORD_ALREADY_CREATED);
        }
    }

    /**
     * Delete Permission according to user details
     *
     * @param permissionId UUID
     * @return PermissionResponseDTO
     */
    @Override
    public PermissionResponseDTO delete(UUID permissionId) {
        if (permissionId == null) {
            logger.info("Permission ID is null, cannot proceed with delete. {}", (Object) null);
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        Permission myPermission = this.permissionRepo.delete(permissionId);
        if (myPermission == null) {
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND);
        }
        activityLoggerService.logActivity(EntityType.PERMISSION, myPermission.getId(), ActionType.DELETED, myPermission.getId());
        return PermissionMapper.toPermissionResponseDTO(myPermission, ResponseStatus.SUCCESSFULLY_DELETED);
    }

    /**
     * load all permissions
     *
     * @return List<PermissionResponseDTO>
     */
    @Override
    public List<PermissionResponseDTO> getAll() {
        return PermissionMapper.toRoleResponseDTOs(this.permissionRepo.findAll(), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * Search Permission by id
     *
     * @param permissionId
     * @return
     */
    @Override
    public PermissionResponseDTO findById(UUID permissionId) {
        if (permissionId == null) {
            logger.info("Permission Id is null, cannot proceed with search.");
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        Permission myPermission = this.permissionRepo.findById(permissionId);
        if (myPermission == null) {
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND);
        }
        return PermissionMapper.toPermissionResponseDTO(myPermission, ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * Search Permission By Name
     *
     * @param name
     * @return
     */
    @Override
    public PermissionResponseDTO findByName(String name) {
        if (name.isEmpty()) {
            logger.info("Permission Name is null, cannot proceed with search.");
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.PERMISSION_NAME_NOT_FOUND);
        }
        Permission permission = this.permissionRepo.findByName(name);
        if (permission == null) {
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND);
        }
        return PermissionMapper.toPermissionResponseDTO(permission, ResponseStatus.SUCCESSFULLY_FOUND);
    }

    @Override
    public boolean existsById(UUID permissionId) {
        return permissionRepo.existsById(permissionId);
    }
}
