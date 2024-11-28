package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepo extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
