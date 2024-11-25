package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.company.CompanyRequestDTO;
import fs19.java.backend.application.dto.company.CompanyResponseDTO;
import fs19.java.backend.application.dto.company.CompanyUpdateDTO;
import java.util.List;
import java.util.UUID;

public interface CompanyService {
    CompanyResponseDTO createCompany(CompanyRequestDTO companyDTO);
    CompanyResponseDTO updateCompany(UUID id, CompanyUpdateDTO companyDTO);
    CompanyResponseDTO getCompanyById(UUID id);
    List<CompanyResponseDTO> getAllCompanies();
    void deleteCompany(UUID id);
}