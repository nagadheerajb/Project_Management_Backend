package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.project.ProjectCreateDTO;
import fs19.java.backend.application.dto.project.ProjectReadDTO;
import fs19.java.backend.domain.entity.Project;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProjectMapper {

  public static ProjectReadDTO toReadDTO(Project project) {
    ProjectReadDTO dto = new ProjectReadDTO();
    dto.setId(project.getId());
    dto.setName(project.getName());
    dto.setDescription(project.getDescription());
    dto.setCreatedDate(project.getCreatedDate());
    dto.setStartDate(project.getStartDate());
    dto.setEndDate(project.getEndDate());
    dto.setWorkspaceId(project.getWorkspaceId());
    dto.setStatus(project.getStatus());
    return dto;
  }

  public static Project toEntity(ProjectCreateDTO dto) {
    Project project = new Project();
    project.setId(dto.getId());
    project.setName(dto.getName());
    project.setDescription(dto.getDescription());
    project.setStartDate(dto.getStartDate());
    project.setEndDate(dto.getEndDate());
    project.setStatus(dto.getStatus());
    return project;
  }

}
