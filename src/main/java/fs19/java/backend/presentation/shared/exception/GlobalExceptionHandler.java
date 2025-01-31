package fs19.java.backend.presentation.shared.exception;

import fs19.java.backend.presentation.shared.response.ErrorItem;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleCompanyNotFoundException(CompanyNotFoundException ex) {
        logger.error("CompanyNotFoundException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleRoleNotFoundException(Exception ex) {
        logger.error("RoleNotFoundException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleUserNotFoundException(UserNotFoundException ex) {
        logger.error("UserNotFoundException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleUserAlreadyFoundException(UserAlreadyFoundException ex) {
        logger.error("UserAlreadyFoundException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.CONFLICT.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<GlobalResponse<Void>> handleUserValidationException(UserValidationException ex) {
        logger.error("UserValidationException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.BAD_REQUEST.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WorkspaceNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleWorkspaceNotFoundException(WorkspaceNotFoundException ex) {
        logger.error("WorkspaceNotFoundException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleProjectNotFoundException(ProjectNotFoundException ex) {
        logger.error("ProjectNotFoundException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectValidationException.class)
    public ResponseEntity<GlobalResponse<Void>> handleProjectValidationException(ProjectValidationException ex) {
        logger.error("ProjectValidationException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.BAD_REQUEST.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WorkspaceUserNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleWorkspaceUserNotFoundException(WorkspaceUserNotFoundException ex) {
        logger.error("WorkspaceUserNotFoundException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleCommentNotFoundException(CommentNotFoundException ex) {
        logger.error("CommentNotFoundException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleNotificationNotFoundException(NotificationNotFoundException ex) {
        logger.error("NotificationNotFoundException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PermissionLevelException.class)
    public ResponseEntity<GlobalResponse<Void>> handlePermissionLevelException(PermissionLevelException ex) {
        logger.error("PermissionLevelException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RoleLevelException.class)
    public ResponseEntity<GlobalResponse<Void>> handleRoleLevelException(RoleLevelException ex) {
        logger.error("RoleLevelException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RolePermissionLevelException.class)
    public ResponseEntity<GlobalResponse<Void>> handleRolePermissionLevelException(RolePermissionLevelException ex) {
        logger.error("RolePermissionLevelException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TaskLevelException.class)
    public ResponseEntity<GlobalResponse<Void>> handleTaskLevelException(TaskLevelException ex) {
        logger.error("TaskLevelException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(InvitationLevelException.class)
    public ResponseEntity<GlobalResponse<Void>> handleInvitationLevelException(InvitationLevelException ex) {
        logger.error("InvitationLevelException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ActivityLogNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleActivityLogNotFoundException(ActivityLogNotFoundException ex) {
        logger.error("ActivityLogNotFoundException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem("Invalid request: " + ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.BAD_REQUEST.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInvitationFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleIlleInvalidInvitationException(InvalidInvitationFoundException ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.BAD_REQUEST.value(),
                List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(AuthenticationNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleAccessDeniedException(AuthenticationNotFoundException ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.FORBIDDEN.value(),
                List.of(error));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CredentialNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleCredentialNotFoundException(CredentialNotFoundException ex) {
        logger.error("CredentialNotFoundException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.FORBIDDEN.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GlobalResponse<Void>> handleBadCredentialsException(BadCredentialsException ex) {
        logger.error("BadCredentialsException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem(ex.getCause() instanceof UserValidationException
                ? ex.getCause().getMessage()
                : "Invalid email or password.");
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.UNAUTHORIZED.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        logger.error("UsernameNotFoundException: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem("User not found.");
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<Void>> handleGenericException(Exception ex) {
        logger.error("Unexpected Exception: {}", ex.getMessage(), ex);
        ErrorItem error = new ErrorItem("An unexpected error occurred. Please try again later.");
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
