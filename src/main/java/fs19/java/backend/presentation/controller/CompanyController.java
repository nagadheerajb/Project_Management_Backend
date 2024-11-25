package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.company.CompanyRequestDTO;
import fs19.java.backend.application.dto.company.CompanyResponseDTO;
import fs19.java.backend.application.dto.company.CompanyUpdateDTO;
import fs19.java.backend.application.service.CompanyService;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "Companies", description = "Manage companies")
@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private static final Logger logger = LogManager.getLogger(CompanyController.class);
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(summary = "Create a company", description = "Creates a new company with the provided details.")
    @PostMapping
    public ResponseEntity<GlobalResponse<CompanyResponseDTO>> createCompany(@Valid @RequestBody CompanyRequestDTO companyDTO) {
        logger.info("Received request to create company: {}", companyDTO);
        CompanyResponseDTO createdCompany = companyService.createCompany(companyDTO);
        logger.info("Company created successfully: {}", createdCompany);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdCompany), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a company", description = "Updates the details of an existing company.")
    @PutMapping("/{companyId}")
    public ResponseEntity<GlobalResponse<CompanyResponseDTO>> updateCompany(@PathVariable UUID companyId, @Valid @RequestBody CompanyUpdateDTO companyDTO) {
        logger.info("Received request to update company with ID: {} and DTO: {}", companyId, companyDTO);
        CompanyResponseDTO updatedCompany = companyService.updateCompany(companyId, companyDTO);
        logger.info("Company updated successfully: {}", updatedCompany);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedCompany), HttpStatus.OK);
    }

    @Operation(summary = "Get a company by ID", description = "Retrieves the details of a company by its ID.")
    @GetMapping("/{companyId}")
    public ResponseEntity<GlobalResponse<CompanyResponseDTO>> getCompanyById(@PathVariable UUID companyId) {
        logger.info("Received request to get company with ID: {}", companyId);
        CompanyResponseDTO company = companyService.getCompanyById(companyId);
        logger.info("Company retrieved successfully: {}", company);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), company), HttpStatus.OK);
    }

    @Operation(summary = "Get all companies", description = "Retrieves the details of all companies.")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<CompanyResponseDTO>>> getAllCompanies() {
        logger.info("Received request to get all companies");
        List<CompanyResponseDTO> companies = companyService.getAllCompanies();
        logger.info("All companies retrieved successfully");
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), companies), HttpStatus.OK);
    }

    @Operation(summary = "Delete a company", description = "Deletes a company by its ID.")
    @DeleteMapping("/{companyId}")
    public ResponseEntity<GlobalResponse<Void>> deleteCompany(@PathVariable UUID companyId) {
        logger.info("Received request to delete company with ID: {}", companyId);
        companyService.deleteCompany(companyId);
        logger.info("Company deleted successfully with ID: {}", companyId);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null), HttpStatus.NO_CONTENT);
    }
}