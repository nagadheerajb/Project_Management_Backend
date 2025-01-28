package fs19.java.backend.application;

import fs19.java.backend.application.dto.auth.AuthResponseDTO;
import fs19.java.backend.application.dto.auth.LoginRequestDTO;
import fs19.java.backend.application.dto.auth.SignupRequestDTO;
import fs19.java.backend.application.mapper.UserMapper;
import fs19.java.backend.config.JwtValidator;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.AuthRepoImpl;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import fs19.java.backend.presentation.shared.exception.UserAlreadyFoundException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Responsible to handle auth actions
 */
@Service
public class AuthServiceImpl {

    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);
    private final AuthRepoImpl authRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtValidator jwtValidator;

    public AuthServiceImpl(AuthRepoImpl authRepo, PasswordEncoder passwordEncoder,
                           JwtValidator jwtValidator) {
        this.authRepo = authRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtValidator = jwtValidator;
    }

    /**
     * Sign up the user
     *
     * @param signupRequestDTO
     * @return
     */
    public User signup(SignupRequestDTO signupRequestDTO) {
        String email = signupRequestDTO.email();
        Optional<User> existingUser = authRepo.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new UserAlreadyFoundException(
                    "Given Email Already exist, Can't create a new User record ");
        }
        validateSignUpRequest(signupRequestDTO);
        User user = new User();
        user.setFirstName(signupRequestDTO.firstName());
        user.setLastName(signupRequestDTO.lastName());
        user.setEmail(signupRequestDTO.email());
        user.setPassword(passwordEncoder.encode(signupRequestDTO.password()));
        user.setCreatedDate(DateAndTime.getDateAndTime());
        return authRepo.saveUser(user);
    }

    /**
     * Authenticate the login access
     *
     * @param request
     * @return
     */
    public AuthResponseDTO authenticate(LoginRequestDTO request) {
        logger.info("Authenticating user with email: {}", request.email());
        Optional<User> user = authRepo.findByEmail(request.email());
        if (user.isEmpty()) {
            logger.error("Given Email Not exist: User Not Found");
            throw new UserNotFoundException("Given Email Not exist: User Not Found");
        }
        if (!passwordEncoder.matches(request.password(), user.get().getPassword())) {
            logger.error("Invalid password. Please try again.");
            throw new UserValidationException("Invalid password. Please try again.");
        }
        String accessToken = jwtValidator.generateToken(user.get(), authRepo.findLinkWorkspaceIds(user.get().getId()));
        logger.info("User authenticated successfully{}", accessToken);
        return UserMapper.toAuthResponseDTO(user.get(), accessToken);
    }

    public AuthResponseDTO refreshToken(String currentToken) {
        logger.info("Validating and refreshing token");

        String currentToken1 = jwtValidator.removeBearerPrefix(currentToken);
        // Validate the current token
        String email = jwtValidator.extractUserEmail(currentToken1);
        Optional<User> userOptional = authRepo.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found for the given token");
        }

        User user = userOptional.get();

        // Extract linked workspaces (or other permissions) for the new token
        List<UUID> workspaceIds = authRepo.findLinkWorkspaceIds(user.getId());

        // Generate a new token
        String newAccessToken = jwtValidator.generateToken(user, workspaceIds);

        logger.info("Generated new token for user: {}", user.getEmail());

        return UserMapper.toAuthResponseDTO(user, newAccessToken);
    }


    public static void validateSignUpRequest(SignupRequestDTO signupRequestDTO) {
        if (signupRequestDTO.email() == null || signupRequestDTO.email().isEmpty()) {
            throw new UserValidationException("Email is required");
        }
        if (signupRequestDTO.password() == null || signupRequestDTO.password().isEmpty()) {
            throw new UserValidationException("Password is required");
        }
        if (signupRequestDTO.password().length() < 6) {
            throw new UserValidationException("Password must be between 6 and 40 characters");
        }
        if (signupRequestDTO.firstName() == null || signupRequestDTO.firstName().isEmpty()) {
            throw new UserValidationException("FirstName is required");
        }
        if (signupRequestDTO.lastName() == null || signupRequestDTO.lastName().isEmpty()) {
            throw new UserValidationException("LastName is required");
        }
    }
}
