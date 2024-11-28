package fs19.java.backend.domain.abstraction;

import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.domain.entity.Permission;

import java.util.List;
import java.util.UUID;

/**
 * Define the permission repository
 */
public interface PermissionRepository {

    Permission save(Permission permission);
    Permission update(UUID permissionId, PermissionRequestDTO permissionRequestDTO);
    Permission delete(UUID permissionId);
    List<Permission> findAll();
    Permission findById(UUID permissionId);
    Permission findByName(String name);
    boolean existsById(UUID permissionId);


}
