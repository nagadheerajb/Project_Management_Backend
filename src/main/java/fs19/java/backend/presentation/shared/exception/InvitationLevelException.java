package fs19.java.backend.presentation.shared.exception;

public class InvitationLevelException extends RuntimeException {
    public static final String INVITATION_CREATE = "Occurred while create a new Record";
    public static final String INVITATION_UPDATE = "Occurred while update a Record";
    public static final String INVITATION_DELETE = "Occurred while delete a Record";


    public InvitationLevelException(String message) {
        super(message);
    }
}