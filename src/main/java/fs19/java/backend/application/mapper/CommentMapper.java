package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.comment.CommentRequestDTO;
import fs19.java.backend.application.dto.comment.CommentResponseDTO;
import fs19.java.backend.domain.entity.Comment;
import fs19.java.backend.domain.entity.Task;
import fs19.java.backend.domain.entity.User;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.UUID;

@Component
public class CommentMapper {

    public Comment toEntity(CommentRequestDTO commentRequestDTO, Task task, User createdBy) {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        comment.setTaskId(task);
        comment.setContent(commentRequestDTO.getContent());
        comment.setCreatedDate(ZonedDateTime.now());
        comment.setCreatedBy(createdBy);
        return comment;
    }

    public CommentResponseDTO toDTO(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getTaskId().getId(),
                comment.getContent(),
                comment.getCreatedDate(),
                comment.getCreatedBy().getId()
        );
    }
}