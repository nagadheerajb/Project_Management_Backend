package fs19.java.backend.activitylog;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.activitylog.ActivityLogDTO;
import fs19.java.backend.domain.abstraction.ActivityLogRepository;
import fs19.java.backend.domain.abstraction.UserRepository;
import fs19.java.backend.domain.entity.ActivityLog;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
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
public class ActivityLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private UserRepository userRepository;

    private ActivityLog existingActivityLog;
    private User existingUser;

    @BeforeEach
    public void setUp() {
        existingUser = new User(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", "password", "123456789", null, "profile.jpg");
        userRepository.saveUser(existingUser);

        existingActivityLog = new ActivityLog(UUID.randomUUID(), EntityType.PROJECT, UUID.randomUUID(), ActionType.CREATED, ZonedDateTime.now(), existingUser.getId());
        activityLogRepository.save(existingActivityLog);
    }

    @Test
    public void testCreateActivityLog() throws Exception {
        ActivityLogDTO request = new ActivityLogDTO(null, EntityType.TASK, UUID.randomUUID(), ActionType.CREATED, null, existingUser.getId());

        mockMvc.perform(post("/api/v1/activity-logs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(201)))
                .andExpect(jsonPath("$.data.entityType", is("TASK")));
    }

    @Test
    public void testUpdateActivityLog() throws Exception {
        ActivityLogDTO request = new ActivityLogDTO(existingActivityLog.getId(), EntityType.COMMENT, existingActivityLog.getEntityId(), ActionType.UPDATED, null, existingUser.getId());

        mockMvc.perform(put("/api/v1/activity-logs/" + existingActivityLog.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.entityType", is("COMMENT")));
    }

    @Test
    public void testGetActivityLogById() throws Exception {
        mockMvc.perform(get("/api/v1/activity-logs/" + existingActivityLog.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.entityType", is(existingActivityLog.getEntityType().name())));
    }

    @Test
    public void testGetAllActivityLogs() throws Exception {
        mockMvc.perform(get("/api/v1/activity-logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testDeleteActivityLog() throws Exception {
        mockMvc.perform(delete("/api/v1/activity-logs/" + existingActivityLog.getId()))
                .andExpect(status().isNoContent());
    }

    private ResultActions performPostActivityLog(ActivityLogDTO request) throws Exception {
        return mockMvc.perform(post("/api/v1/activity-logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }
}
