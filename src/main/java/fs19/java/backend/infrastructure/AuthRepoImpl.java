package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserPermissionConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AuthRepoImpl {

    private final UserJpaRepo userRepo;
    private final UserPermissionConfig userJpaRepoCustom;


    public AuthRepoImpl(UserJpaRepo userRepo, UserPermissionConfig userJpaRepoCustom) {
        this.userRepo = userRepo;
        this.userJpaRepoCustom = userJpaRepoCustom;
    }

    public Optional<User> findByEmail(String username) {
        return userRepo.findByEmail(username);
    }

    public User saveUser(User user) {
        userRepo.save(user);
        return user;
    }

    public List<UUID> findLinkWorkspaceIds(UUID id) {
        return userJpaRepoCustom.findLinkWorkspaceIds(id);
    }
}
