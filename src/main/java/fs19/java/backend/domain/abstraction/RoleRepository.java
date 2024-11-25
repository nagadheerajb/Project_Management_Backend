package fs19.java.backend.domain.abstraction;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Role;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Define the role repository
 */
public interface RoleRepository {

    Role save(Role role);

    Role update(UUID roleId, RoleRequestDTO role, Company company);

    Role delete(UUID roleId);

    List<Role> findAll();

    Role findByName(String roleName);

    Role findById(UUID roleId);

    boolean existsById(UUID roleId);

    Optional<Company> getCompanyByCompanyId(@NotNull UUID companyId);
}
