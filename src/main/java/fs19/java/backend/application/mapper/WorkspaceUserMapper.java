package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.workspace_user.WorkspaceUserResponseDTO;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.domain.entity.WorkspaceUser;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceUserMapper {

    public static WorkspaceUser toEntity(User user, Role role,
                                         Workspace workspace) {
        WorkspaceUser workspaceUser = new WorkspaceUser();
        workspaceUser.setRole(role);
        workspaceUser.setUser(user);
        workspaceUser.setWorkspace(workspace);
        return workspaceUser;
    }

    public static WorkspaceUserResponseDTO toDTO(@NotNull WorkspaceUser workspaceUser) {
        WorkspaceUserResponseDTO workspaceUsersDTO = new WorkspaceUserResponseDTO();
        workspaceUsersDTO.setId(workspaceUser.getId());
        workspaceUsersDTO.setRoleId(workspaceUser.getRole().getId());
        workspaceUsersDTO.setUserId(workspaceUser.getUser().getId());
        workspaceUsersDTO.setWorkspaceId(workspaceUser.getWorkspace().getId());
        workspaceUsersDTO.setWorkspaceName(workspaceUser.getWorkspace().getName());

        // Add company details
        workspaceUsersDTO.setCompanyId(workspaceUser.getWorkspace().getCompanyId().getId());
        workspaceUsersDTO.setCompanyName(workspaceUser.getWorkspace().getCompanyId().getName());

        return workspaceUsersDTO;
    }



}
