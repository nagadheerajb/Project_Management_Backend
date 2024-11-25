package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentJpaRepo extends JpaRepository<Comment, UUID> {
}