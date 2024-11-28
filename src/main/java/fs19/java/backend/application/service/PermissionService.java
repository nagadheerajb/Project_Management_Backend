package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.application.dto.permission.PermissionResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

/**
 * Permission service interface
 */
public interface PermissionService {

    PermissionResponseDTO save(@Valid PermissionRequestDTO permissionRequestDTO);
    PermissionResponseDTO update(UUID permissionId, @Valid PermissionRequestDTO permissionRequestDTO);
    PermissionResponseDTO delete(UUID permissionId);
    List<PermissionResponseDTO> getAll();
    PermissionResponseDTO findById(UUID permissionId);
    PermissionResponseDTO findByName(String name);
    boolean existsById(UUID permissionId);

}
