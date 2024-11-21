package fs19.java.backend.notification;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.notification.NotificationDTO;
import fs19.java.backend.domain.abstraction.NotificationRepository;
import fs19.java.backend.domain.abstraction.UserRepository;
import fs19.java.backend.domain.entity.Notification;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.enums.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.ZonedDateTime;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    private Notification existingNotification;
    private User mentionedByUser;
    private User mentionedToUser;

    @BeforeEach
    public void setUp() {
        mentionedByUser = new User(UUID.randomUUID(), "Alice", "Smith", "alice.smith@example.com", "password", "123456789", null, "profile1.jpg");
        mentionedToUser = new User(UUID.randomUUID(), "Bob", "Johnson", "bob.johnson@example.com", "password", "987654321", null, "profile2.jpg");

        userRepository.saveUser(mentionedByUser);
        userRepository.saveUser(mentionedToUser);

        existingNotification = new Notification(UUID.randomUUID(), "Test Notification", NotificationType.PROJECT_CREATED, ZonedDateTime.now(), false, UUID.randomUUID(), mentionedByUser.getId(), mentionedToUser.getId());
        notificationRepository.save(existingNotification);
    }

    @Test
    public void testCreateNotification() throws Exception {
        NotificationDTO request = new NotificationDTO(null, "New Notification", NotificationType.PROJECT_INVITATION, null, false, UUID.randomUUID(), mentionedByUser.getId(), mentionedToUser.getId());

        mockMvc.perform(post("/api/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(201)))
                .andExpect(jsonPath("$.data.content", is("New Notification")));
    }

    @Test
    public void testUpdateNotification() throws Exception {
        NotificationDTO request = new NotificationDTO(existingNotification.getId(), "Updated Notification", NotificationType.PROJECT_UPDATED, null, false, UUID.randomUUID(), mentionedByUser.getId(), mentionedToUser.getId());

        mockMvc.perform(put("/api/v1/notifications/" + existingNotification.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.content", is("Updated Notification")));
    }

    @Test
    public void testGetNotificationById() throws Exception {
        mockMvc.perform(get("/api/v1/notifications/" + existingNotification.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.content", is(existingNotification.getContent())));
    }

    @Test
    public void testGetAllNotifications() throws Exception {
        mockMvc.perform(get("/api/v1/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testDeleteNotification() throws Exception {
        mockMvc.perform(delete("/api/v1/notifications/" + existingNotification.getId()))
                .andExpect(status().isNoContent());
    }

    private ResultActions performPostNotification(NotificationDTO request) throws Exception {
        return mockMvc.perform(post("/api/v1/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }
}