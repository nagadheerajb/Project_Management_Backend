package fs19.java.backend.infrastructure.tempMemory;

import fs19.java.backend.application.dto.invitation.InvitationRequestDTO;
import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.domain.entity.*;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.*;

/**
 * Use as temporary  DB until JPA configuration
 */
@Component
public class RoleInMemoryDB {

    private final List<Role> existing_roles;
    private final List<Permission> existing_permission;
    private final List<RolePermission> existing_role_permission;
    private final List<Task> existing_task;
    private final List<Invitation> existing_invitation;

    public RoleInMemoryDB() {
        existing_permission = new ArrayList<>();
        existing_invitation = new ArrayList<>();
        existing_role_permission = new ArrayList<>();
        existing_roles = new ArrayList<>();
        existing_task = new ArrayList<>();
        existing_roles.add(new Role(UUID.randomUUID(), "DEV", DateAndTime.getDateAndTime(), new Company()));
        existing_roles.add(new Role(UUID.randomUUID(), "QA", DateAndTime.getDateAndTime(), new Company()));
        existing_roles.add(new Role(UUID.randomUUID(), "PM", DateAndTime.getDateAndTime(), new Company()));

        existing_permission.add(new Permission(UUID.randomUUID(), "READ_ACCESS"));
        existing_permission.add(new Permission(UUID.randomUUID(), "WRITE_ACCESS"));
        existing_permission.add(new Permission(UUID.randomUUID(), "ADMIN_ACCESS"));
        existing_permission.add(new Permission(UUID.randomUUID(), "VIEW_ACCESS"));


        existing_role_permission.add(new RolePermission(UUID.randomUUID(), existing_roles.getFirst(), existing_permission.getFirst()));
        existing_invitation.add(new Invitation(UUID.randomUUID(), false, DateAndTime.getDateAndTime(), "abc@gmail.com", existing_roles.getFirst(), new Company()));
    }

    public Role createRole(RoleRequestDTO roleRequestDTO, Company company) {
        Role myRole = new Role(UUID.randomUUID(), roleRequestDTO.getName(), DateAndTime.getDateAndTime(), company);
        existing_roles.add(myRole);
        return myRole;
    }

    public Permission createPermission(PermissionRequestDTO permissionRequestDTO) {
        Permission myPermission = new Permission(UUID.randomUUID(), permissionRequestDTO.getName());
        existing_permission.add(myPermission);
        return myPermission;
    }

    public Task createTask(TaskRequestDTO taskRequestDTO, User createduser, User asssignedUser) {
        Task task = new Task(UUID.randomUUID(), taskRequestDTO.getName(), taskRequestDTO.getDescription(), DateAndTime.getDateAndTime(), taskRequestDTO.getResolvedDate(), taskRequestDTO.getDueDate(), taskRequestDTO.getAttachments(), taskRequestDTO.getTaskStatus(), taskRequestDTO.getProjectId(), createduser, asssignedUser, taskRequestDTO.getPriority());
        existing_task.add(task);
        return task;
    }


    public Role updateRole(UUID roleId, RoleRequestDTO role, Company company) {
        Role myRole = null;
        // Find the role based on ID
        Optional<Role> roleOptional = existing_roles.stream()
                .filter(role1 -> role1.getId().toString().equalsIgnoreCase(roleId.toString()))
                .findFirst();

        if (roleOptional.isPresent()) {
            myRole = roleOptional.get();
            // Update the role properties
            existing_roles.remove(myRole);
            myRole.setName(role.getName());
            myRole.setCompany(company);
            myRole.setCreatedDate(DateAndTime.getDateAndTime());
            existing_roles.add(myRole);
        } else {
            System.out.println("Role with ID " + roleId + " not found in existing_roles.");
        }
        return myRole;
    }

    public Permission updatePermission(UUID permissionId, PermissionRequestDTO permissionRequestDTO) {
        Permission myPermission = null;
        Optional<Permission> permissionOptional = existing_permission.stream()
                .filter(p -> p.getId().toString().equalsIgnoreCase(permissionId.toString()))
                .findFirst();

        if (permissionOptional.isPresent()) {
            myPermission = permissionOptional.get();
            existing_permission.remove(myPermission);
            myPermission.setName(permissionRequestDTO.getName());
            existing_permission.add(myPermission);
        } else {
            System.out.println("Permission with ID " + permissionId + " not found in existing_permissions.");
        }
        return myPermission;
    }


    public RolePermission updateRolePermission(UUID rolePermissionId, RolePermissionRequestDTO rolePermissionRequestDTO) {
        RolePermission myRolePermission = null;
        Optional<RolePermission> permissionOptional = existing_role_permission.stream()
                .filter(p -> p.getId().toString().equalsIgnoreCase(rolePermissionId.toString()))
                .findFirst();

        if (permissionOptional.isPresent()) {
            myRolePermission = permissionOptional.get();
            existing_role_permission.remove(myRolePermission);
            myRolePermission.setPermission(findPermissionById(rolePermissionRequestDTO.getPermissionId()));
            myRolePermission.setRole(findRoleById(rolePermissionRequestDTO.getId()));
            existing_role_permission.add(myRolePermission);
        } else {
            System.out.println("Role-Permission with ID " + rolePermissionId + " not found in existing_roles.");
        }
        return myRolePermission;
    }


    public Role deleteRole(UUID roleId) {
        Role myRole = null;
        Optional<Role> roleOptional = existing_roles.stream()
                .filter(role1 -> role1.getId().toString().equalsIgnoreCase(roleId.toString()))
                .findFirst();
        if (roleOptional.isPresent()) {
            myRole = roleOptional.get();
            existing_roles.remove(myRole);
        } else {
            System.out.println("Role with ID " + roleId.toString() + " not found in existing Permission.");
        }
        return myRole;
    }

    public Permission deletePermission(UUID permissionId) {
        Permission myPermission = null;
        Optional<Permission> permissionOptional = existing_permission.stream()
                .filter(p -> p.getId().toString().equalsIgnoreCase(permissionId.toString()))
                .findFirst();
        if (permissionOptional.isPresent()) {
            myPermission = permissionOptional.get();
            existing_permission.remove(myPermission);
        } else {
            System.out.println("Permission with ID " + permissionId + " not found in existing Permission.");
        }
        return myPermission;
    }

    public Invitation deleteInvitation(UUID invitationId) {
        Invitation invitation = null;
        Optional<Invitation> invitationOptional = existing_invitation.stream()
                .filter(p -> p.getId().toString().equalsIgnoreCase(invitationId.toString()))
                .findFirst();
        if (invitationOptional.isPresent()) {
            invitation = invitationOptional.get();
            existing_invitation.remove(invitation);
        } else {
            System.out.println("Invitation with ID " + invitationId + " not found in existing Permission.");
        }
        return invitation;
    }


    public List<Role> findAllRoles() {
        return existing_roles;
    }

    public List<Permission> findAllPermissions() {
        return existing_permission;
    }

    public List<RolePermission> findAllRolePermissions() {
        return existing_role_permission;
    }

    public List<Task> findAllTasks() {
        return existing_task;
    }

    public List<Invitation> findAllInvitations() {
        return existing_invitation;
    }


    public Role findRoleByName(@NotNull String name) {
        Role myRole = null;
        Optional<Role> roleOptional = existing_roles.stream().filter(role1 -> role1.getName().equalsIgnoreCase(name)).findFirst();
        if (roleOptional.isPresent()) {
            myRole = roleOptional.get();
        }
        return myRole;
    }

    public Role findRoleById(UUID id) {
        Role myRole = null;
        Optional<Role> roleOptional = existing_roles.stream().filter(role1 -> role1.getId().toString().equalsIgnoreCase(id.toString())).findFirst();
        if (roleOptional.isPresent()) {
            myRole = roleOptional.get();
        }
        return myRole;
    }

    public Permission findPermissionById(UUID id) {
        Permission permission = null;
        Optional<Permission> permissionOptional = existing_permission.stream().filter(p -> p.getId().toString().equalsIgnoreCase(id.toString())).findFirst();
        if (permissionOptional.isPresent()) {
            permission = permissionOptional.get();
        }
        return permission;
    }

    public RolePermission findRolePermissionById(UUID id) {
        RolePermission myRolePermission = null;
        Optional<RolePermission> roleOptional = existing_role_permission.stream().filter(rolePermission -> rolePermission.getId().toString().equalsIgnoreCase(id.toString())).findFirst();
        if (roleOptional.isPresent()) {
            myRolePermission = roleOptional.get();
        }
        return myRolePermission;
    }

    public Task findTaskById(UUID id) {
        Task myTask = null;
        Optional<Task> taskOptional = existing_task.stream().filter(t -> t.getId().toString().equalsIgnoreCase(id.toString())).findFirst();
        if (taskOptional.isPresent()) {
            myTask = taskOptional.get();
        }
        return myTask;
    }

    public Permission findPermissionByName(@NotNull String name) {
        Permission permission = null;
        Optional<Permission> permissionOptional = existing_permission.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst();
        if (permissionOptional.isPresent()) {
            permission = permissionOptional.get();
        }
        return permission;
    }


    public RolePermission assignPermission(Role roleById, Permission permissionById) {
        RolePermission myRolePermission = new RolePermission(UUID.randomUUID(), roleById, permissionById);
        existing_role_permission.add(myRolePermission);
        return myRolePermission;
    }

    public RolePermission isAlreadyAssignedPermission(@NotNull UUID roleId, @NotNull UUID permissionId) {
        RolePermission myRolePermission = null;
        Optional<RolePermission> roleOptional = existing_role_permission.stream().filter(rolePermission -> (rolePermission.getRole().getId().toString().equalsIgnoreCase(roleId.toString()) && rolePermission.getPermission().getId().toString().equalsIgnoreCase(permissionId.toString()))).findFirst();
        if (roleOptional.isPresent()) {
            myRolePermission = roleOptional.get();
        }
        return myRolePermission;
    }

    public RolePermission deleteRolePermission(UUID rolePermissionId) {
        RolePermission myRolePermission = null;
        Optional<RolePermission> rolePermission = existing_role_permission.stream()
                .filter(p -> p.getId().toString().equalsIgnoreCase(rolePermissionId.toString()))
                .findFirst();
        if (rolePermission.isPresent()) {
            myRolePermission = rolePermission.get();
            existing_role_permission.remove(myRolePermission);
        } else {
            System.out.println("Role-Permission with ID " + rolePermissionId + " not found in existing Permission.");
        }
        return myRolePermission;
    }

    public List<RolePermission> finaAllRolesByPermissionId(UUID permissionId) {
        return existing_role_permission.stream()
                .filter(p -> p.getPermission().getId().toString().equalsIgnoreCase(permissionId.toString())).toList();
    }

    public List<RolePermission> findAllPermissionByRoleId(UUID roleId) {
        return existing_role_permission.stream()
                .filter(p -> p.getRole().getId().toString().equalsIgnoreCase(roleId.toString())).toList();
    }

    public Task updateTask(UUID taskId, TaskRequestDTO taskRequestDTO, User assignedUser) {
        Task task = null;
        Optional<Task> taskResult = existing_task.stream()
                .filter(p -> p.getId().toString().equalsIgnoreCase(taskId.toString()))
                .findFirst();

        if (taskResult.isPresent()) {
            task = taskResult.get();
            existing_task.remove(task);
            task.setName(taskRequestDTO.getName());
            task.setDescription(taskRequestDTO.getDescription());
            task.setResolvedDate(taskRequestDTO.getResolvedDate());
            task.setDueDate(taskRequestDTO.getDueDate());
            task.setAttachments(taskRequestDTO.getAttachments());
            task.setPriority(taskRequestDTO.getPriority());
            task.setProjectId(taskRequestDTO.getProjectId());
            task.setAssignedUser(assignedUser);
            existing_task.add(task);
        } else {
            System.out.println("Task with ID " + taskId + " not found in existing tasks.");
        }
        return task;
    }


    public Task deleteTask(UUID taskId) {
        Task myTask = null;
        Optional<Task> taskOptional = existing_task.stream()
                .filter(t -> t.getId().toString().equalsIgnoreCase(taskId.toString()))
                .findFirst();
        if (taskOptional.isPresent()) {
            myTask = taskOptional.get();
            existing_task.remove(myTask);
        } else {
            System.out.println("Task with ID " + taskId.toString() + " not found in existing tasks.");
        }
        return myTask;
    }

    public List<Task> getTasksByAssignedUserId(UUID userId) {
        return existing_task.stream()
                .filter(p -> p.getAssignedUser().getId().toString().equalsIgnoreCase(userId.toString())).toList();
    }

    public List<Task> getTasksByCreatedUserId(UUID createdUserId) {
        return existing_task.stream()
                .filter(p -> p.getAssignedUser().getId().toString().equalsIgnoreCase(createdUserId.toString())).toList();
    }

    public Invitation createInvitation(InvitationRequestDTO invitationRequestDTO, Role role, Company company) {
        Invitation myInvitation = new Invitation(UUID.randomUUID(), invitationRequestDTO.isAccepted(), DateAndTime.getExpiredDateAndTime(), invitationRequestDTO.getEmail(), role, company);
        existing_invitation.add(myInvitation);
        return myInvitation;
    }

    public Invitation updateInvitation(UUID invitationId, InvitationRequestDTO invitationRequestDTO, Role role, Company company) {
        Invitation myInvitation = null;
        Optional<Invitation> invitationOptional = existing_invitation.stream()
                .filter(p -> p.getId().toString().equalsIgnoreCase(invitationId.toString()))
                .findFirst();

        if (invitationOptional.isPresent()) {
            myInvitation = invitationOptional.get();
            existing_invitation.remove(myInvitation);
            myInvitation.setAccepted(invitationRequestDTO.isAccepted());
            myInvitation.setEmail(invitationRequestDTO.getEmail());
            myInvitation.setRole(role);
            myInvitation.setCompany(company);
            myInvitation.setExpiredAt(invitationRequestDTO.getExpiredAt());
            existing_invitation.add(myInvitation);
        } else {
            System.out.println("Invitation with ID " + invitationId + " not found in existing Invitation record.");
        }
        return myInvitation;
    }

    public Invitation findInvitationById(UUID id) {
        Invitation myInvitation = null;
        Optional<Invitation> invitationOptional = existing_invitation.stream().filter(e -> e.getId().toString().equalsIgnoreCase(id.toString())).findFirst();
        if (invitationOptional.isPresent()) {
            myInvitation = invitationOptional.get();
        }
        return myInvitation;
    }
}
