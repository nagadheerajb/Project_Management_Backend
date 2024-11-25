package fs19.java.backend.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.application.dto.task.TaskResponseDTO;
import fs19.java.backend.infrastructure.JpaRepositories.ProjectJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Commit
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserJpaRepo userJpaRepo;
    @Autowired
    private ProjectJpaRepo projectJpaRepo;

    private static UUID testTaskId;
    private static final String BASE_URL = "/app/v1/tasks";

    @Test
    @Order(1)
    @DisplayName("Test Create Task")
    void testCreateTask() throws Exception {
        TaskRequestDTO request = new TaskRequestDTO();
        request.setName("Test Task");
        request.setDescription("Task created for testing.");
        request.setCreatedDate(DateAndTime.getDateAndTime());
        request.setTaskStatus("IN_DEVELOPMENT");
        request.setPriority("HIGH_PRIORITY");
        request.setProjectId(projectJpaRepo.findAll().getFirst().getId());
        request.setCreatedUserId(userJpaRepo.findAll().getFirst().getId());
        request.setAssignedUserId(userJpaRepo.findAll().getFirst().getId());

        String responseContent = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Test Task"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        saveIdForExecuteTest(responseContent);
    }

    @Test
    @Order(2)
    @DisplayName("Test Get Task by ID")
    void testGetTaskById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testTaskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Test Task"));
    }

    @Test
    @Order(3)
    @DisplayName("Test Update Task")
    void testUpdateTask() throws Exception {
        TaskRequestDTO updateRequest = new TaskRequestDTO();
        updateRequest.setName("Updated Task");
        updateRequest.setDescription("Updated task description.");
        updateRequest.setTaskStatus("RELEASED");
        updateRequest.setPriority("HIGH_PRIORITY");
        updateRequest.setCreatedUserId(userJpaRepo.findAll().getFirst().getId());
        updateRequest.setProjectId(projectJpaRepo.findAll().getFirst().getId());
        updateRequest.setAssignedUserId(userJpaRepo.findAll().getFirst().getId());

        mockMvc.perform(put(BASE_URL + "/" + testTaskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Task"));
    }

    @Test
    @Order(4)
    @DisplayName("Test Get All Tasks")
    void testGetAllTasks() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(5)
    @DisplayName("Test Get Tasks by Assigned User ID")
    void testGetTasksByAssignedUserId() throws Exception {
        UUID assignedUserId = UUID.randomUUID();

        mockMvc.perform(get(BASE_URL + "/findByAssigned/" + assignedUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(6)
    @DisplayName("Test Get Tasks by Created User ID")
    void testGetTasksByCreatedUserId() throws Exception {
        UUID createdUserId = UUID.randomUUID();

        mockMvc.perform(get(BASE_URL + "/findByCreated/" + createdUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(7)
    @DisplayName("Test Delete Task by ID")
    void testDeleteTaskById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + testTaskId))
                .andExpect(status().isOk());
    }

    @Test
    @Order(8)
    @DisplayName("Test Cleanup - Task Does Not Exist")
    void testCleanUp() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testTaskId))
                .andExpect(status().isNotFound());
    }

    /**
     * Saves the ID from the response for subsequent tests.
     *
     * @param responseContent The response content from the create task endpoint.
     * @throws JsonProcessingException
     */
    private void saveIdForExecuteTest(String responseContent) throws JsonProcessingException {
        GlobalResponse<TaskResponseDTO> response = objectMapper.readValue(responseContent, GlobalResponse.class);
        Object data = response.getData();
        if (data instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) data;
            Object id = map.get("id");
            testTaskId = UUID.fromString((String) id);
        }
    }
}
