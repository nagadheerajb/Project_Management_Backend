package fs19.java.backend.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import fs19.java.backend.application.dto.project.ProjectCreateDTO;
import fs19.java.backend.application.dto.project.ProjectUpdateDTO;
import fs19.java.backend.domain.entity.ActivityLog;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.WorkspaceType;
import fs19.java.backend.infrastructure.JpaRepositories.CompanyJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.WorkspaceJpaRepo;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserJpaRepo userRepository;

  @Autowired
  private WorkspaceJpaRepo workspaceRepository;

  @Autowired
  private CompanyJpaRepo companyJpaRepo;

  private User user;
  private Workspace workspace;
  private ProjectCreateDTO projectCreateDto;
  private ProjectUpdateDTO projectUpdateDto;

  @BeforeEach
  public void setUp() {

    user = userRepository.findAll().stream().findFirst().orElseGet(() -> {
      var userId = UUID.randomUUID();
      User newUser = new User(userId, "user1", "Sony", "user1.sony@example.com",
          "password", "123456789", ZonedDateTime.now(), "profile.jpg", List.of());
      var activityLogList = List.of(
          ActivityLog.builder()
              .entityType(EntityType.USER)
              .entityId(UUID.randomUUID())
              .action(ActionType.CREATED)
              .createdDate(ZonedDateTime.now())
              .userId(newUser)
              .build()
      );
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

    projectCreateDto = ProjectCreateDTO.builder()
        .name("Project 1")
        .description("Description")
        .startDate(ZonedDateTime.now().plusDays(1))
        .endDate(ZonedDateTime.parse("2025-01-16T12:34:56Z"))
        .createdByUserId(user.getId())
        .workspaceId(workspace.getId())
        .status(false)
        .build();

    projectUpdateDto = ProjectUpdateDTO.builder()
        .description("New Description")
        .startDate(ZonedDateTime.now().plusDays(1))
        .endDate(ZonedDateTime.parse("2025-01-16T12:34:56Z"))
        .status(true)
        .build();
  }

  @Test
  void shouldCreateProject() throws Exception {
    performPostProject(projectCreateDto)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.code", is(201)))
        .andExpect(jsonPath("$.data.name", is("Project 1")));
  }

  @Test
  void shouldGetAllProjects() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/projects"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").isArray());
  }

  @Test
  void shouldGetProjectById() throws Exception {
    String response = performPostProject(projectCreateDto)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.name", is("Project 1")))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getContentAsString();

    String projectId = JsonPath.parse(response).read("$.data.id");

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/projects/{id}", projectId))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.name", is("Project 1")));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/projects"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").isArray());
  }

  @Test
  void shouldUpdateProject() throws Exception {
    String response = performPostProject(projectCreateDto)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.name", is("Project 1")))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getContentAsString();

    String projectId = JsonPath.parse(response).read("$.data.id");

    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/projects/{id}", projectId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(projectUpdateDto)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.description", is("New Description")));
  }

  @Test
  void shouldDeleteProject() throws Exception {
    String response = performPostProject(projectCreateDto)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.name", is("Project 1")))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getContentAsString();

    String projectId = JsonPath.parse(response).read("$.data.id");

    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/projects/{id}", projectId))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  void shouldDeleteProjectReturnProjectNotFound() throws Exception {
    UUID projectId = UUID.randomUUID();
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/projects/" + projectId))
        .andDo(print())
        .andExpect(status().is(204));
  }

  private ResultActions performPostProject(ProjectCreateDTO projectCreateDto) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/projects")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(projectCreateDto)));
  }
}