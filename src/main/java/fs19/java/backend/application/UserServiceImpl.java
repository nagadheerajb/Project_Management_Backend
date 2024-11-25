package fs19.java.backend.application;

import fs19.java.backend.application.dto.auth.AuthResponseDTO;
import fs19.java.backend.application.dto.auth.LoginRequestDTO;
import fs19.java.backend.application.dto.auth.SignupRequestDTO;
import fs19.java.backend.application.dto.user.UserCreateDTO;
import fs19.java.backend.application.dto.user.UserReadDTO;
import fs19.java.backend.application.mapper.UserMapper;
import fs19.java.backend.application.service.UserService;
import fs19.java.backend.config.JwtValidator;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import fs19.java.backend.presentation.shared.exception.UserAlreadyFoundException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserValidationException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
  private static final String ERROR_MESSAGE = "User not found with ID ";
  private final PasswordEncoder passwordEncoder;
  private final JwtValidator jwtValidator;
  private final UserJpaRepo userRepository;

  private final ActivityLoggerService activityLoggerService;

  public UserServiceImpl(UserJpaRepo userRepository, ActivityLoggerService activityLoggerService,
       PasswordEncoder passwordEncoder, JwtValidator jwtValidator) {
    this.userRepository = userRepository;
    this.activityLoggerService = activityLoggerService;
    this.passwordEncoder = passwordEncoder;
    this.jwtValidator = jwtValidator;
  }

  @Override
  public UserReadDTO createUser(UserCreateDTO createUserDTO) {
    logger.info("Creating user with DTO: {}", createUserDTO);

    validateUserCreateDTO(createUserDTO);

    User user = UserMapper.toEntity(createUserDTO);
    user.setCreatedDate(ZonedDateTime.now());
    user = userRepository.save(user);
    logger.info("User created and saved: {}", user);

    logger.info("EntityType: {}", EntityType.USER);
    logger.info("Entity ID: {}", user.getId());
    logger.info("Action: {}", ActionType.CREATED);
    logger.info("User ID: {}", user.getId());
    activityLoggerService.logActivity(EntityType.USER, user.getId(), ActionType.CREATED, user.getId());
    logger.info("Activity logged for user creation: {}", user.getId());

    return UserMapper.toReadDTO(user);
  }

  @Override
  public UserReadDTO updateUser(UUID id, UserReadDTO userDTO) {
    logger.info("Updating user with ID: {} and DTO: {}", id, userDTO);

    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      logger.info("User found for update: {}", user.get());

      User updatedUser = user.get();
      updatedUser.setFirstName(userDTO.getFirstName());
      updatedUser.setLastName(userDTO.getLastName());
      updatedUser.setEmail(userDTO.getEmail());
      updatedUser.setPhone(userDTO.getPhone());
      updatedUser.setProfileImage(userDTO.getProfileImage());
      updatedUser = userRepository.save(updatedUser);
      logger.info("User updated and saved: {}", updatedUser);
      activityLoggerService.logActivity(EntityType.USER, updatedUser.getId(), ActionType.UPDATED, updatedUser.getId());

      return UserMapper.toReadDTO(updatedUser);
    } else {
      throw new UserNotFoundException(ERROR_MESSAGE + id);
    }
  }

  @Override
  public UserReadDTO findUserById(UUID id) {
    logger.info("Finding user by ID: {}", id);

    Optional<User> userOptional = userRepository.findById(id);
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException(ERROR_MESSAGE + id);
    }
    logger.info("User found: {}", userOptional.get());
    return UserMapper.toReadDTO(userOptional.get());
  }

  @Override
  public List<UserReadDTO> findAllUsers() {
    logger.info("Finding all users");

    List<User> users = userRepository.findAll();
    List<UserReadDTO> userReadDTOs = users.stream()
        .map(UserMapper::toReadDTO)
        .toList();
    logger.info("Users found: {}", userReadDTOs);
    return userReadDTOs;

  }

  @Override
  public boolean deleteUser(UUID id) {
    logger.info("Deleting user with ID: {}", id);

    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      logger.info("User found for deletion: {}", user.get());

      userRepository.delete(user.get());
      logger.info("User deleted successfully");

      return true;
    } else {
      throw new UserNotFoundException(ERROR_MESSAGE + id);
    }
  }
  //------signup and authenticate methods------

  /**
   * Sign up the user
   *
   * @param signupRequestDTO
   * @return
   */
  public User signup(SignupRequestDTO signupRequestDTO) {
    String email = signupRequestDTO.email();
    Optional<User> existingUser = userRepository.findByEmail(email);
    if (existingUser.isPresent()) {
      logger.error("Given Email Already exist, Can't create a new User record ");

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
    return userRepository.save(user);
  }

  /**
   * Authenticate the login access
   *
   * @param request
   * @return
   */
  public AuthResponseDTO authenticate(LoginRequestDTO request) {
    Optional<User> user = userRepository.findByEmail(request.email());
    if (user.isEmpty()) {
      logger.error("Given Email Not exist: User Not Found");
      throw new UserNotFoundException("Given Email Not exist: User Not Found");
    }
    String accessToken = jwtValidator.generateToken(user.get());
    logger.info("User Authenticated Successfully");
    return UserMapper.toAuthResponseDTO(user.get(), accessToken);
  }

  private void validateUserCreateDTO(UserCreateDTO createUserDTO) {
    if (createUserDTO.getFirstName() == null || createUserDTO.getFirstName().isEmpty()) {
      throw new UserValidationException("First name is required");
    }
    if (createUserDTO.getLastName() == null || createUserDTO.getLastName().isEmpty()) {
      throw new UserValidationException("Last name is required");
    }
    if (createUserDTO.getEmail() == null || createUserDTO.getEmail().isEmpty()) {
      throw new UserValidationException("Email is required");
    }
    if (createUserDTO.getPassword() != null && createUserDTO.getPassword().length() < 8) {
      throw new UserValidationException("Password must be at least 8 characters long");
    }
    if (createUserDTO.getPhone().length() > 15 || createUserDTO.getPhone().length() < 10) {
      throw new UserValidationException("Phone number must be between 10 and 15 characters long");
    }
  }


  public static void validateSignUpRequest(SignupRequestDTO signupRequestDTO) {
    if (signupRequestDTO.email() == null || signupRequestDTO.email().isEmpty()) {
      throw new UserValidationException("Email is required");
    }
    if (signupRequestDTO.password() == null || signupRequestDTO.password().isEmpty()) {
      throw new UserValidationException("Password is required");
    }
    if (signupRequestDTO.password().length() < 6 || signupRequestDTO.password().length() > 40) {
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


