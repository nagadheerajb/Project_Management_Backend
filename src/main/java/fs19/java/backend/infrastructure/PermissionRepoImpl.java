package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.domain.abstraction.PermissionRepository;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.infrastructure.JpaRepositories.PermissionJpaRepo;
import fs19.java.backend.presentation.shared.exception.PermissionLevelException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Permission Repo implementation
 */
@Repository
public class PermissionRepoImpl implements PermissionRepository {

    private final PermissionJpaRepo permissionJpaRepo;

    public PermissionRepoImpl(PermissionJpaRepo permissionJpaRepo) {
        this.permissionJpaRepo = permissionJpaRepo;
    }

    /**
     * Create permission
     *
     * @param permission
     * @return
     */
    @Override
    public Permission save(Permission permission) {
        try {
            return permissionJpaRepo.save(permission);
        } catch (Exception e) {
            throw new PermissionLevelException(e.getLocalizedMessage() + " : " + PermissionLevelException.PERMISSION_CREATE);
        }
    }

    /**
     * Update permission
     *
     * @param permissionId
     * @param permissionRequestDTO
     * @return
     */
    @Override
    public Permission update(UUID permissionId, PermissionRequestDTO permissionRequestDTO) {
        Permission permission = findById(permissionId);
        permission.setName(permissionRequestDTO.getName());
        return permissionJpaRepo.save(permission);
    }

    /**
     * Delete permission
     *
     * @param permissionId
     * @return
     */
    @Override
    public Permission delete(UUID permissionId) {
        Permission permission = findById(permissionId);
        permissionJpaRepo.delete(permission);
        return permission;

    }

    /**
     * Load all permissions
     *
     * @return
     */
    @Override
    public List<Permission> findAll() {
        return permissionJpaRepo.findAll();
    }

    /**
     * Load permission by permission Id
     *
     * @param permissionId
     * @return
     */
    @Override
    public Permission findById(UUID permissionId) {
        return permissionJpaRepo.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException("Permission result not found with ID: " + permissionId));
    }

    /**
     * Load permission by permission Name
     *
     * @param name
     * @return
     */
    @Override
    public Permission findByName(String name) {
        return permissionJpaRepo.findByName(name);
    }

    /**
     * Check id is existing
     *
     * @param permissionId
     * @return
     */
    @Override
    public boolean existsById(UUID permissionId) {
        return permissionJpaRepo.existsById(permissionId);
    }

}
