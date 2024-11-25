package fs19.java.backend.application;

import fs19.java.backend.application.dto.invitation.InvitationRequestDTO;
import fs19.java.backend.application.dto.invitation.InvitationResponseDTO;
import fs19.java.backend.application.mapper.InvitationMapper;
import fs19.java.backend.application.service.InvitationService;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Invitation;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.infrastructure.InvitationRepoImpl;
import fs19.java.backend.infrastructure.JpaRepositories.CompanyJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.RoleRepoImpl;
import fs19.java.backend.presentation.controller.ActivityLogController;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {

    private static final Logger logger = LogManager.getLogger(ActivityLogController.class);

    private final InvitationRepoImpl invitationRepo;
    private final RoleRepoImpl roleRepo;
    private final ActivityLoggerService activityLoggerService;
    private final UserJpaRepo userJpaRepo;
    private final CompanyJpaRepo companyJpaRepo;


    public InvitationServiceImpl(InvitationRepoImpl invitationRepo, RoleRepoImpl roleRepo, ActivityLoggerService activityLoggerService, UserJpaRepo userJpaRepo, CompanyJpaRepo companyJpaRepo) {
        this.invitationRepo = invitationRepo;
        this.roleRepo = roleRepo;
        this.activityLoggerService = activityLoggerService;
        this.userJpaRepo = userJpaRepo;
        this.companyJpaRepo = companyJpaRepo;
    }

    @Override
    public InvitationResponseDTO save(InvitationRequestDTO invitationRequestDTO) {
        Invitation myInvitation;
        if (invitationRequestDTO.getEmail().isEmpty()) {
            logger.info("Invitation Email from DTO is null, cannot proceed with Invitation creation {}", invitationRequestDTO);
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_EMAIL_NOT_FOUND);
        }
        Role roleById = roleRepo.findById(invitationRequestDTO.getRoleId());
        if (roleById == null) {
            logger.info("No Valid Role Result Found when trying to create. {}", invitationRequestDTO.getRoleId());
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ROLE_NOT_FOUND);
        } else {
            Optional<User> created_userOptional = userJpaRepo.findById(invitationRequestDTO.getCreated_user());
            if (created_userOptional.isPresent()) {
                Optional<Company> companyOptional = companyJpaRepo.findById(invitationRequestDTO.getCompanyId());
                if (companyOptional.isPresent()) {
                    myInvitation = invitationRepo.save(InvitationMapper.toInvitation(invitationRequestDTO, roleById, created_userOptional.get(), companyOptional.get()));
                    if (myInvitation == null) {
                        return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVALID_INFORMATION_INVITATION_DETAILS_NOT_CREATED);
                    }
                    activityLoggerService.logActivity(EntityType.INVITATION, myInvitation.getId(), ActionType.CREATED, created_userOptional.get().getId());
                    return InvitationMapper.toInvitationResponseDTO(myInvitation, ResponseStatus.SUCCESSFULLY_CREATED);
                } else {
                    return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_COMPANY_ID_NOT_FOUND);

                }

            } else {
                return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.USER_ID_NOT_FOUND);

            }

        }


    }

    @Override
    public InvitationResponseDTO update(UUID invitationId, @Valid InvitationRequestDTO invitationRequestDTO) {
        Invitation myInvitation;
        if (invitationId == null) {
            logger.info("Invitation Id is null, cannot proceed with Invitation update.{}", invitationRequestDTO);
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ID_NOT_FOUND);
        } else if (invitationRequestDTO.getEmail().isEmpty()) {
            logger.info("Invitation Email from DTO is null, cannot proceed with Invitation update.{}", invitationRequestDTO);
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_EMAIL_NOT_FOUND);
        }
        Role roleById = roleRepo.findById(invitationRequestDTO.getRoleId());
        if (roleById == null) {
            logger.info("No Valid Role Result Found when trying to create. {}", invitationRequestDTO.getRoleId());
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ROLE_NOT_FOUND);
        } else {
            if (invitationRequestDTO.getCompanyId() != null) {
                Optional<User> created_userOptional = userJpaRepo.findById(invitationRequestDTO.getCreated_user());
                if (created_userOptional.isPresent()) {
                    Optional<Company> companyOptional = companyJpaRepo.findById(invitationRequestDTO.getCompanyId());
                    if (companyOptional.isPresent()) {
                        myInvitation = invitationRepo.update(invitationId, invitationRequestDTO, roleById, created_userOptional.get(), companyOptional.get());
                        if (myInvitation == null) {
                            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVALID_INFORMATION_INVITATION_DETAILS_NOT_CREATED);
                        }
                        activityLoggerService.logActivity(EntityType.INVITATION, myInvitation.getId(), ActionType.UPDATED, myInvitation.getCreatedBy().getId());
                        return InvitationMapper.toInvitationResponseDTO(myInvitation, ResponseStatus.SUCCESSFULLY_CREATED);

                    } else {
                        return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_COMPANY_ID_NOT_FOUND);

                    }

                } else {
                    return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.USER_ID_NOT_FOUND);

                }
            } else {
                logger.info("No Valid Company ID Found. {}", invitationRequestDTO);
                return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_COMPANY_ID_NOT_FOUND);

            }

        }

    }

    @Override
    public InvitationResponseDTO delete(UUID invitationId) {
        Invitation myInvitation;
        if (invitationId == null) {
            logger.info("Invitation Id is null, cannot proceed with Invitation delete. {}", invitationId);
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ID_NOT_FOUND);
        }
        myInvitation = invitationRepo.findById(invitationId);
        if (myInvitation == null) {
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVALID_INFORMATION_INVITATION_DETAILS_NOT_DELETED);
        }
        activityLoggerService.logActivity(EntityType.INVITATION, myInvitation.getId(), ActionType.DELETED, myInvitation.getCreatedBy().getId());
        return InvitationMapper.toInvitationResponseDTO(invitationRepo.delete(invitationId), ResponseStatus.SUCCESSFULLY_DELETED);
    }

    @Override
    public List<InvitationResponseDTO> findAll() {
        return InvitationMapper.toInvitationResponseDTOs(this.invitationRepo.findAll(), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    @Override
    public InvitationResponseDTO findById(UUID invitationId) {
        Invitation myInvitation;
        if (invitationId == null) {
            logger.info("Invitation Id is null, cannot proceed with Invitation search.{}", invitationId);
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ID_NOT_FOUND);
        }
        myInvitation = invitationRepo.findById(invitationId);
        if (myInvitation == null) {
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVALID_INFORMATION_INVITATION_DETAILS_NOT_FOUND);
        }
        activityLoggerService.logActivity(EntityType.INVITATION, myInvitation.getId(), ActionType.DELETED, myInvitation.getCreatedBy().getId());
        return InvitationMapper.toInvitationResponseDTO(myInvitation, ResponseStatus.SUCCESSFULLY_FOUND);
    }
}
