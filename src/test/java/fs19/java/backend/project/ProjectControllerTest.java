package fs19.java.backend.project;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import fs19.java.backend.application.dto.project.ProjectCreateDTO;
import fs19.java.backend.application.dto.project.ProjectUpdateDTO;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private ProjectCreateDTO projectCreateDto;
  private ProjectUpdateDTO projectUpdateDto;

  @BeforeEach
  public void setUp() {
    projectCreateDto = ProjectCreateDTO.builder()
        .name("Project 1")
        .description("Description")
        .startDate(ZonedDateTime.now().plusDays(1))
        .endDate(ZonedDateTime.parse("2025-01-16T12:34:56Z"))
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
    // Act and Assert
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(projectCreateDto)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.code", is(201)))
        .andExpect(jsonPath("$.data.name", is("Project 1")));
  }

  @Test
  void shouldGetAllProjects() throws Exception {
    // Act and Assert
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/projects"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void shouldGetProjectById() throws Exception {
    // Arrange
    String response = performPostProject(projectCreateDto)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.name", is("Project 1")))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getContentAsString();

    String projectId = JsonPath.parse(response).read("$.data.id");

    // Act and Assert
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/projects/{id}", projectId))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.name", is("Project 1")));

    // Check data array length
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/projects"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").isArray());
  }

  @Test
  void shouldUpdateProject() throws Exception {
    // Arrange
    String response = performPostProject(projectCreateDto)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.name", is("Project 1")))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getContentAsString();

    String projectId = JsonPath.parse(response).read("$.data.id");

    // Act and Assert
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/projects/{id}", projectId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(projectUpdateDto)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.description", is("New Description")));
  }

  @Test
  void shouldDeleteProject() throws Exception {
    // Arrange
    String response = performPostProject(projectCreateDto)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.name", is("Project 1")))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getContentAsString();

    String projectId = JsonPath.parse(response).read("$.data.id");

    // Act and Assert
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/projects/{id}", projectId))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  void shouldDeleteProjectReturnProjectNotFound() throws Exception {
    // Act and Assert
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

