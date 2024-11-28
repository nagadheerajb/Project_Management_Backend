package fs19.java.backend.presentation.shared.exception;

public class CredentialNotFoundException extends RuntimeException {
    public CredentialNotFoundException(String message) {
        super(message);
    }
}