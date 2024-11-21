package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.abstraction.WorkspaceUsersRepository;
import fs19.java.backend.domain.entity.WorkspaceUsers;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class WorkspaceUsersRepoImpl implements WorkspaceUsersRepository {
  private final Map<UUID, WorkspaceUsers> inMemoryWorkspaceUsersDatabase = new ConcurrentHashMap<>();

@Override
public void save(WorkspaceUsers workspaceUsers) {
  inMemoryWorkspaceUsersDatabase.put(workspaceUsers.getId(), workspaceUsers);
}

@Override
public Optional<WorkspaceUsers> findById(UUID id) {
  return Optional.ofNullable(inMemoryWorkspaceUsersDatabase.get(id));
}

@Override
public List<WorkspaceUsers> findAll() {
  return new ArrayList<>(inMemoryWorkspaceUsersDatabase.values());
}

@Override
public void deleteById(UUID id) {
  inMemoryWorkspaceUsersDatabase.remove(id);
}

@Override
public boolean existsById(UUID id) {
  return inMemoryWorkspaceUsersDatabase.containsKey(id);
}
}
