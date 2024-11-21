package fs19.java.backend.company;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.company.CompanyDTO;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.abstraction.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompanyRepository companyRepository;

    private Company existingCompany;

    @BeforeEach
    public void setUp() {
        existingCompany = new Company(UUID.randomUUID(), "Test Company", null, UUID.randomUUID());
        companyRepository.save(existingCompany);
    }

    @Test
    public void testCreateCompany() throws Exception {
        UUID createdBy = UUID.randomUUID();
        CompanyDTO request = new CompanyDTO(null, "New Company", null, createdBy);

        mockMvc.perform(post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(201)))
                .andExpect(jsonPath("$.data.name", is("New Company")));
    }

    @Test
    public void testUpdateCompany() throws Exception {
        CompanyDTO request = new CompanyDTO(existingCompany.getId(), "Updated Company", null, existingCompany.getCreatedBy());

        mockMvc.perform(put("/api/v1/companies/" + existingCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.name", is("Updated Company")));
    }

    @Test
    public void testGetCompanyById() throws Exception {
        mockMvc.perform(get("/api/v1/companies/" + existingCompany.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.name", is(existingCompany.getName())));
    }

    @Test
    public void testGetAllCompanies() throws Exception {
        mockMvc.perform(get("/api/v1/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testDeleteCompany() throws Exception {
        mockMvc.perform(delete("/api/v1/companies/" + existingCompany.getId()))
                .andExpect(status().isNoContent());
    }

    private ResultActions performPostCompany(CompanyDTO request) throws Exception {
        return mockMvc.perform(post("/api/v1/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }
}