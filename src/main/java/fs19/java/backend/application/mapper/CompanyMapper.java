package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.company.CompanyRequestDTO;
import fs19.java.backend.application.dto.company.CompanyResponseDTO;
import fs19.java.backend.application.dto.company.CompanyUpdateDTO;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CompanyMapper {

    // Convert CompanyRequestDTO to Company entity
    public Company toEntity(CompanyRequestDTO dto, User createdBy) {
        Company company = new Company();
        company.setName(dto.getName());
        company.setCreatedBy(createdBy);  // Ensure createdBy is set
        return company;
    }

    // Convert Company entity to CompanyResponseDTO
    public CompanyResponseDTO toResponseDTO(Company company) {
        CompanyResponseDTO companyDTO = new CompanyResponseDTO();
        companyDTO.setId(company.getId());
        companyDTO.setName(company.getName());
        companyDTO.setCreatedDate(company.getCreatedDate());
        companyDTO.setCreatedBy(company.getCreatedBy().getId());
        return companyDTO;
    }

    // Convert CompanyUpdateDTO to Company entity for updates
    public void updateEntity(Company existingCompany, CompanyUpdateDTO dto, User createdBy) {
        existingCompany.setName(dto.getName());
        existingCompany.setCreatedBy(createdBy);
    }
}