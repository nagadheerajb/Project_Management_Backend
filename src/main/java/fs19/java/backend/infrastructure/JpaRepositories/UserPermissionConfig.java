package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.application.dto.user.UserPermissionsDTO;
import fs19.java.backend.config.SecurityRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserPermissionConfig {
    Optional<UserPermissionsDTO> findPermissionsByUserEmail(String email);
    List<SecurityRole> findAllPermissions();
    Optional<UserPermissionsDTO> findPermissionsByUserEmailAndWorkspaceId(String username, UUID workspaceId);
    List<UUID> findLinkWorkspaceIds(UUID id);
}
