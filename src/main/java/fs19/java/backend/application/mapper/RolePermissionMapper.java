package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.domain.entity.RolePermission;
import fs19.java.backend.presentation.shared.status.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RolePermissionMapper {

    /**
     * Convert role permission object into response
     *
     * @param rolePermission
     * @param status
     * @return
     */
    public static RolePermissionResponseDTO toPermissionResponseDTO(RolePermission rolePermission, ResponseStatus status) {
        return new RolePermissionResponseDTO(rolePermission.getId(), rolePermission.getRole().getId(), rolePermission.getPermission().getId(), status);
    }

    /**
     * Convert Role permission list into list
     * @param rolePermissions
     * @param status
     * @return
     */
    public static List<RolePermissionResponseDTO> toPermissionResponseDTOs(List<RolePermission> rolePermissions, ResponseStatus status) {
        List<RolePermissionResponseDTO> responseDTOS = new ArrayList<>();
        rolePermissions.forEach(rolePermission -> {
            responseDTOS.add(toPermissionResponseDTO(rolePermission, status));
        });
        return responseDTOS;
    }

    public static RolePermission toRolePermission(Role role, Permission permission) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);
        return rolePermission;

    }
}

