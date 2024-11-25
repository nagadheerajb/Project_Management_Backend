package fs19.java.backend.role;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.infrastructure.JpaRepositories.PermissionJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.RoleJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
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
class RolePermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static UUID testRolePermissionId;
    private static final String BASE_URL = "/app/v1/rolePermissions";


    @Autowired
    private RoleJpaRepo roleJpaRepo;

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Autowired
    private PermissionJpaRepo permissionJpaRepo;

    @Test
    @Order(1)
    @DisplayName("Test Create Role-Permission")
    void testCreateRolePermission() throws Exception {
        RolePermissionRequestDTO request = new RolePermissionRequestDTO();
        request.setRoleId(roleJpaRepo.findAll().getFirst().getId());
        request.setPermissionId(permissionJpaRepo.findAll().getFirst().getId());
        request.setCreated_user(userJpaRepo.findAll().getFirst().getId());

        String responseContent = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.roleId").exists())
                .andExpect(jsonPath("$.data.permissionId").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        saveIdForExecuteTest(responseContent);
    }

    @Test
    @Order(2)
    @DisplayName("Test Get Role-Permission by ID")
    void testGetRolePermissionById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testRolePermissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.roleId").exists())
                .andExpect(jsonPath("$.data.permissionId").exists());
    }

    @Test
    @Order(4)
    @DisplayName("Test Update Role-Permission")
    void testUpdateRolePermission() throws Exception {
        RolePermissionRequestDTO updateRequest = new RolePermissionRequestDTO();
        updateRequest.setRoleId(roleJpaRepo.findAll().getLast().getId());
        updateRequest.setPermissionId(permissionJpaRepo.findAll().getLast().getId());
        updateRequest.setCreated_user(userJpaRepo.findAll().getFirst().getId());

        mockMvc.perform(put(BASE_URL + "/" + testRolePermissionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.roleId").exists())
                .andExpect(jsonPath("$.data.permissionId").exists());
    }

    @Test
    @Order(5)
    @DisplayName("Test Get All Role-Permissions")
    void testGetAllRolePermissions() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(6)
    @DisplayName("Test Delete Role-Permission by ID")
    void testDeleteRolePermissionById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + testRolePermissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.roleId").exists());
    }

    @Test
    @Order(3)
    @DisplayName("Test Get Role-Permissions by RoleId and PermissionId")
    void testGetExistingRecord() throws Exception {
        UUID roleId = roleJpaRepo.findAll().getFirst().getId();
        UUID permissionId = permissionJpaRepo.findAll().getFirst().getId();

        mockMvc.perform(get(BASE_URL + "/" + roleId + "/" + permissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.roleId").value(roleId.toString()))
                .andExpect(jsonPath("$.data.permissionId").value(permissionId.toString()));
    }

    @Test
    @Order(7)
    @DisplayName("Test Get Role-Permissions by PermissionId")
    void testGetRolePermissionsByPermissionId() throws Exception {
        UUID permissionId = UUID.randomUUID();

        mockMvc.perform(get(BASE_URL + "/findAllRoles/" + permissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(8)
    @DisplayName("Test Get Role-Permissions by RoleId")
    void testGetRolePermissionsByRoleId() throws Exception {
        UUID roleId = UUID.randomUUID();

        mockMvc.perform(get(BASE_URL + "/findAllPermissions/" + roleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    /**
     * Save the ID from the response for subsequent tests
     *
     * @param responseContent String
     * @throws JsonProcessingException
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
