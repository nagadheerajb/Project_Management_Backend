package fs19.java.backend.application;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.application.dto.role.RoleResponseDTO;
import fs19.java.backend.application.mapper.RoleMapper;
import fs19.java.backend.application.service.RoleService;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.RoleRepoImpl;
import fs19.java.backend.presentation.controller.ActivityLogController;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Role entity service layer
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LogManager.getLogger(ActivityLogController.class);
    private final RoleRepoImpl roleRepo;
    private final UserJpaRepo userJpaRepo;
    private final ActivityLoggerService activityLoggerService;

    public RoleServiceImpl(RoleRepoImpl roleRepo, UserJpaRepo userJpaRepo, ActivityLoggerService activityLoggerService) {
        this.roleRepo = roleRepo;
        this.userJpaRepo = userJpaRepo;
        this.activityLoggerService = activityLoggerService;
    }

    /**
     * Create a new role according to user data
     *
     * @param roleRequestDTO
     * @return
     */
    @Override
    public RoleResponseDTO save(@Valid RoleRequestDTO roleRequestDTO) {
        Role myRole;
        RoleResponseDTO roleResponseDTO = validateSaveDTO(roleRequestDTO);
        if (roleResponseDTO != null) return roleResponseDTO;
        Optional<Company> companyOptional = roleRepo.getCompanyByCompanyId(roleRequestDTO.getCompanyId());
        if (companyOptional.isPresent()) {
            if (this.roleRepo.findByName(roleRequestDTO.getName()) == null) {
                myRole = this.roleRepo.save(RoleMapper.toRole(roleRequestDTO, companyOptional.get()));
                if (myRole == null) {
                    return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND);
                }
                activityLoggerService.logActivity(EntityType.ROLE, myRole.getId(), ActionType.CREATED, userJpaRepo.findById(roleRequestDTO.getCreated_user()).get().getId());
                return RoleMapper.toRoleResponseDTO(myRole, ResponseStatus.SUCCESSFULLY_CREATED);
            } else {
                logger.info("Role-Already Found. {}", roleRequestDTO);
                return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.RECORD_ALREADY_CREATED);
            }
        } else {
            logger.info("Company-Name, cannot proceed with Role creation.{}", roleRequestDTO);
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.COMPANY_ID_NOT_FOUND);
        }

    }


    /**
     * Update a role according to user data
     *
     * @param roleId
     * @param roleRequestDTO
     * @return
     */
    @Override
    public RoleResponseDTO update(UUID roleId, @Valid RoleRequestDTO roleRequestDTO) {
        Role myRole;
        RoleResponseDTO roleResponseDTO = validateUpdateDTO(roleId, roleRequestDTO);
        if (roleResponseDTO != null) return roleResponseDTO;
        Optional<Company> companyOptional = roleRepo.getCompanyByCompanyId(roleRequestDTO.getCompanyId());
        if (companyOptional.isPresent()) {
            if (this.roleRepo.findByName(roleRequestDTO.getName()) == null) {
                myRole = this.roleRepo.update(roleId, roleRequestDTO, companyOptional.get());
                if (myRole == null) {
                    return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND);
                }
                activityLoggerService.logActivity(EntityType.ROLE, myRole.getId(), ActionType.UPDATED, userJpaRepo.findById(roleRequestDTO.getCreated_user()).get().getId());
                return RoleMapper.toRoleResponseDTO(myRole, ResponseStatus.SUCCESSFULLY_UPDATED);
            } else {
                logger.info("Role-Already Found. {}", roleRequestDTO);
                return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.RECORD_ALREADY_CREATED);
            }
        } else {
            logger.info("Company-Not Found, cannot proceed with Role creation.{}", roleRequestDTO);
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.COMPANY_NAME_NOT_FOUND);
        }
    }


    /**
     * Delete role according to user details
     *
     * @param roleId UUID
     * @return RoleResponseDTO
     */
    @Override
    public RoleResponseDTO delete(UUID roleId) {
        if (roleId == null) {
            logger.info("Role ID is null, cannot proceed with delete.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.ROLE_ID_NOT_FOUND);
        }
        Role myRole = this.roleRepo.delete(roleId);
        if (myRole == null) {
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND);
        }
        activityLoggerService.logActivity(EntityType.ROLE, myRole.getId(), ActionType.DELETED, myRole.getId());
        return RoleMapper.toRoleResponseDTO(myRole, ResponseStatus.SUCCESSFULLY_DELETED);
    }

    /**
     * Return all roles
     *
     * @return
     */
    @Override
    public List<RoleResponseDTO> findAll() {
        return RoleMapper.toRoleResponseDTOs(this.roleRepo.findAll(), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * Search Role by Id
     *
     * @param roleId
     * @return
     */
    @Override
    public RoleResponseDTO findById(UUID roleId) {
        if (roleId == null) {
            logger.info("Role ID is null, cannot proceed with search.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.ROLE_ID_NOT_FOUND);
        }
        Role myRole = this.roleRepo.findById(roleId);
        if (myRole == null) {
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND);
        }
        return RoleMapper.toRoleResponseDTO(myRole, ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * Search Role By Name
     *
     * @param name
     * @return
     */
    @Override
    public RoleResponseDTO findByName(String name) {
        if (name.isEmpty()) {
            logger.info("Role Name is null, cannot proceed with search.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.ROLE_NAME_NOT_FOUND);
        }
        Role myRole = this.roleRepo.findByName(name);
        if (myRole == null) {
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND);
        }
        return RoleMapper.toRoleResponseDTO(myRole, ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * Validate DTO and send the specific codes to a client
     *
     * @param roleRequestDTO
     * @return
     */
    private RoleResponseDTO validateSaveDTO(RoleRequestDTO roleRequestDTO) {
        if (roleRequestDTO.getName().isEmpty()) { // expected valid name only and that validation is enough
            logger.info("Role Name from DTO is null, cannot proceed with Role creation.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.ROLE_NAME_NOT_FOUND);
        }
        if (roleRequestDTO.getCompanyId() == null) {
            logger.info("Role Name from DTO is null, cannot proceed with Role creation.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.COMPANY_ID_NOT_FOUND);
        }
        return null;
    }

    /**
     * Validate DTO and send the specific codes to a client
     *
     * @param roleRequestDTO
     * @return
     */
    private RoleResponseDTO validateUpdateDTO(UUID roleId, RoleRequestDTO roleRequestDTO) {
        if (roleId == null) {
            logger.info("Role ID is null, cannot proceed with update.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.ROLE_ID_NOT_FOUND);
        }
        return validateSaveDTO(roleRequestDTO);
    }
}
