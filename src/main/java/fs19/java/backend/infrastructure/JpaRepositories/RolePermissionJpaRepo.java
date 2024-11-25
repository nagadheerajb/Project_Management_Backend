package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RolePermissionJpaRepo extends JpaRepository<RolePermission, UUID> {
    @Query(value = "SELECT * " +
            "FROM role_permission rp " +
            "WHERE rp.role_id = :roleId ",
            nativeQuery = true)
    List<RolePermission> findByRoleId(@Param("roleId") UUID roleId);

    @Query(value = "SELECT * " +
            "FROM role_permission rp " +
            "WHERE rp.permission_id = :permissionId ",
            nativeQuery = true)
    List<RolePermission> findByPermissionId(@Param("permissionId") UUID permissionId);

    @Query(value = "SELECT * " +
            "FROM role_permission rp " +
            "WHERE rp.permission_id = :permissionId AND rp.role_id = :roleId ",   nativeQuery = true)
    RolePermission findByPermissionIdAndRoleId(@Param("permissionId") UUID permissionId, @Param("roleId") UUID roleId);

}
