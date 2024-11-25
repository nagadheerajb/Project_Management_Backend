package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.project.ProjectCreateDTO;
import fs19.java.backend.application.dto.project.ProjectReadDTO;
import fs19.java.backend.domain.entity.Project;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProjectMapper {

  public static ProjectReadDTO toReadDTO(Project project) {
    return ProjectReadDTO.builder()
        .id(project.getId())
        .name(project.getName())
        .description(project.getDescription())
        .createdDate(project.getCreatedDate())
        .startDate(project.getStartDate())
        .endDate(project.getEndDate())
        .createdByUserId(project.getCreatedByUser().getId())
        .workspaceId(project.getWorkspace().getId())
        .status(project.getStatus())
        .build();
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
