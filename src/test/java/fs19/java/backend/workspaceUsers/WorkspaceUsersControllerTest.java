package fs19.java.backend.workspaceUsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import fs19.java.backend.application.dto.workspace_users.WorkspaceUsersRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WorkspaceUsersControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private WorkspaceUsersRequestDTO workspaceUsersRequestDTO;

  @BeforeEach
  void setUp() {
    workspaceUsersRequestDTO = WorkspaceUsersRequestDTO.builder()
        .roleId(UUID.randomUUID())
        .userId(UUID.randomUUID())
        .workspaceId(UUID.randomUUID())
        .build();
  }

  @Test
  void shouldCreateWorkspaceUser() throws Exception {
    mockMvc.perform(post("/api/v1/workspace-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(workspaceUsersRequestDTO)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.code", is(201)));
  }

  @Test
  void shouldGetAllWorkspaceUsers() throws Exception {
    mockMvc.perform(get("/api/v1/workspace-users"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)));
  }

  @Test
  void shouldGetWorkspaceUserById() throws Exception {
    // Create a workspace user first
    String response = mockMvc.perform(post("/api/v1/workspace-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(workspaceUsersRequestDTO)))
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
  void shouldUpdateWorkspaceUser() throws Exception {
    // Create a workspace user first
    String response = mockMvc.perform(post("/api/v1/workspace-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(workspaceUsersRequestDTO)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    UUID workspaceUserId = UUID.fromString(JsonPath.parse(response).read("$.data.id"));

    WorkspaceUsersRequestDTO updatedWorkspaceUsersRequestDTO = WorkspaceUsersRequestDTO.builder()
        .roleId(UUID.randomUUID())
        .userId(UUID.randomUUID())
        .workspaceId(UUID.randomUUID())
        .build();

    mockMvc.perform(put("/api/v1/workspace-users/{id}", workspaceUserId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedWorkspaceUsersRequestDTO)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)));
  }

  @Test
  void shouldDeleteWorkspaceUser() throws Exception {
    // Create a workspace user first
    String response = mockMvc.perform(post("/api/v1/workspace-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(workspaceUsersRequestDTO)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    UUID workspaceUserId = UUID.fromString(JsonPath.parse(response).read("$.data.id"));

    mockMvc.perform(delete("/api/v1/workspace-users/{id}", workspaceUserId))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)));
  }
}
