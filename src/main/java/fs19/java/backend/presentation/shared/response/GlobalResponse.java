package fs19.java.backend.presentation.shared.response;


import lombok.Getter;
import java.util.List;

@Getter
public class GlobalResponse<T> {
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";

    private final String status;
    private final int code;
    private final T data;
    private final List<ErrorItem> errors;

    public GlobalResponse(int code, T data) {
        this.status = SUCCESS;
        this.code = code;
        this.data = data;
        this.errors = null;
    }

    public GlobalResponse(int code, List<ErrorItem> errors) {
        this.status = ERROR;
        this.code = code;
        this.data = null;
        this.errors = errors;
    }

    public GlobalResponse(int code, T data, List<ErrorItem> errors) {
        this.status = errors.isEmpty() ? SUCCESS : ERROR;
        this.code = code;
        this.data = data;
        this.errors = errors;
    }
}
