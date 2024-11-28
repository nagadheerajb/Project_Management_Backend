package fs19.java.backend.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.comment.CommentRequestDTO;
import fs19.java.backend.application.dto.comment.CommentResponseDTO;
import fs19.java.backend.application.dto.comment.CommentUpdateDTO;
import fs19.java.backend.infrastructure.JpaRepositories.CommentJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.TaskJpaRepo;
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
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentJpaRepo commentJpaRepo;

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Autowired
    private TaskJpaRepo taskJpaRepo;

    private static UUID testCommentId;
    private static UUID testUserId;
    private static UUID testTaskId;

    private static final String BASE_URL = "/api/v1/comments";

    @BeforeEach
    public void setUp() {
        testUserId = userJpaRepo.findAll().get(0).getId();
        testTaskId = taskJpaRepo.findAll().get(0).getId();
    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Create Comment")
    public void testCreateComment() throws Exception {
        CommentRequestDTO request = new CommentRequestDTO();
        request.setTaskId(testTaskId);
        request.setContent("This is a test comment");
        request.setCreatedBy(testUserId);

        String responseContent = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.content").value("This is a test comment"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        saveIdForExecuteTest(responseContent);
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get Comment by ID")
    public void testGetCommentById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testCommentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value("This is a test comment"));
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Update Comment")
    public void testUpdateComment() throws Exception {
        CommentUpdateDTO updateRequest = new CommentUpdateDTO();
        updateRequest.setContent("This is an updated comment");

        mockMvc.perform(put(BASE_URL + "/" + testCommentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value("This is an updated comment"));
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get All Comments")
    public void testGetAllComments() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].content").value("This is a comment on the task."));
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Delete Comment by ID")
    public void testDeleteCommentById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + testCommentId))
                .andExpect(status().isNoContent());
    }

    private void saveIdForExecuteTest(String responseContent) throws JsonProcessingException {
        GlobalResponse<CommentResponseDTO> response = objectMapper.readValue(responseContent, GlobalResponse.class);
        Object data = response.getData();
        if (data instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) data;
            Object id = map.get("id");
            if (id != null) {
                testCommentId = UUID.fromString((String) id);
            } else {
                throw new IllegalStateException("Comment ID not found in response");
            }
        } else {
            throw new IllegalStateException("Response data is not in expected format");
        }
    }
}