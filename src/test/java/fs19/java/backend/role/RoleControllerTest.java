package fs19.java.backend.role;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.application.dto.role.RoleResponseDTO;
import fs19.java.backend.infrastructure.JpaRepositories.CompanyJpaRepo;
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
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static UUID testRoleId;

    private static final String BASE_URL = "/app/v1/roles";

    @Autowired
    private CompanyJpaRepo companyJpaRepo;

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Test
    @Order(1)
    @DisplayName("Test Create Role")
    void testCreateRole() throws Exception {
        RoleRequestDTO request = new RoleRequestDTO();
        request.setName("Admin");
        request.setCompanyId(companyJpaRepo.findAll().getFirst().getId());
        request.setCreated_user(userJpaRepo.findAll().getFirst().getId());

        String responseContent = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Admin"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        saveIdForExecuteTest(responseContent);
    }

    @Test
    @Order(2)
    @DisplayName("Test Get Role by ID")
    void testGetRoleById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testRoleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Admin"));
    }

    @Test
    @Order(3)
    @DisplayName("Test Update Role")
    void testUpdateRole() throws Exception {
        RoleRequestDTO updateRequest = new RoleRequestDTO();
        updateRequest.setName("UpdatedRole");
        updateRequest.setCompanyId(companyJpaRepo.findAll().getFirst().getId());
        updateRequest.setCreated_user(userJpaRepo.findAll().getFirst().getId());

        mockMvc.perform(put(BASE_URL + "/" + testRoleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("UpdatedRole"));
    }

    @Test
    @Order(4)
    @DisplayName("Test Get All Roles")
    void testGetAllRoles() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("ADMIN"));
    }

    @Test
    @Order(5)
    @DisplayName("Test Search Role by Name")
    void testSearchRoleByName() throws Exception {
        mockMvc.perform(get(BASE_URL + "/search/UpdatedRole"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("UpdatedRole"));
    }

    @Test
    @Order(6)
    @DisplayName("Test Delete Role by ID")
    void testDeleteRoleById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + testRoleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("UpdatedRole"));
    }

    @Test
    @Order(7)
    @DisplayName("Test Clean Up Database")
    void testCleanUp() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testRoleId))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Execute the test cases and save the ID for future use
     *
     * @param responseContent String
     * @throws JsonProcessingException
     */
    private void saveIdForExecuteTest(String responseContent) throws JsonProcessingException {
        GlobalResponse<RoleResponseDTO> response = objectMapper.readValue(responseContent, GlobalResponse.class);
        Object data = response.getData();
        if (data instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) data;
            Object id = map.get("id");
            testRoleId = UUID.fromString((String) id);
        }
    }
}
