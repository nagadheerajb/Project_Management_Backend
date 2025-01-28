package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommentJpaRepo extends JpaRepository<Comment, UUID> {

    @Query("SELECT c FROM Comment c WHERE c.taskId.id = :taskId")
    Page<Comment> findAllByTaskId(UUID taskId, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.taskId.id = :taskId")
    List<Comment> findAllByTaskId(UUID taskId);

    @Override
    <S extends Comment> List<S> saveAll(Iterable<S> entities);

    @Override
    default void deleteAll(Iterable<? extends Comment> entities) {
        List<Comment> comments = (List<Comment>) entities;
        comments.forEach(comment -> {
            if (comment.getId() != null) { // NullPointerException fix
                deleteById(comment.getId());
            }
        });
    }

    @Override
    Page<Comment> findAll(Pageable pageable);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.taskId.id = :taskId")
    void deleteAllInBatchByTaskId(@Param("taskId") UUID taskId);
}
