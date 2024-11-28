package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.application.dto.permission.PermissionResponseDTO;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

/**
 * Permission to Permission DTO/s
 */
public class PermissionMapper {

    /**
     * Responsible to convert to a permission object to a response object
     *
     * @param permission Permission
     * @param responseStatus ResponseStatus
     * @return PermissionResponseDTO
     */
    public static PermissionResponseDTO toPermissionResponseDTO(Permission permission, ResponseStatus responseStatus) {
        return new PermissionResponseDTO(permission.getId(), permission.getName(),permission.getUrl(),permission.getPermissionType(), responseStatus);
    }

    /**
     * Convert permission list into role response dto list
     *
     * @param permissions List<Permission>
     * @param status      ResponseStatus
     * @return List<PermissionResponseDTO>
     */
    public static List<PermissionResponseDTO> toRoleResponseDTOs(List<Permission> permissions, ResponseStatus status) {
        List<PermissionResponseDTO> responseDTOS = new ArrayList<>();
        permissions.forEach(permission -> responseDTOS.add(toPermissionResponseDTO(permission, status)));
        return responseDTOS;
    }

    /**
     * Convert request object to entity object
     * @param permissionRequestDTO PermissionRequestDTO
     * @return Permission
     */
    public static Permission toPermission(@Valid PermissionRequestDTO permissionRequestDTO) {
        Permission myPermission = new Permission();
        myPermission.setName(permissionRequestDTO.getName());
        myPermission.setPermissionType(permissionRequestDTO.getPermissionType());
        myPermission.setUrl(permissionRequestDTO.getPermissionUrl());
        return myPermission;
    }
}
