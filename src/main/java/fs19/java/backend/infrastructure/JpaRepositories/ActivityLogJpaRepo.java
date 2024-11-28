package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActivityLogJpaRepo extends JpaRepository<ActivityLog, UUID> {

    @Query("SELECT 'COMPANY' FROM Company c WHERE c.id = :id")
    Optional<String> findCompanyById(@Param("id") UUID id);

    @Query("SELECT 'WORKSPACE' FROM Workspace w WHERE w.id = :id")
    Optional<String> findWorkspaceById(@Param("id") UUID id);

    @Query("SELECT 'PROJECT' FROM Project p WHERE p.id = :id")
    Optional<String> findProjectById(@Param("id") UUID id);

    @Query("SELECT 'TASK' FROM Task t WHERE t.id = :id")
    Optional<String> findTaskById(@Param("id") UUID id);

    @Query(value = """
        SELECT *
        FROM activity_log a
        WHERE
           (a.entity_id = :entityId AND a.entity_type = :entityType)

           OR

           (:entityType = 'COMPANY' AND a.entity_type = 'WORKSPACE' AND a.entity_id IN (
                SELECT w.id FROM workspace w WHERE w.company_id = :entityId
           ))

           OR

           (:entityType = 'COMPANY' AND a.entity_type = 'PROJECT' AND a.entity_id IN (
                SELECT p.id FROM project p WHERE p.workspace_id IN (
                    SELECT w.id FROM workspace w WHERE w.company_id = :entityId
                )
           ))

           OR

           (:entityType = 'COMPANY' AND a.entity_type = 'TASK' AND a.entity_id IN (
                SELECT t.id FROM task t WHERE t.project_id IN (
                    SELECT p.id FROM project p WHERE p.workspace_id IN (
                        SELECT w.id FROM workspace w WHERE w.company_id = :entityId
                    )
                )
           ))

           OR

           (:entityType = 'WORKSPACE' AND a.entity_type = 'PROJECT' AND a.entity_id IN (
                SELECT p.id FROM project p WHERE p.workspace_id = :entityId
           ))

           OR

           (:entityType = 'WORKSPACE' AND a.entity_type = 'TASK' AND a.entity_id IN (
                SELECT t.id FROM task t WHERE t.project_id IN (
                    SELECT p.id FROM project p WHERE p.workspace_id = :entityId
                )
           ))

           OR

           (:entityType = 'PROJECT' AND a.entity_type = 'TASK' AND a.entity_id IN (
                SELECT t.id FROM task t WHERE t.project_id = :entityId
           ))
        ORDER BY a.created_date ASC
    """, nativeQuery = true)
    List<ActivityLog> findLogsByEntity(
            @Param("entityId") UUID entityId,
            @Param("entityType") String entityType
    );
}
