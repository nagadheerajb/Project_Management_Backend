package fs19.java.backend.presentation.shared.exception;

public class ActivityLogNotFoundException extends RuntimeException {
    public ActivityLogNotFoundException(String message) {
        super(message);
    }
}