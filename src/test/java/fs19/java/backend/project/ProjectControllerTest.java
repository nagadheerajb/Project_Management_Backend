package fs19.java.backend.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.project.ProjectCreateDTO;
import fs19.java.backend.application.dto.project.ProjectUpdateDTO;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.domain.entity.enums.WorkspaceType;
import fs19.java.backend.infrastructure.JpaRepositories.CompanyJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.WorkspaceJpaRepo;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import java.util.List;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Commit
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private static UUID testProjectId;

  private static final String BASE_URL = "/api/v1/projects";

  @Autowired
  private UserJpaRepo userRepository;

  @Autowired
  private WorkspaceJpaRepo workspaceRepository;

  @Autowired
  private CompanyJpaRepo companyJpaRepo;

  private User user;
  private Workspace workspace;

  @BeforeEach
  void setUp() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    System.out.println("User Authorities: " + auth.getAuthorities());

    user = userRepository.findAll().stream().findFirst().orElseGet(() -> {
      var userId = UUID.randomUUID();
      User newUser = new User(userId, "user1", "Sony", "user1.sony@example.com",
          "password", "123456789", ZonedDateTime.now(), "profile.jpg", List.of());
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
  }

  @Test
  @WithMockUser(username = "admin@gmail.com", authorities = {"TEST-USER"})
  @Order(1)
  @DisplayName("Test Create Project")
  void testCreateProject() throws Exception {
    ProjectCreateDTO request = ProjectCreateDTO.builder()
        .name("Project 1")
        .description("Description")
        .startDate(ZonedDateTime.now().plusDays(1))
        .endDate(ZonedDateTime.parse("2025-01-16T12:34:56Z"))
        .createdByUserId(user.getId())
        .workspaceId(workspace.getId())
        .status(false)
        .build();

    String responseContent = mockMvc.perform(post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.name").value("Project 1"))
        .andReturn()
        .getResponse()
        .getContentAsString();

    saveIdForExecuteTest(responseContent);
  }

  @Test
  @Order(2)
  @WithMockUser(username = "admin", authorities = {"TEST-USER"})
  @DisplayName("Test Get Project by ID")
  void testGetProjectById() throws Exception {
    mockMvc.perform(get(BASE_URL + "/" + testProjectId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.name").value("Project 1"));
  }

  @Test
  @Order(3)
  @WithMockUser(username = "admin", authorities = {"TEST-USER"})
  @DisplayName("Test Update Project")
  void testUpdateProject() throws Exception {
    ProjectUpdateDTO updateRequest = ProjectUpdateDTO.builder()
        .description("New Description")
        .startDate(ZonedDateTime.now().plusDays(1))
        .endDate(ZonedDateTime.parse("2025-01-16T12:34:56Z"))
        .status(true)
        .build();

    mockMvc.perform(put(BASE_URL + "/" + testProjectId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.description").value("New Description"));
  }

  @Test
  @Order(4)
  @WithMockUser(username = "admin", authorities = {"TEST-USER"})
  @DisplayName("Test Get All Projects")
  void testGetAllProjects() throws Exception {
    mockMvc.perform(get(BASE_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").isArray());
  }

  @Test
  @Order(5)
  @WithMockUser(username = "admin", authorities = {"TEST-USER"})
  @DisplayName("Test Delete Project by ID")
  void testDeleteProjectById() throws Exception {
    mockMvc.perform(delete(BASE_URL + "/" + testProjectId))
        .andExpect(status().isNoContent());
  }

  private void saveIdForExecuteTest(String responseContent) throws Exception {
    var response = objectMapper.readValue(responseContent, GlobalResponse.class);
    var data = (LinkedHashMap<String, Object>) response.getData();
    testProjectId = UUID.fromString((String) data.get("id"));
  }
}