package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CompanyJpaRepo extends JpaRepository<Company, UUID> {
    List<Company> findByCreatedBy(User createdBy);
}
