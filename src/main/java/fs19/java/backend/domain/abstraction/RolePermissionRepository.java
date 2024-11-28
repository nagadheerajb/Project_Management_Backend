package fs19.java.backend.domain.abstraction;

import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.domain.entity.RolePermission;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Role permission repository
 */
public interface RolePermissionRepository {

    RolePermissionResponseDTO save(RolePermissionRequestDTO rolePermissionRequestDTO);
    List<RolePermissionResponseDTO> findAll();
    RolePermissionResponseDTO delete(UUID rolePermissionId);
    RolePermissionResponseDTO update(UUID rolePermissionId, @Valid RolePermissionRequestDTO rolePermissionRequestDTO);
    RolePermissionResponseDTO validate(RolePermissionRequestDTO rolePermissionRequestDTO);
    RolePermissionResponseDTO findById(UUID id);
    List<RolePermissionResponseDTO> findByRoleId(UUID roleId);
    List<RolePermissionResponseDTO> findByPermissionId(UUID permissionId);
    RolePermission existsById(@NotNull UUID roleId, @NotNull UUID permissionId);
}
