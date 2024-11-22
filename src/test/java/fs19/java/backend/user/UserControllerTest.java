package fs19.java.backend.user;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import fs19.java.backend.application.dto.user.UserCreateDTO;
import fs19.java.backend.application.dto.user.UserReadDTO;
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
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private UserCreateDTO userCreateDto;
  private UserReadDTO userReadDto;

  @BeforeEach
  void setUp() {
    userCreateDto = UserCreateDTO.builder()
        .firstName("John")
        .lastName("Doe")
        .email("john.doe@example.com")
        .password("password123")
        .phone("1234566574")
        .profileImage("profile.jpg")
        .build();

    userReadDto = UserReadDTO.builder()
        .firstName("John")
        .lastName("Doe")
        .email("john.doe@example.com")
        .phone("1234566566")
        .profileImage("profile.jpg")
        .build();
  }

  @Test
  void shouldCreateUserOnPost() throws Exception {
    // Act and Assert
    performPostUser(userCreateDto)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.code", is(201)))
        .andExpect(jsonPath("$.data.firstName", is("John")));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)))
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data.size()", is(1)))
        .andExpect(jsonPath("$.data[0].firstName", is("John")))
        .andExpect(jsonPath("$.data[0].lastName", is("Doe")));
  }

  @Test
  void shouldGetAllUsersOnGet() throws Exception {
    performPostUser(userCreateDto).andExpect(status().isCreated());
    performPostUser(userCreateDto).andExpect(status().isCreated());

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)))
        .andExpect(jsonPath("$.data").isArray());
  }

  @Test
  void shouldGetUserByIdOnGet() throws Exception {
    String response = performPostUser(userCreateDto)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.firstName", is("John")))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getContentAsString();

    // Extract the ID from the response
    String userId = JsonPath.parse(response).read("$.data.id");

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{id}", userId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.firstName", is("John")));
  }

  @Test
  void shouldDeleteUserOnDelete() throws Exception {
    String response = performPostUser(userCreateDto)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.firstName", is("John")))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getContentAsString();

    // Extract the ID from the response
    String userId = JsonPath.parse(response).read("$.data.id");
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/{id}", userId))
        .andExpect(status().isNoContent());
  }

  @Test
  void shouldUpdateUserOnPut() throws Exception {
    // Create a user
    String response = mockMvc.perform(post("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreateDto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.firstName", is("John")))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getContentAsString();

    // Extract the ID from the response
    String userId = JsonPath.parse(response).read("$.data.id");

    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/{id}", userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userReadDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.phone", is(userReadDto.getPhone())));
  }

  private ResultActions performPostUser(UserCreateDTO userCreateDTO) throws Exception {
    return mockMvc.perform(post("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDTO)));
  }
}
