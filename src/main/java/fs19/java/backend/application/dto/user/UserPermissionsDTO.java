package fs19.java.backend.application.dto.user;

import java.util.List;
import lombok.Data;

@Data
public class UserPermissionsDTO {
  private String userName;
  private String password;
  private List<PermissionDTO> permissions;
}
