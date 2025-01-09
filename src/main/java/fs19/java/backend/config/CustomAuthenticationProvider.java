package fs19.java.backend.security;

import fs19.java.backend.application.AuthServiceImpl;
import fs19.java.backend.application.dto.auth.LoginRequestDTO;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserValidationException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AuthServiceImpl authService;

    public CustomAuthenticationProvider(@Lazy AuthServiceImpl authService) {
        this.authService = authService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        try {
            // Delegate authentication to AuthServiceImpl
            authService.authenticate(new LoginRequestDTO(email, password));
        } catch (UserNotFoundException ex) {
            throw new BadCredentialsException("Invalid email or password.", ex);
        } catch (UserValidationException ex) {
            throw new BadCredentialsException(ex.getMessage(), ex);
        }

        // Return authenticated token
        return new UsernamePasswordAuthenticationToken(email, password, null); // Add authorities if needed
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
