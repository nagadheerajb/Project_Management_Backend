package fs19.java.backend.application;

import fs19.java.backend.application.dto.comment.CommentRequestDTO;
import fs19.java.backend.application.dto.comment.CommentResponseDTO;
import fs19.java.backend.application.dto.comment.CommentUpdateDTO;
import fs19.java.backend.application.events.GenericEvent;
import fs19.java.backend.application.mapper.CommentMapper;
import fs19.java.backend.application.service.CommentService;
import fs19.java.backend.domain.entity.Comment;
import fs19.java.backend.domain.entity.Task;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.infrastructure.JpaRepositories.CommentJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.TaskJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.exception.CommentNotFoundException;
import fs19.java.backend.presentation.shared.exception.TaskLevelException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LogManager.getLogger(CommentServiceImpl.class);

    private static final String COMMENT_NOT_FOUND_MESSAGE = "Comment with ID %s not found";
    private static final String TASK_NOT_FOUND_MESSAGE = "Task with ID %s not found";
    private static final String USER_NOT_FOUND_MESSAGE = "User with ID %s not found";

    private final CommentJpaRepo commentRepository;
    private final TaskJpaRepo taskRepository;
    private final UserJpaRepo userRepository;
    private final CommentMapper commentMapper;
    private final ApplicationEventPublisher eventPublisher;

    public CommentServiceImpl(CommentJpaRepo commentRepository, TaskJpaRepo taskRepository, UserJpaRepo userRepository, CommentMapper commentMapper, ApplicationEventPublisher eventPublisher) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO) {
        logger.info("Creating comment: {}", commentRequestDTO);
        if (commentRequestDTO == null) {
            throw new IllegalArgumentException("CommentRequestDTO must not be null");
        }

        User createdBy = userRepository.findById(commentRequestDTO.getCreatedBy())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, commentRequestDTO.getCreatedBy())));
        Task task = taskRepository.findById(commentRequestDTO.getTaskId())
                .orElseThrow(() -> new TaskLevelException(String.format(TASK_NOT_FOUND_MESSAGE, commentRequestDTO.getTaskId())));

        Comment comment = commentMapper.toEntity(commentRequestDTO, task, createdBy);
        Comment savedComment = commentRepository.save(comment);
        logger.info("Comment created successfully: {}", savedComment);
        eventPublisher.publishEvent(new GenericEvent<>(this, savedComment, EntityType.COMMENT, "Created"));
        return commentMapper.toDTO(savedComment);
    }

    @Override
    public CommentResponseDTO updateComment(UUID id, CommentUpdateDTO commentUpdateDTO) {
        logger.info("Updating comment with ID: {} and DTO: {}", id, commentUpdateDTO);
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(String.format(COMMENT_NOT_FOUND_MESSAGE, id)));

        existingComment.setContent(commentUpdateDTO.getContent());
        Comment savedComment = commentRepository.save(existingComment);
        logger.info("Comment updated successfully: {}", savedComment);
        eventPublisher.publishEvent(new GenericEvent<>(this, savedComment, EntityType.COMMENT, "Updated"));
        return commentMapper.toDTO(savedComment);
    }

    @Override
    public CommentResponseDTO getCommentById(UUID id) {
        logger.info("Retrieving comment with ID: {}", id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(String.format(COMMENT_NOT_FOUND_MESSAGE, id)));

        logger.info("Comment retrieved successfully: {}", comment);
        return commentMapper.toDTO(comment);
    }

    @Override
    public List<CommentResponseDTO> getAllComments() {
        logger.info("Retrieving all comments");
        List<CommentResponseDTO> comments = commentRepository.findAll().stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
        logger.info("All comments retrieved successfully");
        return comments;
    }

    @Override
    public void deleteComment(UUID id) {
        logger.info("Deleting comment with ID: {}", id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Comment with ID: {} not found for deletion", id);
                    return new CommentNotFoundException(String.format(COMMENT_NOT_FOUND_MESSAGE, id));
                });

        commentRepository.deleteById(id);
        logger.info("Comment with ID: {} deleted successfully", id);
        eventPublisher.publishEvent(new GenericEvent<>(this, comment, EntityType.COMMENT, "Deleted"));
        //User createdBy = SecurityUtils.getCurrentUser();
        //activityLoggerService.logActivity(EntityType.COMPANY, id, ActionType.DELETED, createdBy.getId());
    }

    // New method to fetch all comments for a task
    @Override
    public Page<CommentResponseDTO> getAllCommentsForTask(UUID taskId, Pageable pageable) {
        logger.info("Retrieving paginated comments for task with ID: {}", taskId);
        Page<Comment> comments = commentRepository.findAllByTaskId(taskId, pageable);
        Page<CommentResponseDTO> commentDTOs = comments.map(commentMapper::toDTO);
        logger.info("Paginated comments for task retrieved successfully");
        return commentDTOs;
    }


    // New method to bulk delete comments by task ID
    @Override
    @Transactional
    public void deleteCommentsByTaskId(UUID taskId) {
        logger.info("Deleting all comments for task with ID: {}", taskId);
        List<Comment> comments = commentRepository.findAllByTaskId(taskId);
        if (!comments.isEmpty()) {
            commentRepository.deleteAllInBatchByTaskId(taskId); // Optimized bulk deletion
            logger.info("All comments for task deleted successfully");
            //eventPublisher.publishEvent(new GenericEvent<>(this, comments, EntityType.COMMENT, "Deleted"));
        } else {
            logger.warn("No comments found for task ID: {}", taskId);
        }
    }

    // New method to support pagination and filtering
    @Override
    public Page<CommentResponseDTO> getAllComments(Pageable pageable) {
        logger.info("Retrieving all comments with pagination and filtering");
        Page<Comment> comments = commentRepository.findAll(pageable);
        Page<CommentResponseDTO> commentDTOs = comments.map(commentMapper::toDTO);
        logger.info("All comments retrieved successfully with pagination and filtering");
        return commentDTOs;
    }
}