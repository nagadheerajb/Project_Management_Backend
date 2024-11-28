package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.application.dto.role.RoleResponseDTO;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will ensure the role related mapping
 */
public class RoleMapper {

    /**
     * Convert role into Role response dto
     *
     * @param role   Role
     * @param status ResponseStatus
     * @return RoleResponseDTO
     */
    public static RoleResponseDTO toRoleResponseDTO(Role role, ResponseStatus status) {
        return new RoleResponseDTO(role.getId(), role.getName(), role.getCreatedDate(), role.getCompany() == null ? null : role.getCompany().getId(), status);
    }

    /**
     * Convert role list into role response dto list
     * @param roles  List<Role> roles
     * @param status ResponseStatus
     * @return  seDTO>
     */
    public static List<RoleResponseDTO> toRoleResponseDTOs(List<Role> roles, ResponseStatus status) {
        List<RoleResponseDTO> responseDTOS = new ArrayList<>();
        roles.forEach(role -> responseDTOS.add(toRoleResponseDTO(role, status)));
        return responseDTOS;
    }


    /**
     * Convert request object to entity object
     * @param roleRequestDTO RoleRequestDTO
     * @param company        Company
     * @return Role
     */
    public static Role toRole(@Valid RoleRequestDTO roleRequestDTO, Company company) {
        Role myRole = new Role();
        myRole.setName(roleRequestDTO.getName());
        myRole.setCreatedDate(DateAndTime.getDateAndTime());
        myRole.setCompany(company);
        return myRole;
    }
}
