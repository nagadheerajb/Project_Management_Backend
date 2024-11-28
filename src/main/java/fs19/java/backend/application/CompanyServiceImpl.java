package fs19.java.backend.application;

import fs19.java.backend.application.dto.company.CompanyRequestDTO;
import fs19.java.backend.application.dto.company.CompanyResponseDTO;
import fs19.java.backend.application.dto.company.CompanyUpdateDTO;
import fs19.java.backend.application.mapper.CompanyMapper;
import fs19.java.backend.application.service.CompanyService;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.infrastructure.JpaRepositories.CompanyJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.exception.CompanyNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger logger = LogManager.getLogger(CompanyServiceImpl.class);
    private static final String COMPANY_NOT_FOUND_MESSAGE = "Company with ID %s not found";
    private static final String USER_NOT_FOUND_MESSAGE = "User not found with ID %s";


    private final CompanyJpaRepo companyRepository;
    private final UserJpaRepo userRepository;
    private final ActivityLoggerService activityLoggerService;
    private final NotificationSender notificationSender;

    public CompanyServiceImpl(CompanyJpaRepo companyRepository, UserJpaRepo userRepository, ActivityLoggerService activityLoggerService, NotificationSender notificationSender) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.activityLoggerService = activityLoggerService;
        this.notificationSender = notificationSender;
    }

    @Override
    public CompanyResponseDTO createCompany(CompanyRequestDTO companyDTO) {
        logger.info("Creating company with DTO: {}", companyDTO);

        User createdBy = userRepository.findById(companyDTO.getCreatedBy())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, companyDTO.getCreatedBy())));
        logger.info("User found for company creation: {}", createdBy);

        Company company = CompanyMapper.toEntity(companyDTO, createdBy);
        company.setCreatedDate(ZonedDateTime.now());

        Company savedCompany = companyRepository.save(company);
        logger.info("Company created and saved: {}", savedCompany);

        logger.info("EntityType: {}", EntityType.COMPANY);
        logger.info("Entity ID: {}", savedCompany.getId());
        logger.info("Action: {}", ActionType.CREATED);
        logger.info("User ID: {}", createdBy.getId());

        activityLoggerService.logActivity(EntityType.COMPANY, savedCompany.getId(), ActionType.CREATED, createdBy.getId());
        logger.info("Activity logged for company creation");

        return CompanyMapper.toResponseDTO(savedCompany);
    }

    @Override
    public CompanyResponseDTO updateCompany(UUID id, CompanyUpdateDTO companyDTO) {
        logger.info("Updating company with ID: {} and DTO: {}", id, companyDTO);

        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, id)));
        logger.info("Existing company found: {}", existingCompany);

        User createdBy = userRepository.findById(companyDTO.getCreatedBy())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, companyDTO.getCreatedBy())));
        logger.info("User found for company update: {}", createdBy);

        CompanyMapper.updateEntity(existingCompany, companyDTO, createdBy);

        Company savedCompany = companyRepository.save(existingCompany);
        logger.info("Company updated and saved: {}", savedCompany);

        activityLoggerService.logActivity(EntityType.COMPANY, savedCompany.getId(), ActionType.UPDATED, createdBy.getId());
        logger.info("Activity logged for company update");

        return CompanyMapper.toResponseDTO(savedCompany);
    }

    @Override
    public CompanyResponseDTO getCompanyById(UUID id) {
        logger.info("Retrieving company with ID: {}", id);

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, id)));
        logger.info("Company retrieved: {}", company);

        return CompanyMapper.toResponseDTO(company);
    }

    @Override
    public List<CompanyResponseDTO> getAllCompanies() {
        logger.info("Retrieving all companies");

        List<CompanyResponseDTO> companies = companyRepository.findAll().stream()
                .map(CompanyMapper::toResponseDTO)
                .collect(Collectors.toList());
        logger.info("All companies retrieved: {}", companies);

        return companies;
    }

    @Override
    public void deleteCompany(UUID id) {
        logger.info("Deleting company with ID: {}", id);

        if (!companyRepository.existsById(id)) {
            logger.error("Company with ID: {} not found for deletion", id);
            throw new CompanyNotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, id));
        }

        companyRepository.deleteById(id);
        logger.info("Company with ID: {} deleted", id);
        //User createdBy = SecurityUtils.getCurrentUser();
        //activityLoggerService.logActivity(EntityType.COMPANY, id, ActionType.DELETED, createdBy.getId());
    }
}