package fs19.java.backend.domain.abstraction;

import fs19.java.backend.domain.entity.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository {
    void saveProject(Project project);
    List<Project> findAllProjects();
    Optional<Project> findById(UUID id);
    void deleteProject(Project user);

}
