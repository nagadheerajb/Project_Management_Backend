package fs19.java.backend.config;

import fs19.java.backend.application.InvitationServiceImpl;
import fs19.java.backend.domain.entity.*;
import fs19.java.backend.domain.entity.enums.*;
import fs19.java.backend.infrastructure.JpaRepositories.*;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {

    public static String ADMIN_USER_NAME = "ADMIN";

    private final CompanyJpaRepo companyJpaRepo;
    private final RoleJpaRepo roleJpaRepo;
    private final PermissionJpaRepo permissionJpaRepo;
    private final UserJpaRepo userJpaRepo;
    private final ProjectJpaRepo projectJpaRepo;
    private final WorkspaceJpaRepo workspaceJpaRepo;
    private final WorkspaceUserJpaRepo workspaceUserJpaRepo;
    private final TaskJpaRepo taskJpaRepo;
    private final CommentJpaRepo commentJpaRepo;
    private final InvitationJpaRepo invitationJpaRepo;
    private final NotificationJpaRepo notificationJpaRepo;
    private final ActivityLogJpaRepo activityLogJpaRepo;
    private final RolePermissionJpaRepo rolePermissionJpaRepo;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public DataLoader(
            CompanyJpaRepo companyJpaRepo,
            RoleJpaRepo roleJpaRepo,
            PermissionJpaRepo permissionJpaRepo,
            UserJpaRepo userJpaRepo,
            ProjectJpaRepo projectJpaRepo,
            WorkspaceJpaRepo workspaceJpaRepo,
            WorkspaceUserJpaRepo workspaceUserJpaRepo, TaskJpaRepo taskJpaRepo, CommentJpaRepo commentJpaRepo, InvitationJpaRepo invitationJpaRepo, NotificationJpaRepo notificationJpaRepo, ActivityLogJpaRepo activityLogJpaRepo, RolePermissionJpaRepo rolePermissionJpaRepo) {

        this.companyJpaRepo = companyJpaRepo;
        this.roleJpaRepo = roleJpaRepo;
        this.permissionJpaRepo = permissionJpaRepo;
        this.userJpaRepo = userJpaRepo;
        this.projectJpaRepo = projectJpaRepo;
        this.workspaceJpaRepo = workspaceJpaRepo;
        this.workspaceUserJpaRepo = workspaceUserJpaRepo;
        this.taskJpaRepo = taskJpaRepo;
        this.commentJpaRepo = commentJpaRepo;
        this.invitationJpaRepo = invitationJpaRepo;
        this.notificationJpaRepo = notificationJpaRepo;
        this.activityLogJpaRepo = activityLogJpaRepo;
        this.rolePermissionJpaRepo = rolePermissionJpaRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> byEmail = userJpaRepo.findByEmail("admin@gmail.com");
        if (byEmail.isEmpty()) {
            // Step 1: Create a default admin user
            User user = new User();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setEmail("admin@gmail.com");
            user.setPhone("1234567890");
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setCreatedDate(DateAndTime.getDateAndTime());
            User saveUser = userJpaRepo.save(user);

            User user2 = new User();
            user2.setFirstName("Test");
            user2.setLastName("Test");
            user2.setEmail("test@gmail.com");
            user2.setPhone("1234567890");
            user2.setPassword(passwordEncoder.encode("123456789"));
            user2.setCreatedDate(DateAndTime.getDateAndTime());
            User saveUser2 = userJpaRepo.save(user2);


            // Step 2: Create a default company
            Company company = new Company();
            company.setName("Company-A");
            company.setCreatedBy(user);
            company.setCreatedDate(DateAndTime.getDateAndTime());
            Company saveCompany = companyJpaRepo.save(company);

            // Step 3: Create roles (Admin, Member, etc.)
            Role adminRole = new Role();
            adminRole.setName(ADMIN_USER_NAME);
            adminRole.setCreatedDate(DateAndTime.getDateAndTime());
            adminRole.setCompany(saveCompany);
            Role saveAdminRole = roleJpaRepo.save(adminRole);

            Role memberRole = new Role();
            memberRole.setName("MEMBER");
            memberRole.setCreatedDate(DateAndTime.getDateAndTime());
            memberRole.setCompany(saveCompany);
            Role saveMemberRole = roleJpaRepo.save(memberRole);

            // Step 4: Create workspaces
            Workspace workspace = new Workspace();
            workspace.setName("Workspace1");
            workspace.setDescription("Workspace1");
            workspace.setType(WorkspaceType.PUBLIC);
            workspace.setCompanyId(saveCompany);
            workspace.setCreatedDate(DateAndTime.getDateAndTime());
            workspace.setCreatedBy(user);
            Workspace saveWorkspace = workspaceJpaRepo.save(workspace);


            Workspace workspace2 = new Workspace();
            workspace2.setName("Workspace2");
            workspace2.setDescription("Workspace2");
            workspace2.setType(WorkspaceType.PUBLIC);
            workspace2.setCompanyId(company);
            workspace2.setCreatedDate(DateAndTime.getDateAndTime());
            workspace2.setCreatedBy(user);
            Workspace saveWorkspace2 = workspaceJpaRepo.save(workspace2);


            // Step 5: Assign users to workspaces
            WorkspaceUser adminWorkspaceUser = new WorkspaceUser();
            adminWorkspaceUser.setRole(saveAdminRole);
            adminWorkspaceUser.setUser(saveUser);
            adminWorkspaceUser.setWorkspace(saveWorkspace);
            workspaceUserJpaRepo.save(adminWorkspaceUser);

            WorkspaceUser memberWorkspaceUser = new WorkspaceUser();
            memberWorkspaceUser.setRole(saveMemberRole);
            memberWorkspaceUser.setUser(saveUser);
            memberWorkspaceUser.setWorkspace(saveWorkspace2);
            workspaceUserJpaRepo.save(memberWorkspaceUser);

            // Step 6: Create CRUD permissions for all entities
            String[] entities = {
                    "activity-log", "comments", "companies", "invitations",
                    "notifications", "permissions", "projects", "roles",
                    "rolePermissions", "tasks", "users", "workspaces", "workspace-users"
            };

            for (String entity : entities) {
                String baseUrl = "/api/v1/" + entity + "/**"; // E.g., "/api/v1/users"
                assignCrudPermissionsForEntity(entity, baseUrl, saveAdminRole);
            }

            // Step 7: Additional setup for Projects, Tasks, etc.
            Project project = new Project();
            project.setName("Project1");
            project.setStatus(true);
            project.setStartDate(DateAndTime.getDateAndTime());
            project.setCreatedByUser(user);
            project.setCreatedDate(DateAndTime.getDateAndTime());
            project.setWorkspace(saveWorkspace);
            Project saveProject = projectJpaRepo.save(project);

            Task task = new Task();
            task.setName("Task 1");
            task.setDescription("Initial setup task");
            task.setCreatedDate(DateAndTime.getDateAndTime());
            task.setTaskStatus("TODO");
            task.setAttachments(Arrays.asList("doc1.pdf", "image1.png"));
            task.setProject(saveProject);
            task.setCreatedUser(user);
            task.setAssignedUser(user);
            task.setPriority("LOW_PRIORITY");
            Task saveTask = taskJpaRepo.save(task);


            Comment comment = new Comment();
            comment.setTaskId(task);
            comment.setContent("This is a comment on the task.");
            comment.setCreatedDate(DateAndTime.getDateAndTime());
            comment.setCreatedBy(user);
            commentJpaRepo.save(comment);

            // Notifications, Invitations, etc.
            Notification notification = new Notification();
            notification.setContent("You have a new task assigned.");
            notification.setNotifyType(NotificationType.PROJECT_UPDATED);
            notification.setCreatedDate(DateAndTime.getDateAndTime());
            notification.setRead(false);
            notification.setProjectId(saveProject);
            notification.setMentionedBy(user);
            notification.setMentionedTo(user);
            notificationJpaRepo.save(notification);

            Invitation invitation = new Invitation();
            invitation.setAccepted(false);
            invitation.setExpiredAt(DateAndTime.getExpiredDateAndTime());
            invitation.setEmail("invitee@example.com");
            invitation.setRole(saveAdminRole);
            invitation.setWorkspace(saveWorkspace);
            invitation.setCreatedBy(saveUser);
            invitation.setUrl(String.format(InvitationServiceImpl.urlBody, "invitee@example.com", saveAdminRole.getId(), saveWorkspace.getId()));
            invitationJpaRepo.save(invitation);
        }
    }

    // Utility method to create CRUD permissions for an entity
    private void assignCrudPermissionsForEntity(String entity, String baseUrl, Role role) {
        for (PermissionType type : PermissionType.values()) {
            String action = type.name(); // GET, POST, DELETE, PUT
            Permission permission = new Permission();
            permission.setName(entity + "_" + action);
            permission.setUrl(baseUrl);
            permission.setPermissionType(type);
            Permission savedPermission = permissionJpaRepo.save(permission);

            RolePermission rolePermission = new RolePermission();
            rolePermission.setRole(role);
            rolePermission.setPermission(savedPermission);
            rolePermissionJpaRepo.save(rolePermission);
        }
    }

    private void createActivityLog(EntityType entityType, UUID entityId, ActionType action, String description, User user) {
        ActivityLog activityLog = ActivityLog.builder()
                .entityType(entityType)
                .entityId(entityId)
                .action(action)
                .createdDate(DateAndTime.getDateAndTime())
                .userId(user)
                .build();

        activityLogJpaRepo.save(activityLog);
    }


}