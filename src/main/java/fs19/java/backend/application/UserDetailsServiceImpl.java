package fs19.java.backend.application;

import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.domain.entity.WorkspaceUser;
import fs19.java.backend.infrastructure.AuthRepoImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Auth action service implementation
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AuthRepoImpl authRepo;

    public UserDetailsServiceImpl(AuthRepoImpl authRepo) {
        this.authRepo = authRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<fs19.java.backend.domain.entity.User> user = authRepo.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        //toDO: convert to single request
        List<WorkspaceUser> userWorkSpaces = authRepo.getUserWorkSpaces(user.get().getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        userWorkSpaces.forEach(workspaceUser -> {
            List<Permission> permissions = authRepo.getPermissions(workspaceUser.getRole().getId());
            permissions.forEach(permission -> {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            });
        });
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}