package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.domain.entity.RolePermission;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.WorkspaceUser;
import fs19.java.backend.infrastructure.JpaRepositories.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AuthRepoImpl {

    private final RoleJpaRepo roleJpaRepo;
    private final RolePermissionJpaRepo rolePermissionJpaRepo;
    private final PermissionJpaRepo permissionJpaRepo;
    private final WorkspaceUserJpaRepo workspaceUserJpaRepo;
    private final UserJpaRepo userRepo;


    public AuthRepoImpl(RoleJpaRepo roleJpaRepo, RolePermissionJpaRepo rolePermissionJpaRepo, PermissionJpaRepo permissionJpaRepo, WorkspaceUserJpaRepo workspaceUserJpaRepo, UserJpaRepo userRepo) {
        this.roleJpaRepo = roleJpaRepo;
        this.rolePermissionJpaRepo = rolePermissionJpaRepo;
        this.permissionJpaRepo = permissionJpaRepo;
        this.workspaceUserJpaRepo = workspaceUserJpaRepo;
        this.userRepo = userRepo;
    }

    public Optional<User> findByEmail(String username) {
        return userRepo.findByEmail(username);
    }

    public User saveUser(User user) {
        userRepo.save(user);
        return user;
    }

    public List<WorkspaceUser> getUserWorkSpaces(UUID userId) {
        WorkspaceUser workspaceUser = new WorkspaceUser();
        workspaceUser.setUser(userRepo.findById(userId).orElse(null));
        if (workspaceUser.getUser() == null) {
            return null;
        }
        Example<WorkspaceUser> example = Example.of(workspaceUser,
                ExampleMatcher.matchingAll()
                        .withIgnoreNullValues()
                        .withMatcher("user_id", ExampleMatcher.GenericPropertyMatchers.exact()));
        return workspaceUserJpaRepo.findAll(example);

    }

    public List<Permission> getPermissions(UUID roleId) {
        List<Permission> permissions = new ArrayList<>();
        List<RolePermission> rolePermissions = rolePermissionJpaRepo.findByRoleId(roleId);
        rolePermissions.forEach(rp -> {
            Optional<Permission> permission = permissionJpaRepo.findById(rp.getPermission().getId());
            permission.ifPresent(permissions::add);
        });
        return permissions;
    }


}
