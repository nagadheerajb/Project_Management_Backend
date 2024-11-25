package fs19.java.backend.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.company.CompanyRequestDTO;
import fs19.java.backend.application.dto.company.CompanyResponseDTO;
import fs19.java.backend.application.dto.company.CompanyUpdateDTO;
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
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserJpaRepo userJpaRepo;

    private static UUID testCompanyId;
    private static UUID testUserId;

    private static final String BASE_URL = "/api/v1/companies";

    @BeforeEach
    void setUp() {
        // Retrieve a user from the repository for the createdBy field
        testUserId = userJpaRepo.findAll().get(0).getId();
    }

    @Test
    @Order(1)
    @DisplayName("Test Create Company")
    void testCreateCompany() throws Exception {
        CompanyRequestDTO request = new CompanyRequestDTO();
        request.setName("Example Company");
        request.setCreatedBy(testUserId);

        String responseContent = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Example Company"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        saveIdForExecuteTest(responseContent);
    }

    @Test
    @Order(2)
    @DisplayName("Test Get Company by ID")
    void testGetCompanyById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + testCompanyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Example Company"));
    }

    @Test
    @Order(3)
    @DisplayName("Test Update Company")
    void testUpdateCompany() throws Exception {
        CompanyUpdateDTO updateRequest = new CompanyUpdateDTO();
        updateRequest.setName("Updated Company");
        updateRequest.setCreatedBy(testUserId);

        mockMvc.perform(put(BASE_URL + "/" + testCompanyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Company"));
    }

    @Test
    @Order(4)
    @DisplayName("Test Get All Companies")
    void testGetAllCompanies() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("ABC"));
    }

    @Test
    @Order(5)
    @DisplayName("Test Delete Company by ID")
    void testDeleteCompanyById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + testCompanyId))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    @DisplayName("Test Cleanup - Company does not exist")
    void testCleanup() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + testCompanyId))
                .andExpect(status().isNotFound());
    }

    private void saveIdForExecuteTest(String responseContent) throws JsonProcessingException {
        GlobalResponse<CompanyResponseDTO> response = objectMapper.readValue(responseContent, GlobalResponse.class);
        Object data = response.getData();
        if (data instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) data;
            Object id = map.get("id");
            testCompanyId = UUID.fromString((String) id);
        }
    }
}