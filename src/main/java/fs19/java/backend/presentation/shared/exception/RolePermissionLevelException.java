package fs19.java.backend.presentation.shared.exception;

public class RolePermissionLevelException extends RuntimeException {
    public static final String ROLE_PERMISSION_CREATE = "Occurred while create a new Record";

    public RolePermissionLevelException(String message) {
        super(message);
    }
}