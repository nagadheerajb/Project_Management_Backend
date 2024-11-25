package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface RolePermissionService {

    RolePermissionResponseDTO create(@Valid RolePermissionRequestDTO rolePermissionRequestDTO);

    RolePermissionResponseDTO update(UUID rolePermissionId, @Valid RolePermissionRequestDTO rolePermissionRequestDTO);

    RolePermissionResponseDTO delete(UUID rolePermissionId);

    List<RolePermissionResponseDTO> findById(UUID permissionId);

    RolePermissionResponseDTO findByRoleIdAndPermissionId(UUID roleId, UUID permissionId);

    List<RolePermissionResponseDTO> findAll();

    RolePermissionResponseDTO findByPermissionId(UUID permissionId);

    List<RolePermissionResponseDTO> findByRoleId(UUID roleId);
}
