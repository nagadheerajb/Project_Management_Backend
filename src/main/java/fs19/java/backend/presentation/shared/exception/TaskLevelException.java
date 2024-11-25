package fs19.java.backend.presentation.shared.exception;

public class TaskLevelException extends RuntimeException {
    public static final String TASK_CREATE = "Occurred while create a new Record";
    public static final String TASK_UPDATE = "Occurred while update a Record";
    public static final String TASK_DELETE = "Occurred while delete a Record";


    public TaskLevelException(String message) {
        super(message);
    }
}