package fs19.java.backend.notification;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.notification.NotificationDTO;
import fs19.java.backend.domain.entity.Project;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.enums.NotificationType;
import fs19.java.backend.infrastructure.JpaRepositories.NotificationJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.ProjectJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Commit
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificationJpaRepo notificationRepository;

    @Autowired
    private ProjectJpaRepo projectRepository;

    @Autowired
    private UserJpaRepo userRepository;

    private static UUID testNotificationId;
    private static UUID testProjectId;
    private static UUID testMentionedById;
    private static UUID testMentionedToId;

    private static final String BASE_URL = "/api/v1/notifications";

    @BeforeEach
    public void setUp() {
        // Retrieve or create users
        User mentionedByUser = userRepository.findAll().get(0);
        testMentionedById = mentionedByUser.getId();
        User mentionedToUser = userRepository.findAll().get(0);
        testMentionedToId = mentionedToUser.getId();
        Project existingProject = projectRepository.findAll().get(0);
        testProjectId = existingProject.getId();
    }

    @Test
    @Order(1)
    @DisplayName("Test Create Notification")
    public void testCreateNotification() throws Exception {
        NotificationDTO request = new NotificationDTO();
        request.setContent("New Notification");
        request.setNotifyType(NotificationType.PROJECT_INVITATION);
        request.setProjectId(testProjectId);
        request.setMentionedBy(testMentionedById);
        request.setMentionedTo(testMentionedToId);

        String responseContent = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(201)))
                .andExpect(jsonPath("$.data.content", is("New Notification")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        saveIdForExecuteTest(responseContent);
    }

    @Test
    @Order(2)
    @DisplayName("Test Get Notification by ID")
    public void testGetNotificationById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testNotificationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.content").value("New Notification"));
    }

    @Test
    @Order(3)
    @DisplayName("Test Update Notification")
    public void testUpdateNotification() throws Exception {
        NotificationDTO updateRequest = new NotificationDTO();
        updateRequest.setContent("Updated Notification");
        updateRequest.setNotifyType(NotificationType.PROJECT_CREATED);
        updateRequest.setProjectId(testProjectId);
        updateRequest.setMentionedBy(testMentionedById);
        updateRequest.setMentionedTo(testMentionedToId);
        updateRequest.setRead(false);

        mockMvc.perform(put(BASE_URL + "/" + testNotificationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.content").value("Updated Notification"));
    }

    @Test
    @Order(4)
    @DisplayName("Test Get All Notifications")
    public void testGetAllNotifications() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].content").value("You have a new task assigned."));
    }

    @Test
    @Order(5)
    @DisplayName("Test Delete Notification by ID")
    public void testDeleteNotification() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + testNotificationId))
                .andExpect(status().isNoContent());
    }

    private void saveIdForExecuteTest(String responseContent) throws JsonProcessingException {
        GlobalResponse<NotificationDTO> response = objectMapper.readValue(responseContent, GlobalResponse.class);
        Object data = response.getData();
        if (data instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) data;
            Object id = map.get("id");
            if (id != null) {
                testNotificationId = UUID.fromString((String) id);
            }
        }
    }
}
