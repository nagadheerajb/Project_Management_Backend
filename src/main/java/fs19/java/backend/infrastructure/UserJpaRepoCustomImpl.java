package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.user.PermissionDTO;
import fs19.java.backend.application.dto.user.UserPermissionsDTO;
import fs19.java.backend.config.SecurityRole;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.domain.entity.RolePermission;
import fs19.java.backend.domain.entity.enums.PermissionType;
import fs19.java.backend.infrastructure.JpaRepositories.PermissionJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.RolePermissionJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserPermissionConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserJpaRepoCustomImpl implements UserPermissionConfig {

    private final RolePermissionJpaRepo rolePermissionJpaRepo;
    private final PermissionJpaRepo permissionJpaRepo;

    @PersistenceContext
    private EntityManager entityManager;

    public UserJpaRepoCustomImpl(RolePermissionJpaRepo rolePermissionJpaRepo, PermissionJpaRepo permissionJpaRepo) {
        this.rolePermissionJpaRepo = rolePermissionJpaRepo;
        this.permissionJpaRepo = permissionJpaRepo;
    }

    @Override
    public Optional<UserPermissionsDTO> findPermissionsByUserEmailAndWorkspaceId(String email, UUID workspaceId) {
        Query query = entityManager.createNativeQuery(
                        "SELECT u.email as userName,u.password as password, " +
                                "p.id as permissionId, p.name as permissionName " +
                                "FROM users u " +
                                "JOIN workspace_user wu ON wu.user_id = u.id " +
                                "JOIN role_permission r ON wu.role_id = r.role_id " +
                                "JOIN permission p ON r.permission_id = p.id " +
                                "WHERE u.email = :email AND wu.workspace_Id = :workspaceId")
                .setParameter("email", email).setParameter("workspaceId", workspaceId);

        List<Object[]> results = query.getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(mapNativeResult(results));
    }

    @Override
    public List<UUID> findLinkWorkspaceIds(UUID id) {
        return rolePermissionJpaRepo.findLinkWorkspaceIds(id);
    }

    @Override
    public Optional<UserPermissionsDTO> findPermissionsByUserEmail(String email) {
        Query query = entityManager.createNativeQuery(
                        "SELECT u.email as userName,u.password as password, " +
                                "p.id as permissionId, p.name as permissionName " +
                                "FROM users u " +
                                "LEFT JOIN workspace_user wu ON wu.user_id = u.id " +
                                "LEFT JOIN role_permission r ON wu.role_id = r.role_id " +
                                "LEFT JOIN permission p ON r.permission_id = p.id " +
                                "WHERE u.email = :email")
                .setParameter("email", email);

        List<Object[]> results = query.getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(mapNativeResult(results));
    }

    public List<SecurityRole> findAllPermissions() {
        List<SecurityRole> roles = new ArrayList<>();
        List<RolePermission> allRecords = rolePermissionJpaRepo.findAll();
        allRecords.forEach(rp -> {
            SecurityRole securityRole = new SecurityRole();
            Optional<Permission> optionalPermission = permissionJpaRepo.findById(rp.getPermission().getId());
            if (optionalPermission.isPresent()) {
                Permission permission = optionalPermission.get();
                securityRole.setPermission(permission.getUrl());
                securityRole.setRole(permission.getName());
                securityRole.setMethod(getHttpMethod(permission.getPermissionType()));
                roles.add(securityRole);
            }
        });
        return roles;
    }

    private HttpMethod getHttpMethod(PermissionType permissionType) {
        switch (permissionType) {
            case GET -> {
                return HttpMethod.GET;
            }
            case POST -> {
                return HttpMethod.POST;
            }
            case PUT -> {
                return HttpMethod.PUT;
            }
            case DELETE -> {
                return HttpMethod.DELETE;
            }

        }
        return HttpMethod.GET;
    }

    private UserPermissionsDTO mapNativeResult(List<Object[]> results) {
        UserPermissionsDTO userPermissionsDTO = new UserPermissionsDTO();
        List<PermissionDTO> permissions = new ArrayList<>();

        for (Object[] row : results) {
            if (userPermissionsDTO.getUserName() == null) {
                userPermissionsDTO.setUserName((String) row[0]);
                userPermissionsDTO.setPassword((String) row[1]);
            }
            if (row[2] != null) {
                PermissionDTO permissionDTO = new PermissionDTO();
                permissionDTO.setPermissionId(((UUID) row[2]));
                permissionDTO.setPermissionName((String) row[3]);
                permissions.add(permissionDTO);
            }
        }

        userPermissionsDTO.setPermissions(permissions);
        return userPermissionsDTO;
    }
}