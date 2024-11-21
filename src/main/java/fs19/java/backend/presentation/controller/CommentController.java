package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.comment.CommentRequestDTO;
import fs19.java.backend.application.dto.comment.CommentResponseDTO;
import fs19.java.backend.application.dto.comment.CommentUpdateDTO;
import fs19.java.backend.application.service.CommentService;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Comment", description = "Manage comments")
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Create a new comment", description = "Creates a new comment with the provided details")
    @PostMapping
    public ResponseEntity<GlobalResponse<CommentResponseDTO>> createComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO) {
        System.out.println("Received create comment request: " + commentRequestDTO);

        CommentResponseDTO createdComment = commentService.createComment(commentRequestDTO);

        System.out.println("Returning created comment with ID: " + createdComment.getId());

        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdComment), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a comment", description = "Updates the details of an existing comment")
    @PutMapping("/{commentId}")
    public ResponseEntity<GlobalResponse<CommentResponseDTO>> updateComment(@PathVariable UUID commentId, @Valid @RequestBody CommentUpdateDTO commentUpdateDTO) {
        System.out.println("Received update comment request for ID: " + commentId);
        System.out.println("Update content: " + commentUpdateDTO.getContent());

        CommentResponseDTO updatedComment = commentService.updateComment(commentId, commentUpdateDTO);

        System.out.println("Returning updated comment with ID: " + updatedComment.getId());

        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedComment), HttpStatus.OK);
    }

    @Operation(summary = "Get a comment by ID", description = "Retrieves the details of a comment by its ID")
    @GetMapping("/{commentId}")
    public ResponseEntity<GlobalResponse<CommentResponseDTO>> getCommentById(@PathVariable UUID commentId) {
        CommentResponseDTO comment = commentService.getCommentById(commentId);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), comment), HttpStatus.OK);
    }

    @Operation(summary = "Get all comments", description = "Fetches all comments")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<CommentResponseDTO>>> getAllComments() {
        List<CommentResponseDTO> comments = commentService.getAllComments();
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), comments), HttpStatus.OK);
    }

    @Operation(summary = "Delete a comment", description = "Deletes a comment by its ID")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<GlobalResponse<Void>> deleteComment(@PathVariable UUID commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null), HttpStatus.NO_CONTENT);
    }
}