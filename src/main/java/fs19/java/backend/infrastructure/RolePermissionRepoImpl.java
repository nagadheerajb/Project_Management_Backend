package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.application.mapper.RolePermissionMapper;
import fs19.java.backend.domain.abstraction.RolePermissionRepository;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.domain.entity.RolePermission;
import fs19.java.backend.infrastructure.JpaRepositories.PermissionJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.RoleJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.RolePermissionJpaRepo;
import fs19.java.backend.presentation.shared.exception.RolePermissionLevelException;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Role-Permission Repo implementation
 */
@Repository
@EnableJpaRepositories
public class RolePermissionRepoImpl implements RolePermissionRepository {

    private final RolePermissionJpaRepo rolePermissionJpaRepo;
    private final RoleJpaRepo roleJpaRepo;
    private final PermissionJpaRepo permissionJpaRepo;

    public RolePermissionRepoImpl(RolePermissionJpaRepo rolePermissionJpaRepo, RoleJpaRepo roleJpaRepo, PermissionJpaRepo permissionJpaRepo) {
        this.rolePermissionJpaRepo = rolePermissionJpaRepo;
        this.roleJpaRepo = roleJpaRepo;
        this.permissionJpaRepo = permissionJpaRepo;
    }


    /**
     * create role permission by using user info
     *
     * @param rolePermissionRequestDTO
     * @return
     */
    @Override
    public RolePermissionResponseDTO save(RolePermissionRequestDTO rolePermissionRequestDTO) {
        Optional<Role> roleById = roleJpaRepo.findById(rolePermissionRequestDTO.getRoleId());
        if (roleById.isEmpty()) {
            RolePermissionResponseDTO responseDTO = new RolePermissionResponseDTO();
            responseDTO.setStatus(ResponseStatus.ROLE_RESULT_NOT_FOUND);
            return responseDTO;
        }
        Optional<Permission> permissionById = permissionJpaRepo.findById(rolePermissionRequestDTO.getPermissionId());
        if (permissionById.isEmpty()) {
            RolePermissionResponseDTO responseDTO = new RolePermissionResponseDTO();
            responseDTO.setStatus(ResponseStatus.PERMISSION_RESULT_NOT_FOUND);
            return responseDTO;
        }
        return getCreatedRolePermission(roleById.get(), permissionById.get());
    }

    /**
     * Responsible to search all role permissions
     *
     * @return List<RolePermissionResponseDTO>
     */
    @Override
    public List<RolePermissionResponseDTO> findAll() {
        return RolePermissionMapper.toPermissionResponseDTOs(rolePermissionJpaRepo.findAll(), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * search role-permission by id
     *
     * @param id
     * @return
     */
    @Override
    public RolePermissionResponseDTO findById(UUID id) {
        if (id == null) {
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_ID_NOT_FOUND);
        } else {
            Optional<RolePermission> rolePermission = rolePermissionJpaRepo.findById(id);
            if (rolePermission.isEmpty()) {
                RolePermissionResponseDTO responseDTO = new RolePermissionResponseDTO();
                responseDTO.setStatus(ResponseStatus.ROLE_PERMISSION_RESULT_NOT_FOUND);
                return responseDTO;
            }
            return RolePermissionMapper.toPermissionResponseDTO(rolePermission.get(), ResponseStatus.SUCCESSFULLY_FOUND);

        }
    }

    /**
     * Update role permission By Id
     *
     * @param rolePermissionId
     * @param rolePermissionRequestDTO
     * @return
     */
    @Override
    public RolePermissionResponseDTO update(UUID rolePermissionId, @Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        RolePermission resultIfExist = existsById(rolePermissionRequestDTO.getRoleId(), rolePermissionRequestDTO.getPermissionId());
        if (resultIfExist == null) {
            Optional<RolePermission> rolePermissionOptional = rolePermissionJpaRepo.findById(rolePermissionId);
            if (rolePermissionOptional.isPresent()) {
                Optional<Role> roleById = roleJpaRepo.findById(rolePermissionRequestDTO.getRoleId());
                if (roleById.isEmpty()) {
                    RolePermissionResponseDTO responseDTO = new RolePermissionResponseDTO();
                    responseDTO.setStatus(ResponseStatus.ROLE_RESULT_NOT_FOUND);
                    return responseDTO;
                }
                Optional<Permission> permissionById = permissionJpaRepo.findById(rolePermissionRequestDTO.getPermissionId());
                if (permissionById.isEmpty()) {
                    RolePermissionResponseDTO responseDTO = new RolePermissionResponseDTO();
                    responseDTO.setStatus(ResponseStatus.PERMISSION_RESULT_NOT_FOUND);
                    return responseDTO;
                }
                RolePermission rolePermission = rolePermissionOptional.get();
                rolePermission.setRole(roleById.get());
                rolePermission.setPermission(permissionById.get());
                rolePermissionJpaRepo.save(rolePermission);
                return RolePermissionMapper.toPermissionResponseDTO(rolePermission, ResponseStatus.SUCCESSFULLY_UPDATED);
            } else {
                return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_RESULT_NOT_FOUND);
            }
        } else {
            return RolePermissionMapper.toPermissionResponseDTO(resultIfExist, ResponseStatus.ROLE_PERMISSION_ID_RECORD_ALREADY_EXIST);
        }
    }

    /**
     * Validate existing records are available in the DB
     *
     * @param rolePermissionRequestDTO
     * @return
     */
    @Override
    public RolePermissionResponseDTO validate(RolePermissionRequestDTO rolePermissionRequestDTO) {
        Optional<Role> roleById = roleJpaRepo.findById(rolePermissionRequestDTO.getRoleId());
        if (roleById.isEmpty()) {
            RolePermissionResponseDTO responseDTO = new RolePermissionResponseDTO();
            responseDTO.setStatus(ResponseStatus.ROLE_RESULT_NOT_FOUND);
            return responseDTO;
        }
        Optional<Permission> permissionById = permissionJpaRepo.findById(rolePermissionRequestDTO.getPermissionId());
        if (permissionById.isEmpty()) {
            RolePermissionResponseDTO responseDTO = new RolePermissionResponseDTO();
            responseDTO.setStatus(ResponseStatus.PERMISSION_RESULT_NOT_FOUND);
            return responseDTO;
        }
        return null;
    }

    /**
     * Delete role-permission by Id
     *
     * @param rolePermissionId
     * @return
     */
    @Override
    public RolePermissionResponseDTO delete(UUID rolePermissionId) {
        Optional<RolePermission> rolePermissionById = rolePermissionJpaRepo.findById(rolePermissionId);
        if (rolePermissionById.isEmpty()) {
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_RESULT_NOT_FOUND);
        } else {
            rolePermissionJpaRepo.delete(rolePermissionById.get());
            return RolePermissionMapper.toPermissionResponseDTO(rolePermissionById.get(), ResponseStatus.SUCCESSFULLY_DELETED);
        }
    }

    /**
     * Load roles according to permission Id
     *
     * @param permissionId
     * @return
     */
    @Override
    public List<RolePermissionResponseDTO> findByPermissionId(UUID permissionId) {
        return RolePermissionMapper.toPermissionResponseDTOs(rolePermissionJpaRepo.findByPermissionId(permissionId), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * Load permissions by role Id
     *
     * @param roleId
     * @return
     */
    @Override
    public List<RolePermissionResponseDTO> findByRoleId(UUID roleId) {
        return RolePermissionMapper.toPermissionResponseDTOs(rolePermissionJpaRepo.findByRoleId(roleId), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * Check role-permission record already exists
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    @Override
    public RolePermission existsById(UUID roleId, UUID permissionId) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole(roleJpaRepo.findById(roleId).orElse(null));
        rolePermission.setPermission(permissionJpaRepo.findById(permissionId).orElse(null));

        if (rolePermission.getRole() == null || rolePermission.getPermission() == null) {
            return null; // Return null if either role or permission is not found
        }
        Example<RolePermission> example = Example.of(rolePermission,
                ExampleMatcher.matchingAll()
                        .withIgnoreNullValues()
                        .withMatcher("permission", ExampleMatcher.GenericPropertyMatchers.exact())
                        .withMatcher("role", ExampleMatcher.GenericPropertyMatchers.exact()));

        return rolePermissionJpaRepo.findOne(example).orElse(null);
    }


    /**
     * Create and return newly created role-permission object
     *
     * @param roleById
     * @param permissionById
     * @return
     */
    private RolePermissionResponseDTO getCreatedRolePermission(Role roleById, Permission permissionById) {
        try {
            RolePermission rolePermission = RolePermissionMapper.toRolePermission(roleById, permissionById);
            rolePermissionJpaRepo.save(rolePermission);
            return RolePermissionMapper.toPermissionResponseDTO(rolePermission, ResponseStatus.SUCCESSFULLY_CREATED);

        } catch (Exception e) {
            throw new RolePermissionLevelException(e.getLocalizedMessage() + " : " + RolePermissionLevelException.ROLE_PERMISSION_CREATE);
        }
    }

}
