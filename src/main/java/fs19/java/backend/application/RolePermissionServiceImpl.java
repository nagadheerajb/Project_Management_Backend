package fs19.java.backend.application;

import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.application.mapper.RolePermissionMapper;
import fs19.java.backend.application.service.RolePermissionService;
import fs19.java.backend.domain.entity.RolePermission;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.RolePermissionRepoImpl;
import fs19.java.backend.presentation.controller.ActivityLogController;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Role-Permission entity service layer
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    private static final Logger logger = LogManager.getLogger(ActivityLogController.class);
    private final RolePermissionRepoImpl rolePermissionRepo;
    private final ActivityLoggerService activityLoggerService;
    private final UserJpaRepo userJpaRepo;
    private RolePermissionRepoImpl rolePermissionRepo1;

    public RolePermissionServiceImpl(RolePermissionRepoImpl rolePermissionRepo, ActivityLoggerService activityLoggerService, UserJpaRepo userJpaRepo) {
        this.rolePermissionRepo = rolePermissionRepo;
        this.activityLoggerService = activityLoggerService;
        this.userJpaRepo = userJpaRepo;
    }

    /**
     * Assign permission to role
     *
     * @param rolePermissionRequestDTO
     * @return
     */
    @Override
    public RolePermissionResponseDTO create(@Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        if (rolePermissionRequestDTO.getRoleId() == null) {
            logger.info("Role Id is null, cannot proceed with create. {}", rolePermissionRepo);
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_ID_NOT_FOUND);
        } else if (rolePermissionRequestDTO.getPermissionId() == null) {
            logger.info("Permission Id is null, cannot proceed with create.{}", rolePermissionRepo1);
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        RolePermission resultIfExist = rolePermissionRepo.existsById(rolePermissionRequestDTO.getRoleId(), rolePermissionRequestDTO.getPermissionId());
        if (resultIfExist == null) {
            RolePermissionResponseDTO responseDTO = rolePermissionRepo.save(rolePermissionRequestDTO);
            activityLoggerService.logActivity(EntityType.ROLE_PERMISSION, responseDTO.getId(), ActionType.CREATED, userJpaRepo.findById(rolePermissionRequestDTO.getCreated_user()).get().getId());
            return responseDTO;
        }
        logger.info("Record Already created,Please use the existing information (Role permission id :" + resultIfExist.getId() + ") {}", rolePermissionRepo);
        return RolePermissionMapper.toPermissionResponseDTO(resultIfExist, ResponseStatus.ROLE_PERMISSION_ID_RECORD_ALREADY_EXIST);
    }

    /**
     * Update role permission record
     *
     * @param rolePermissionId
     * @param rolePermissionRequestDTO
     * @return
     */
    @Override
    public RolePermissionResponseDTO update(UUID rolePermissionId, @Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        if (rolePermissionId == null) {
            logger.info("Role-Permission Id is null, cannot proceed with update.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_ID_NOT_FOUND);
        } else {
            RolePermissionResponseDTO responseDTO = rolePermissionRepo.update(rolePermissionId, rolePermissionRequestDTO);
            activityLoggerService.logActivity(EntityType.ROLE_PERMISSION, responseDTO.getId(), ActionType.UPDATED, userJpaRepo.findById(rolePermissionRequestDTO.getCreated_user()).get().getId());
            return responseDTO;
        }
    }


    /**
     * Get all permission records
     *
     * @return
     */
    @Override
    public List<RolePermissionResponseDTO> findAll() {
        return rolePermissionRepo.findAll();
    }

    /**
     * Get role permission by record id
     *
     * @param permissionId
     * @return
     */
    @Override
    public RolePermissionResponseDTO findByPermissionId(UUID permissionId) {
        if (permissionId == null) {
            logger.info("Role-Permission ID is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_ID_NOT_FOUND);
        }
        return rolePermissionRepo.findById(permissionId);
    }

    /**
     * load permission by record id
     *
     * @param roleId
     * @return
     */
    @Override
    public List<RolePermissionResponseDTO> findByRoleId(UUID roleId) {
        if (roleId == null) {
            logger.info(" Role ID is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTOs(new ArrayList<>(), ResponseStatus.ROLE_ID_NOT_FOUND);
        }
        return rolePermissionRepo.findByRoleId(roleId);
    }

    /**
     * load role-permission by id
     *
     * @param rolePermissionId
     * @return
     */
    @Override
    public RolePermissionResponseDTO delete(UUID rolePermissionId) {
        if (rolePermissionId.toString() == null) {
            RolePermissionResponseDTO responseDTO = RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_ID_NOT_FOUND);
            activityLoggerService.logActivity(EntityType.ROLE_PERMISSION, responseDTO.getId(), ActionType.DELETED, rolePermissionId);

        }
        return rolePermissionRepo.delete(rolePermissionId);
    }

    /**
     * Load role-permission by permission id
     *
     * @param permissionId
     * @return
     */
    @Override
    public List<RolePermissionResponseDTO> findById(UUID permissionId) {
        if (permissionId == null) {
            logger.info(" Permission ID is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTOs(new ArrayList<>(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        return rolePermissionRepo.findByPermissionId(permissionId);
    }

    /**
     * Check already record is exist in a given system
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    @Override
    public RolePermissionResponseDTO findByRoleIdAndPermissionId(UUID roleId, UUID permissionId) {
        if (roleId == null) {
            logger.info("Role Id is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_ID_NOT_FOUND);
        } else if (permissionId == null) {
            logger.info("Permission Id is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        RolePermission resultIfExist = rolePermissionRepo.existsById(roleId, permissionId);
        if (resultIfExist == null) {
            logger.info(" Invalid Information, records not found.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.INVALID_INFORMATION_ROLE_PERMISSION_DETAILS_NOT_FOUND);
        }
        return RolePermissionMapper.toPermissionResponseDTO(resultIfExist, ResponseStatus.SUCCESSFULLY_FOUND);

    }
}
