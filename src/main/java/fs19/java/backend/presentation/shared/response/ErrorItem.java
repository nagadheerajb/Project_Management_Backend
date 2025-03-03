package fs19.java.backend.presentation.shared.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ErrorItem {

    private final String message;
    private String errorCode;

    public ErrorItem(String message) {
        this.message = message;
    }

    public ErrorItem(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
