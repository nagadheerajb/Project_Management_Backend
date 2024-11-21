package fs19.java.backend.domain.abstraction;

import fs19.java.backend.domain.entity.WorkspaceUsers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceUsersRepository {
  void save(WorkspaceUsers workspaceUsers);
  Optional<WorkspaceUsers> findById(UUID id);
  List<WorkspaceUsers> findAll();
  void deleteById(UUID id);
  boolean existsById(UUID id);
}
