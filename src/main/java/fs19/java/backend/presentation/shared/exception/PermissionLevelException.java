package fs19.java.backend.presentation.shared.exception;

public class PermissionLevelException extends RuntimeException {
    public static final String PERMISSION_CREATE = "Occurred while create a new Record";

    public PermissionLevelException(String message) {
        super(message);
    }
}