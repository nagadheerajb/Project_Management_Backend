package fs19.java.backend.application.dto.user;

import java.util.UUID;
import lombok.Data;

@Data
public class PermissionDTO {
  private UUID permissionId;
  private String permissionName;
}
