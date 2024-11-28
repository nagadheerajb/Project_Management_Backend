package fs19.java.backend.workspaceUsers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import fs19.java.backend.application.dto.workspace_user.WorkspaceUserRequestDTO;
import fs19.java.backend.domain.entity.ActivityLog;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.domain.entity.enums.WorkspaceType;
import fs19.java.backend.infrastructure.JpaRepositories.CompanyJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.RoleJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.WorkspaceJpaRepo;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Commit
class WorkspaceUserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserJpaRepo userRepository;

  @Autowired
  private WorkspaceJpaRepo workspaceRepository;

  @Autowired
  private RoleJpaRepo roleRepository;

  @Autowired
  private CompanyJpaRepo companyJpaRepo;

  private User user;
  private Workspace workspace;
  private Role role;
  private Role role2;
 private static int emailCounter = 1;

  private WorkspaceUserRequestDTO workspaceUserRequestDTO;

  @BeforeEach
  void setUp() {
    user = userRepository.findAll().stream().findFirst().orElseGet(() -> {
      User newUser = new User(UUID.randomUUID(), "user1", "Sony", "user1.sony@example.com",
          "password", "123456789", ZonedDateTime.now(), "profile.jpg",List.of());
      var activityLogList = getActivityLogs(newUser);
      newUser.setActivityLogs(activityLogList);
      return userRepository.save(newUser);

    });

    Company company = new Company(UUID.randomUUID(), "Test Company", ZonedDateTime.now(), user);
    company = companyJpaRepo.save(company);

    workspace = new Workspace();
    workspace.setId(UUID.randomUUID());
    workspace.setName("Test Workspace 1");
    workspace.setDescription("Description");
    workspace.setType(WorkspaceType.PUBLIC);
    workspace.setCreatedBy(user);
    workspace.setCompanyId(company);
    workspace = workspaceRepository.save(workspace);

    role = new Role();
    role.setId(UUID.randomUUID());
    role.setName("Developer");
    role.setCreatedDate(DateAndTime.getDateAndTime());
    role.setCompany(company);
    role = roleRepository.save(role);

    role2 = new Role();
    role2.setId(UUID.randomUUID());
    role2.setName("Role2");
    role2.setCreatedDate(DateAndTime.getDateAndTime());
    role2.setCompany(company);
    role2 = roleRepository.save(role2);

    workspaceUserRequestDTO = WorkspaceUserRequestDTO.builder()
        .id(UUID.randomUUID())
        .roleId(role.getId())
        .userId(user.getId())
        .workspaceId(workspace.getId())
        .build();


  }

  private static List<ActivityLog> getActivityLogs(User newUser) {
    return List.of(
        ActivityLog.builder()
            .entityType(EntityType.USER)
            .entityId(UUID.randomUUID())
            .action(ActionType.CREATED)
            .createdDate(ZonedDateTime.now())
            .userId(newUser)
            .build()
    );
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"TEST-USER"})
  void shouldCreateWorkspaceUser() throws Exception {
    mockMvc.perform(post("/api/v1/workspace-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(workspaceUserRequestDTO)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.code", is(201)));
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"TEST-USER"})
  void shouldGetAllWorkspaceUsers() throws Exception {
    mockMvc.perform(get("/api/v1/workspace-users"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)));
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"TEST-USER"})
  void shouldGetWorkspaceUserById() throws Exception {
    User user2 = new User(UUID.randomUUID(), "User2", "Pony", "user2"+ emailCounter++ +"@example.com",
        "password", "123456789", ZonedDateTime.now(), "profile.jpg", List.of());
    var activityLogList = getActivityLogs(user2);
    user2.setActivityLogs(activityLogList);
    user2 = userRepository.save(user2);

    WorkspaceUserRequestDTO workspaceUserRequestDTO2 = WorkspaceUserRequestDTO.builder()
        .id(UUID.randomUUID())
        .roleId(role.getId())
        .userId(user2.getId())
        .workspaceId(workspace.getId())
        .build();

    String response = performPostWorkspaceUser(workspaceUserRequestDTO2)
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    UUID workspaceUserId = UUID.fromString(JsonPath.parse(response).read("$.data.id"));

    mockMvc.perform(get("/api/v1/workspace-users/{id}", workspaceUserId))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)));
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"TEST-USER"})
  void shouldUpdateWorkspaceUser() throws Exception {
    User user3 = new User(UUID.randomUUID(), "User3", "lastname", "user3" + emailCounter++ + "@example.com", "password", "123456789", ZonedDateTime.now(), "profile.jpg",
        List.of());
    var activityLogList = getActivityLogs(user3);
    user3.setActivityLogs(activityLogList);
    user3 = userRepository.save(user3);

    WorkspaceUserRequestDTO workspaceUserRequestDTO3 = WorkspaceUserRequestDTO.builder()
        .id(UUID.randomUUID())
        .roleId(role.getId())
        .userId(user3.getId())
        .workspaceId(workspace.getId())
        .build();

    String response = performPostWorkspaceUser(workspaceUserRequestDTO3)
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    UUID workspaceUserId = UUID.fromString(JsonPath.parse(response).read("$.data.id"));

    WorkspaceUserRequestDTO updatedWorkspaceUserRequestDTO = WorkspaceUserRequestDTO.builder()
        .roleId(role2.getId())
        .userId(user.getId())
        .workspaceId(workspace.getId())
        .build();

    mockMvc.perform(put("/api/v1/workspace-users/{id}", workspaceUserId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedWorkspaceUserRequestDTO)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)));
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"TEST-USER"})
  void shouldDeleteWorkspaceUser() throws Exception {
    User user4 = new User(UUID.randomUUID(), "User4", "Tony", "user42" + emailCounter++ + "@example.com", "password", "123456789", ZonedDateTime.now(), "profile.jpg",
        List.of());
    var activityLogList = getActivityLogs(user4);
    user4.setActivityLogs(activityLogList);
    user4 = userRepository.save(user4);

    WorkspaceUserRequestDTO workspaceUserRequestDTO4 = WorkspaceUserRequestDTO.builder()
        .id(UUID.randomUUID())
        .roleId(role2.getId())
        .userId(user4.getId())
        .workspaceId(workspace.getId())
        .build();

    String response = performPostWorkspaceUser(workspaceUserRequestDTO4)
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    UUID workspaceUserId = UUID.fromString(JsonPath.parse(response).read("$.data.id"));

    mockMvc.perform(delete("/api/v1/workspace-users/{id}", workspaceUserId))
        .andExpect(jsonPath("$.code", is(204)));
  }

  private ResultActions performPostWorkspaceUser(WorkspaceUserRequestDTO workspaceUserRequestDTO) throws Exception {
    return mockMvc.perform(post("/api/v1/workspace-users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(workspaceUserRequestDTO)));
  }
}