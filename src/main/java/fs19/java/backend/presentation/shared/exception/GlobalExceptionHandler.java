package fs19.java.backend.presentation.shared.exception;

import fs19.java.backend.presentation.shared.response.ErrorItem;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleCompanyNotFoundException(CompanyNotFoundException ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleRoleNotFoundException(Exception ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<Void>> handleGenericException(Exception ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(),
            List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserValidationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<GlobalResponse<Void>> handleValidationException(Exception ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.BAD_REQUEST.value(),
            List.of(error));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WorkspaceNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleWorkspaceNotFoundException(WorkspaceNotFoundException ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleProjectNotFoundException(ProjectNotFoundException ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(),
                List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectValidationException.class)
    public ResponseEntity<GlobalResponse<Void>> handleProjectValidationException(ProjectValidationException ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.BAD_REQUEST.value(),
                List.of(error));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WorkspaceUserNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleWorkspaceUserNotFoundException(WorkspaceUserNotFoundException ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(),
                List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleCommentNotFoundException(CommentNotFoundException ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> NotificationNotFoundException(NotificationNotFoundException ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalResponse<Void> response = new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
