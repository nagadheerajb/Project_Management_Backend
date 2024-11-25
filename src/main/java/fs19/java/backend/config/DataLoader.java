package fs19.java.backend.config;

import fs19.java.backend.domain.entity.*;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.domain.entity.enums.NotificationType;
import fs19.java.backend.domain.entity.enums.WorkspaceType;
import fs19.java.backend.infrastructure.JpaRepositories.*;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

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
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("aaaa@gmail.com");
        user.setPassword(passwordEncoder.encode("123456789"));
        user.setCreatedDate(DateAndTime.getDateAndTime());
        User saveUser = userJpaRepo.save(user);

        Company company = new Company();
        company.setName("ABC");
        company.setCreatedBy(user);
        company.setCreatedDate(DateAndTime.getDateAndTime());
        companyJpaRepo.save(company);

        Permission permission = new Permission();
        permission.setName("ROLE_ACCESS");
        Permission savePermission = permissionJpaRepo.save(permission);


        Role role = new Role();
        role.setName("ADMIN");
        role.setCreatedDate(DateAndTime.getDateAndTime());
        role.setCompany(companyJpaRepo.findAll().getFirst());
        Role saveRole = roleJpaRepo.save(role);

        Role role2 = new Role();
        role2.setName("ROLE-NAME");
        role2.setCreatedDate(DateAndTime.getDateAndTime());
        role2.setCompany(companyJpaRepo.findAll().getFirst());
        roleJpaRepo.save(role2);

        Workspace workspace = new Workspace();
        workspace.setName("Workspace1");
        workspace.setDescription("Workspace1");
        workspace.setType(WorkspaceType.PUBLIC);
        workspace.setCompanyId(companyJpaRepo.findAll().getFirst());
        workspace.setCreatedBy(userJpaRepo.findAll().getFirst());
        Workspace saveWorkspace = workspaceJpaRepo.save(workspace);

        Project project = new Project();
        project.setName("Project1");
        project.setStatus(true);
        project.setStartDate(DateAndTime.getDateAndTime());
        project.setCreatedByUser(userJpaRepo.findAll().getFirst());
        project.setCreatedDate(DateAndTime.getDateAndTime());
        project.setWorkspace(workspaceJpaRepo.findAll().getFirst());
        projectJpaRepo.save(project);

        WorkspaceUser workspaceUser = new WorkspaceUser();
        workspaceUser.setRole(saveRole);
        workspaceUser.setUser(saveUser);
        workspaceUser.setWorkspace(saveWorkspace);
        workspaceUserJpaRepo.save(workspaceUser);

        Permission permission1 = new Permission();
        permission1.setName("VIEW_PROJECT");
        permissionJpaRepo.save(permission1);

        Permission permission2 = new Permission();
        permission2.setName("EDIT_TASK");
        permissionJpaRepo.save(permission2);

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole(saveRole);
        rolePermission.setPermission(savePermission);
        rolePermissionJpaRepo.save(rolePermission);

        Task task = new Task();
        task.setName("Task 1");
        task.setDescription("Initial setup task");
        task.setCreatedDate(DateAndTime.getDateAndTime());
        task.setTaskStatus("TODO");
        task.setAttachments(Arrays.asList("doc1.pdf", "image1.png"));
        task.setProjectId(project.getId());
        task.setCreatedUser(user);
        task.setAssignedUser(user);
        task.setPriority("LOW_PRIORITY");
        taskJpaRepo.save(task);

        Comment comment = new Comment();
        comment.setTaskId(task);
        comment.setContent("This is a comment on the task.");
        comment.setCreatedDate(DateAndTime.getDateAndTime());
        comment.setCreatedBy(user);
        commentJpaRepo.save(comment);

        Invitation invitation = new Invitation();
        invitation.setAccepted(false);
        invitation.setExpiredAt(DateAndTime.getDateAndTime().plusDays(7));
        invitation.setEmail("invitee@example.com");
        invitation.setRole(role);
        invitation.setCompany(company);
        invitation.setCreatedBy(saveUser);
        invitationJpaRepo.save(invitation);

        Notification notification = new Notification();
        notification.setContent("You have a new task assigned.");
        notification.setNotifyType(NotificationType.PROJECT_UPDATED);
        notification.setCreatedDate(DateAndTime.getDateAndTime());
        notification.setRead(false);
        notification.setProjectId(project);
        notification.setMentionedBy(user);
        notification.setMentionedTo(user);
        notificationJpaRepo.save(notification);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setEntityType(EntityType.TASK);
        activityLog.setEntityId(task.getId());
        activityLog.setAction(ActionType.CREATED);
        activityLog.setCreatedDate(DateAndTime.getDateAndTime());
        activityLog.setUserId(user);
        activityLogJpaRepo.save(activityLog);
    }
}