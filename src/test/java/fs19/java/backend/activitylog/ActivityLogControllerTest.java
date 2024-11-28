package fs19.java.backend.activitylog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.activitylog.ActivityLogDTO;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.infrastructure.JpaRepositories.ActivityLogJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ActivityLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityLogJpaRepo activityLogJpaRepo;

    @Autowired
    private UserJpaRepo userJpaRepo;

    private static UUID testActivityLogId;
    private static UUID testUserId;

    private static final String BASE_URL = "/api/v1/activity-logs";

    @BeforeEach
    void setUp() {
        // Retrieve or create users
        testUserId = userJpaRepo.findAll().get(0).getId(); // Assuming at least one user exists
    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Create Activity Log")
    void testCreateActivityLog() throws Exception {
        ActivityLogDTO request = new ActivityLogDTO();
        request.setEntityType(EntityType.PROJECT);
        request.setEntityId(UUID.randomUUID());
        request.setAction(ActionType.CREATED);
        request.setUserId(testUserId);

        String responseContent = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.entityType").value("PROJECT"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        saveIdForExecuteTest(responseContent);
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get Activity Log by ID")
    void testGetActivityLogById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testActivityLogId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.entityType").value("PROJECT"));
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Update Activity Log")
    void testUpdateActivityLog() throws Exception {
        ActivityLogDTO updateRequest = new ActivityLogDTO();
        updateRequest.setId(testActivityLogId); // Ensure the ID is set
        updateRequest.setEntityType(EntityType.TASK);
        updateRequest.setEntityId(UUID.randomUUID());
        updateRequest.setAction(ActionType.UPDATED);
        updateRequest.setUserId(testUserId);

        mockMvc.perform(put(BASE_URL + "/" + testActivityLogId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.entityType").value("TASK"));
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get All Activity Logs")
    void testGetAllActivityLogs() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].entityType").value("TASK"));
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Delete Activity Log by ID")
    void testDeleteActivityLogById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + testActivityLogId))
                .andExpect(status().isNoContent());
    }

    private void saveIdForExecuteTest(String responseContent) throws JsonProcessingException {
        GlobalResponse<ActivityLogDTO> response = objectMapper.readValue(responseContent, GlobalResponse.class);
        Object data = response.getData();
        if (data instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) data;
            Object id = map.get("id");
            if (id != null) {
                testActivityLogId = UUID.fromString((String) id);
            }
        }
    }
}
