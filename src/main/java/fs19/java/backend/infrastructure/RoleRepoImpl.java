package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.domain.abstraction.RoleRepository;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.infrastructure.JpaRepositories.CompanyJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.RoleJpaRepo;
import fs19.java.backend.presentation.shared.exception.RoleLevelException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Role Repo implementation
 */
@Repository
public class RoleRepoImpl implements RoleRepository {


    private final RoleJpaRepo roleJpaRepo;
    private final CompanyJpaRepo companyJpaRepo;

    public RoleRepoImpl(RoleJpaRepo roleJpaRepo, CompanyJpaRepo companyJpaRepo) {
        this.roleJpaRepo = roleJpaRepo;
        this.companyJpaRepo = companyJpaRepo;
    }

    /**
     * Create a new role according to user specified details
     *
     * @param role RoleRequestDTO
     * @return Role
     */
    @Override
    public Role save(Role role) {
        try {
            return roleJpaRepo.save(role);
        } catch (Exception e) {
            throw new RoleLevelException(e.getLocalizedMessage() + " : " + RoleLevelException.ROLE_CREATE);
        }
    }

    /**
     * Update role according to user specified data
     *
     * @param roleId
     * @param role
     * @param company
     * @return
     */
    @Override
    public Role update(UUID roleId, RoleRequestDTO role, Company company) {
        Role myRole = findById(roleId);
        myRole.setName(role.getName());
        myRole.setCompany(company);
        return roleJpaRepo.save(myRole);
    }

    @Override
    public Role delete(UUID roleId) {
        Role role = findById(roleId);
        roleJpaRepo.delete(role);
        return role;
    }

    /**
     * Return all roles
     *
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleJpaRepo.findAll();
    }

    @Override
    public Role findByName(String roleName) {
        return roleJpaRepo.findByName(roleName);
    }

    /**
     * Get role By Id
     *
     * @param roleId
     * @return
     */
    @Override
    public Role findById(UUID roleId) {
        return roleJpaRepo.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role result not found with ID: " + roleId));

    }

    /**
     * Check id is existing
     *
     * @param roleId
     * @return
     */
    @Override
    public boolean existsById(UUID roleId) {
        return roleJpaRepo.existsById(roleId);
    }

    @Override
    public Optional<Company> getCompanyByCompanyId(UUID companyId) {
        return companyJpaRepo.findById(companyId);
    }
}
