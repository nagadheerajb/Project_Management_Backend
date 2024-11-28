package fs19.java.backend.role;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.enums.PermissionType;
import fs19.java.backend.infrastructure.JpaRepositories.PermissionJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.RoleJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RolePermissionControllerTest {

    private static UUID testRolePermissionId;
    private static final String BASE_URL = "/api/v1/rolePermissions";
    private static Permission savedPermission;
    private static Permission savedPermission2;
    private static UUID roleId;
    private static UUID userId;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleJpaRepo roleJpaRepo;

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Autowired
    private PermissionJpaRepo permissionJpaRepo;

    @BeforeAll
    static void setup(@Autowired PermissionJpaRepo permissionJpaRepo,
                      @Autowired RoleJpaRepo roleJpaRepo,
                      @Autowired UserJpaRepo userJpaRepo) {
        // Save test permissions
        Permission permission = new Permission();
        permission.setName("PERMISSION" + "_" + "TEST");
        permission.setUrl("test-url");
        permission.setPermissionType(PermissionType.GET);
        savedPermission = permissionJpaRepo.save(permission);

        Permission permission2 = new Permission();
        permission2.setName("TASK" + "_" + "TEST");
        permission2.setUrl("test-url");
        permission2.setPermissionType(PermissionType.GET);
        savedPermission2 = permissionJpaRepo.save(permission2);

        // Fetch role and user for test setup
        Role testRole = roleJpaRepo.findAll().stream().findFirst().orElseThrow(() -> new IllegalStateException("No roles found in database"));
        User testUser = userJpaRepo.findAll().stream().findFirst().orElseThrow(() -> new IllegalStateException("No users found in database"));

        roleId = testRole.getId();
        userId = testUser.getId();
    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Create Role-Permission")
    void testCreateRolePermission() throws Exception {
        RolePermissionRequestDTO request = new RolePermissionRequestDTO();
        request.setRoleId(roleId);
        request.setPermissionId(savedPermission.getId());
        request.setCreated_user(userId);

        String responseContent = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.roleId").value(roleId.toString()))
                .andExpect(jsonPath("$.data.permissionId").value(savedPermission.getId().toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        saveIdForExecuteTest(responseContent);
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get Role-Permission by ID")
    void testGetRolePermissionById() throws Exception {
        assertThat(testRolePermissionId).isNotNull();

        mockMvc.perform(get(BASE_URL + "/" + testRolePermissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.roleId").value(roleId.toString()))
                .andExpect(jsonPath("$.data.permissionId").value(savedPermission.getId().toString()));
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Update Role-Permission")
    void testUpdateRolePermission() throws Exception {
        RolePermissionRequestDTO updateRequest = new RolePermissionRequestDTO();
        updateRequest.setRoleId(roleId);
        updateRequest.setPermissionId(savedPermission2.getId());
        updateRequest.setCreated_user(userId);

        mockMvc.perform(put(BASE_URL + "/" + testRolePermissionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.roleId").exists())
                .andExpect(jsonPath("$.data.permissionId").exists());
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get All Role-Permissions")
    void testGetAllRolePermissions() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(6)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Delete Role-Permission by ID")
    void testDeleteRolePermissionById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + testRolePermissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.roleId").exists());
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get Role-Permissions by RoleId and PermissionId")
    void testGetExistingRecord() throws Exception {

        mockMvc.perform(get(BASE_URL + "/" + roleId + "/" + savedPermission.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.roleId").value(roleId.toString()))
                .andExpect(jsonPath("$.data.permissionId").value(savedPermission.getId().toString()));
    }

    @Test
    @Order(7)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get Role-Permissions by PermissionId")
    void testGetRolePermissionsByPermissionId() throws Exception {
        UUID permissionId = UUID.randomUUID();

        mockMvc.perform(get(BASE_URL + "/findAllRoles/" + permissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(8)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get Role-Permissions by RoleId")
    void testGetRolePermissionsByRoleId() throws Exception {
        UUID roleId = UUID.randomUUID();

        mockMvc.perform(get(BASE_URL + "/findAllPermissions/" + roleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @AfterAll
    static void cleanData(@Autowired PermissionJpaRepo permissionJpaRepo) {
        permissionJpaRepo.deleteById(savedPermission.getId());
        permissionJpaRepo.deleteById(savedPermission2.getId());
    }

    /**
     * Save the ID from the response for subsequent tests
     *
     * @param responseContent String
     * @throws JsonProcessingException if parsing fails
     */
    private void saveIdForExecuteTest(String responseContent) throws JsonProcessingException {
        GlobalResponse<RolePermissionResponseDTO> response = objectMapper.readValue(responseContent, GlobalResponse.class);
        Object data = response.getData();
        if (data instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) data;
            Object id = map.get("id");
            testRolePermissionId = UUID.fromString((String) id);
        }
    }
}
