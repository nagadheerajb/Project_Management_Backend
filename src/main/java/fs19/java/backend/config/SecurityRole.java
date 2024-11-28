package fs19.java.backend.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityRole {
    private HttpMethod method;
    private String role;
    private String permission;
}
