package fs19.java.backend.invitation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.invitation.InvitationRequestDTO;
import fs19.java.backend.infrastructure.JpaRepositories.RoleJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.WorkspaceJpaRepo;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
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
class InvitationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static UUID testInvitationId;
    private static final String BASE_URL = "/api/v1/invitations";

    @Autowired
    private RoleJpaRepo roleJpaRepo;

    @Autowired
    private WorkspaceJpaRepo workspaceJpaRepo;

    @Autowired
    private UserJpaRepo userJpaRepo;


    @BeforeEach
    void printAuthorities() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User Authorities: " + auth.getAuthorities());
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @Order(1)
    @DisplayName("Test Create Invitation")
    void testCreateInvitation() throws Exception {
        InvitationRequestDTO request = new InvitationRequestDTO();
        request.setExpiredAt(DateAndTime.getExpiredDateAndTime());
        request.setAccepted(false);
        request.setEmail("abc@gmail.com");
        request.setRoleId(roleJpaRepo.findAll().getFirst().getId());
        request.setWorkspaceId(workspaceJpaRepo.findAll().getFirst().getId());
        request.setCreated_user(userJpaRepo.findAll().getFirst().getId());

        String responseContent = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.email").value("abc@gmail.com"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        saveIdForExecuteTest(responseContent);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @Order(2)
    @DisplayName("Test Get Invitation by ID")
    void testGetInvitationById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testInvitationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(testInvitationId.toString()))
                .andExpect(jsonPath("$.data.email").exists());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @Order(3)
    @DisplayName("Test Update Invitation")
    void testUpdateInvitation() throws Exception {
        InvitationRequestDTO updateRequest = new InvitationRequestDTO();
        updateRequest.setExpiredAt(DateAndTime.getExpiredDateAndTime());
        updateRequest.setAccepted(true);
        updateRequest.setEmail("abc@gmail.com");
        updateRequest.setRoleId(roleJpaRepo.findAll().getFirst().getId());
        updateRequest.setWorkspaceId(workspaceJpaRepo.findAll().getFirst().getId());
        updateRequest.setCreated_user(userJpaRepo.findAll().getFirst().getId());

        mockMvc.perform(put(BASE_URL + "/" + testInvitationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accepted").value(true))
                .andExpect(jsonPath("$.data.email").value("abc@gmail.com"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @Order(4)
    @DisplayName("Test Get All Invitations")
    void testGetAllInvitations() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @Order(5)
    @DisplayName("Test Delete Invitation by ID")
    void testDeleteInvitationById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + testInvitationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(testInvitationId.toString()));
    }

    /**
     * Save the ID from the response for subsequent tests
     *
     * @param responseContent String
     * @throws JsonProcessingException
     */
    private void saveIdForExecuteTest(String responseContent) throws JsonProcessingException {
        GlobalResponse response = objectMapper.readValue(responseContent, GlobalResponse.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getData();
        testInvitationId = UUID.fromString((String) map.get("id"));
    }
}
