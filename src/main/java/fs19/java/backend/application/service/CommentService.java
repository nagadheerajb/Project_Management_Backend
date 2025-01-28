package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.comment.CommentRequestDTO;
import fs19.java.backend.application.dto.comment.CommentResponseDTO;
import fs19.java.backend.application.dto.comment.CommentUpdateDTO; // Import CommentUpdateDTO
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO);
    CommentResponseDTO updateComment(UUID id, CommentUpdateDTO commentUpdateDTO); // Change method signature
    CommentResponseDTO getCommentById(UUID id);
    List<CommentResponseDTO> getAllComments();
    void deleteComment(UUID id);
    void deleteCommentsByTaskId(UUID taskId);
    Page<CommentResponseDTO> getAllComments(Pageable pageable);
    Page<CommentResponseDTO> getAllCommentsForTask(UUID taskId, Pageable pageable);
}