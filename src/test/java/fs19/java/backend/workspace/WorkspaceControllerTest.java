package fs19.java.backend.workspace;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.workspace.WorkspaceRequestDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceResponseDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceUpdateDTO;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.domain.entity.enums.WorkspaceType;
import fs19.java.backend.infrastructure.JpaRepositories.WorkspaceJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.CompanyJpaRepo;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Commit
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WorkspaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WorkspaceJpaRepo workspaceRepository;

    @Autowired
    private UserJpaRepo userRepository;

    @Autowired
    private CompanyJpaRepo companyRepository;

    private static UUID testWorkspaceId;
    private static UUID testUserId;
    private static UUID testCompanyId;

    private static final String BASE_URL = "/api/v1/workspaces";

    @BeforeEach
    public void setUp() {
        // Retrieve a user from the repository for the createdBy field
        User existingUser = userRepository.findAll().get(0); // Assuming at least one user exists
        testUserId = existingUser.getId();

        // Retrieve a company from the repository for the companyId field
        Company existingCompany = companyRepository.findAll().get(0); // Assuming at least one company exists
        testCompanyId = existingCompany.getId();
    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Create Workspace")
    void testCreateWorkspace() throws Exception {
        WorkspaceRequestDTO request = new WorkspaceRequestDTO();
        request.setName("New Workspace");
        request.setDescription("New Description");
        request.setType(WorkspaceType.PUBLIC);
        request.setCreatedBy(testUserId);
        request.setCompanyId(testCompanyId);

        String responseContent = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("New Workspace"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        saveIdForExecuteTest(responseContent);
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get Workspace by ID")
    void testGetWorkspaceById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testWorkspaceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("New Workspace"));
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Update Workspace")
    void testUpdateWorkspace() throws Exception {
        WorkspaceUpdateDTO updateRequest = new WorkspaceUpdateDTO();
        updateRequest.setName(Optional.of("Updated Workspace"));
        updateRequest.setDescription(Optional.of("Updated Description"));
        updateRequest.setType(Optional.of(WorkspaceType.SHARED));
        updateRequest.setCompanyId(Optional.of(testCompanyId));

        mockMvc.perform(put(BASE_URL + "/" + testWorkspaceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Workspace"));
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get All Workspaces")
    void testGetAllWorkspaces() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("Workspace1"));
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Delete Workspace by ID")
    void testDeleteWorkspaceById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + testWorkspaceId))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    @WithMockUser(username = "admin", authorities = {"TEST-USER"})
    @DisplayName("Test Get Non-Existent Workspace")
    void testGetNonExistentWorkspace() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        mockMvc.perform(get(BASE_URL + "/" + nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.errors[0].message", containsString("Workspace with ID " + nonExistentId + " not found")));
    }

    private void saveIdForExecuteTest(String responseContent) throws JsonProcessingException {
        GlobalResponse<WorkspaceResponseDTO> response = objectMapper.readValue(responseContent, GlobalResponse.class);
        Object data = response.getData();
        if (data instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) data;
            Object id = map.get("id");
            testWorkspaceId = UUID.fromString((String) id);
        }
    }
}
