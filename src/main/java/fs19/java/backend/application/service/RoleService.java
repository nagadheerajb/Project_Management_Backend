package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.application.dto.role.RoleResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

/**
 * Role service implementation
 */
public interface RoleService {

    RoleResponseDTO save(@Valid RoleRequestDTO roleRequestDTO);
    RoleResponseDTO update(UUID roleId, @Valid RoleRequestDTO roleMDDTO);
    RoleResponseDTO delete(UUID roleId);
    RoleResponseDTO findById(UUID roleId);
    List<RoleResponseDTO> findAll();
    RoleResponseDTO findByName(String name);
}
