package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.WorkspaceUser;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkspaceUserJpaRepo extends JpaRepository<WorkspaceUser, UUID> {
    List<WorkspaceUser> findByUser(User user);
    @Query("SELECT wu FROM WorkspaceUser wu " +
            "JOIN FETCH wu.workspace w " +
            "JOIN FETCH w.companyId c " +
            "WHERE wu.user.id = :userId")
    List<WorkspaceUser> findAllByUserIdWithCompany(@Param("userId") UUID userId);
}
