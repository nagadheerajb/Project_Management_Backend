package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.workspace.WorkspaceRequestDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceResponseDTO;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceMapper {

    public Workspace toEntity(WorkspaceRequestDTO workspaceRequestDTO, User createdBy, Company company) {
        Workspace workspace = new Workspace();
        workspace.setName(workspaceRequestDTO.getName());
        workspace.setDescription(workspaceRequestDTO.getDescription());
        workspace.setType(workspaceRequestDTO.getType());
        workspace.setCreatedDate(null); // Set createdDate to null initially; it will be set when persisted
        workspace.setCreatedBy(createdBy);
        workspace.setCompanyId(company);
        return workspace;
    }

    public WorkspaceResponseDTO toDTO(Workspace workspace) {
        return new WorkspaceResponseDTO(
                workspace.getId(),
                workspace.getName(),
                workspace.getDescription(),
                workspace.getType(),
                workspace.getCreatedDate(),
                workspace.getCreatedBy().getId(),
                workspace.getCompanyId().getId()
        );
    }
}