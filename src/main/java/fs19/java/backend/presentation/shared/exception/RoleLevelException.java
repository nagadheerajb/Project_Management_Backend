package fs19.java.backend.presentation.shared.exception;

public class RoleLevelException extends RuntimeException {
    public static final String ROLE_CREATE = "Occurred while create a new Record";

    public RoleLevelException(String message) {
        super(message);
    }
}