package fs19.java.backend.permission;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.application.dto.permission.PermissionResponseDTO;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.domain.entity.enums.PermissionType;
import fs19.java.backend.infrastructure.JpaRepositories.PermissionJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
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
class PermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static UUID testPermissionId;

    private static final String BASE_URL = "/api/v1/permissions";

    @Autowired
    private UserJpaRepo userJpaRepo;
    @Autowired
    private PermissionJpaRepo permissionJpaRepo;

    @BeforeEach
    void printAuthorities() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User Authorities: " + auth.getAuthorities());
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @Order(1)
    @DisplayName("Test Create Permission")
    void testCreatePermission() throws Exception {
        PermissionRequestDTO request = new PermissionRequestDTO();
        request.setCreated_user(userJpaRepo.findAll().getFirst().getId());
        request.setName("TASK_VIEW_TEST");
        request.setPermissionType(PermissionType.GET);
        request.setPermissionUrl("app/v1/tasks-Test");

        String responseContent = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("TASK_VIEW_TEST"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        saveIdForExecuteTest(responseContent);
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get Permission by ID")
    void testGetPermissionById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testPermissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("TASK_VIEW_TEST"));
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Update Permission")
    void testUpdatePermission() throws Exception {
        PermissionRequestDTO updateRequest = new PermissionRequestDTO();
        updateRequest.setCreated_user(userJpaRepo.findAll().getFirst().getId());
        updateRequest.setName("ROLES_VIEW_TEST");
        updateRequest.setPermissionType(PermissionType.GET);
        updateRequest.setPermissionUrl("app/v1/roles-Test");

        mockMvc.perform(put(BASE_URL + "/" + testPermissionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("ROLES_VIEW_TEST"));
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get All Permissions")
    void testGetAllPermissions() throws Exception {
        Permission first = permissionJpaRepo.findAll().getFirst();
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value(first.getName()));
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Search Permission by Name")
    void testSearchPermissionByName() throws Exception {
        mockMvc.perform(get(BASE_URL + "/search/ROLES_VIEW_TEST"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("ROLES_VIEW_TEST"));
    }

    @Test
    @Order(6)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Delete Permission by ID")
    void testDeletePermissionById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + testPermissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("ROLES_VIEW_TEST"));
    }

    @Test
    @Order(7)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Clean Up Database")
    void testCleanUp() throws Exception {
        // Ensure the permission no longer exists
        mockMvc.perform(get(BASE_URL + "/" + testPermissionId))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Execute the test cases and save the ID for future use
     *
     * @param responseContent String
     * @throws JsonProcessingException
     */
    private void saveIdForExecuteTest(String responseContent) throws JsonProcessingException {
        GlobalResponse<PermissionResponseDTO> response = objectMapper.readValue(responseContent, GlobalResponse.class);
        Object data = response.getData();
        if (data instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) data;
            Object id = map.get("id");
            testPermissionId = UUID.fromString((String) id);
        }
    }
}
