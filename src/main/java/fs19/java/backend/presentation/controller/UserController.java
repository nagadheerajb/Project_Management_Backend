package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.auth.AuthResponseDTO;
import fs19.java.backend.application.dto.auth.LoginRequestDTO;
import fs19.java.backend.application.dto.auth.SignupRequestDTO;
import fs19.java.backend.application.dto.user.UserCreateDTO;
import fs19.java.backend.application.UserServiceImpl;
import fs19.java.backend.application.dto.user.UserReadDTO;
import fs19.java.backend.application.mapper.UserMapper;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Users and Auth", description = "Manage Users and Authentication")
@RestController
@RequestMapping("/api/v1")
public class UserController {

  private static final Logger logger = LogManager.getLogger(UserController.class);
  private final UserServiceImpl userService;
  private final AuthenticationManager authenticationManager;

  public UserController(UserServiceImpl userService, AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.authenticationManager = authenticationManager;
  }

  @Operation(summary = "Create a user")
  @PostMapping("/users")
  public ResponseEntity<GlobalResponse<UserReadDTO>> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
    logger.info("Received request to create user: {}", userCreateDTO);
    UserReadDTO createdUser = userService.createUser(userCreateDTO);
    logger.info("User created successfully: {}", createdUser);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdUser), HttpStatus.CREATED);
  }

  @Operation
  @PutMapping("/users/{userId}")
  public ResponseEntity<GlobalResponse<UserReadDTO>> updateUser(@PathVariable UUID userId, @Valid @RequestBody UserReadDTO userToUpdateDTO) {
    logger.info("Received request to update user with ID: {} and DTO: {}", userId, userToUpdateDTO);
    UserReadDTO updatedUser = userService.updateUser(userId, userToUpdateDTO);
    logger.info("User updated successfully: {}", updatedUser);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedUser), HttpStatus.OK);
  }

  @Operation(summary = "Get user by ID")
  @GetMapping("/users/{userId}")
  public ResponseEntity<GlobalResponse<UserReadDTO>> getUserById(@PathVariable UUID userId) {
    logger.info("Received request to get user with ID: {}", userId);
    UserReadDTO user = userService.findUserById(userId);
    logger.info("User retrieved successfully: {}", user);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), user), HttpStatus.OK);
  }

  @Operation(summary = "Get all users")
  @GetMapping("/users")
  public ResponseEntity<GlobalResponse<List<UserReadDTO>>> getAllUsers() {
    logger.info("Received request to get all users");
    List<UserReadDTO> users = userService.findAllUsers();
    logger.info("Users retrieved successfully: {}", users);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), users), HttpStatus.OK);
  }

  @Operation(summary = "Delete user by ID")
  @DeleteMapping("/users/{userId}")
  public ResponseEntity<GlobalResponse<Void>> deleteUser(@PathVariable  UUID userId) {
    logger.info("Received request to delete user with ID: {}", userId);
    userService.deleteUser(userId);
    logger.info("User deleted successfully");
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null), HttpStatus.NO_CONTENT);
  }

  // ---- Auth Endpoints ----

  @PostMapping("/signup")
  public ResponseEntity<GlobalResponse<UserReadDTO>> signup(@RequestBody @Valid SignupRequestDTO signupRequestDTO) {
    logger.info("Received signUp request: {}", signupRequestDTO);
    User signUpUser = userService.signup(signupRequestDTO);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), UserMapper.toReadDTO(
        Objects.requireNonNullElseGet(signUpUser, User::new))), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<GlobalResponse<AuthResponseDTO>> login(@RequestBody @Valid LoginRequestDTO request) {
    logger.info("Received Login request: {}", request);
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
    AuthResponseDTO authenticateDto = userService.authenticate(request);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.FOUND.value(), authenticateDto), HttpStatus.FOUND);
  }
}
