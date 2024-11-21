package fs19.java.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceUsers {
  private UUID id;
  private UUID userId;
  private UUID roleId;
  private UUID workspaceId;
}
