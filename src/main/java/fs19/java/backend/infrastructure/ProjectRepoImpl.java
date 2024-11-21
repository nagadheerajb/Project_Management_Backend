package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.abstraction.ProjectRepository;
import fs19.java.backend.domain.entity.Project;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProjectRepoImpl implements ProjectRepository {
  private final Map<UUID, Project> inMemoryProjectDatabase = new ConcurrentHashMap<>();

  @Override
  public void saveProject(Project project) {
    inMemoryProjectDatabase.put(project.getId(), project);
  }

  @Override
  public List<Project> findAllProjects() {
    return new ArrayList<>(inMemoryProjectDatabase.values());
  }

  @Override
  public void deleteProject(Project project) {
    inMemoryProjectDatabase.remove(project.getId());
  }

  @Override
  public Optional<Project> findById(UUID id) {
    return Optional.ofNullable(inMemoryProjectDatabase.get(id));
  }
}
