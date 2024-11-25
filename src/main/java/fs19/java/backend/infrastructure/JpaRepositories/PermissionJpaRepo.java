package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionJpaRepo extends JpaRepository<Permission, UUID> {
    @Query("SELECT p FROM Permission p WHERE p.name = :name")
    Permission findByName(@Param("name") String name);
}
