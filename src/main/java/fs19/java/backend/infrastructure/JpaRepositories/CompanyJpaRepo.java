package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyJpaRepo extends JpaRepository<Company, UUID> {
}
