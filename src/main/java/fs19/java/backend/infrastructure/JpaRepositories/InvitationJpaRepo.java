package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvitationJpaRepo extends JpaRepository<Invitation, UUID> {

    @Query(value = "SELECT * " +
            "FROM invitation i " +
            "WHERE i.workspace_id = :workspaceId AND i.role_id = :roleId AND i.email = :email ", nativeQuery = true)
    Invitation finByEmailRoleIdAndWorkspaceId(@Param("email") String email, @Param("roleId") UUID roleId, @Param("workspaceId") UUID workspaceId);

}
