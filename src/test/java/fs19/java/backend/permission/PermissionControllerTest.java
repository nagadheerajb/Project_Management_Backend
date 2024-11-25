package fs19.java.backend.permission;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.application.dto.permission.PermissionResponseDTO;
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
class PermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static UUID testPermissionId;

    private static final String BASE_URL = "/app/v1/permissions";

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Test
    @Order(1)
    @DisplayName("Test Create Permission")
    void testCreatePermission() throws Exception {
        PermissionRequestDTO request = new PermissionRequestDTO();
        request.setCreated_user(userJpaRepo.findAll().getFirst().getId());
        request.setName("TEST_PERMISSION");

        String responseContent = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("TEST_PERMISSION"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        saveIdForExecuteTest(responseContent);
    }

    @Test
    @Order(2)
    @DisplayName("Test Get Permission by ID")
    void testGetPermissionById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testPermissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("TEST_PERMISSION"));
    }

    @Test
    @Order(3)
    @DisplayName("Test Update Permission")
    void testUpdatePermission() throws Exception {
        PermissionRequestDTO updateRequest = new PermissionRequestDTO();
        updateRequest.setCreated_user(userJpaRepo.findAll().getFirst().getId());
        updateRequest.setName("UPDATED_PERMISSION");

        mockMvc.perform(put(BASE_URL + "/" + testPermissionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("UPDATED_PERMISSION"));
    }

    @Test
    @Order(4)
    @DisplayName("Test Get All Permissions")
    void testGetAllPermissions() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("ACCESS"));
    }

    @Test
    @Order(5)
    @DisplayName("Test Search Permission by Name")
    void testSearchPermissionByName() throws Exception {
        mockMvc.perform(get(BASE_URL + "/search/UPDATED_PERMISSION"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("UPDATED_PERMISSION"));
    }

    @Test
    @Order(6)
    @DisplayName("Test Delete Permission by ID")
    void testDeletePermissionById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + testPermissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("UPDATED_PERMISSION"));
    }

    @Test
    @Order(7)
    @DisplayName("Test Clean Up Database")
    void testCleanUp()  throws Exception{
        // Ensure the permission no longer exists
        mockMvc.perform(get(BASE_URL + "/" + testPermissionId))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Execute the test cases and save the ID for future use
     * @param responseContent          String
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
