package fs19.java.backend.presentation.shared.exception;

public class UserAlreadyFoundException extends RuntimeException {
  public UserAlreadyFoundException(String message) {
    super(message);
  }
}
