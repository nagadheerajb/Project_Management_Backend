package fs19.java.backend.presentation.shared.exception;

public class AuthenticationNotFoundException extends RuntimeException {
    public AuthenticationNotFoundException(String message) {
        super(message);
    }
}