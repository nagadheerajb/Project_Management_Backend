package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.AuthServiceImpl;
import fs19.java.backend.application.dto.auth.AuthResponseDTO;
import fs19.java.backend.application.dto.auth.LoginRequestDTO;
import fs19.java.backend.application.dto.auth.SignupRequestDTO;
import fs19.java.backend.application.dto.user.UserReadDTO;
import fs19.java.backend.application.mapper.UserMapper;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.presentation.shared.response.ErrorItem;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Tag(name = "Auth", description = "Manage Auth Actions")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private static final Logger logger = LogManager.getLogger(AuthController.class);
  private final AuthServiceImpl authService;
  private final AuthenticationManager authenticationManager;


  public AuthController(AuthServiceImpl authService, AuthenticationManager authenticationManager) {
    this.authService = authService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/signup")
  public ResponseEntity<GlobalResponse<UserReadDTO>> signup(@RequestBody @Valid SignupRequestDTO signupRequestDTO) {
    User signUpUser = authService.signup(signupRequestDTO);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), UserMapper.toReadDTO(Objects.requireNonNullElseGet(signUpUser, User::new))), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<GlobalResponse<AuthResponseDTO>> login(@RequestBody @Valid LoginRequestDTO request) {
    logger.info("Authenticating user with email: {}", request.email());

    // Use AuthenticationManager (now integrated with CustomAuthenticationProvider)
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
    );

    // Call additional custom logic if needed
    AuthResponseDTO authenticateDto = authService.authenticate(request);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), authenticateDto), HttpStatus.OK);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<GlobalResponse<AuthResponseDTO>> refreshToken(@RequestBody Map<String, String> requestBody) {
    logger.info("Refreshing token for user");

    String currentToken = requestBody.get("token"); // Extract token from the JSON payload
    if (currentToken == null || currentToken.isEmpty()) {
      logger.error("Token is missing in the request");

      // Create a List<ErrorItem> for the error response
      ErrorItem errorItem = new ErrorItem("TokenMissing", "Token is required");
      List<ErrorItem> errors = List.of(errorItem);

      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(new GlobalResponse<>(HttpStatus.BAD_REQUEST.value(), null, errors));
    }

    try {
      AuthResponseDTO authResponseDTO = authService.refreshToken(currentToken);
      return ResponseEntity.ok(new GlobalResponse<>(HttpStatus.OK.value(), authResponseDTO));
    } catch (Exception e) {
      logger.error("Error refreshing token: {}", e.getMessage());

      // Create a List<ErrorItem> for the exception
      ErrorItem errorItem = new ErrorItem("InvalidToken", e.getMessage());
      List<ErrorItem> errors = List.of(errorItem);

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(new GlobalResponse<>(HttpStatus.UNAUTHORIZED.value(), null, errors));
    }
  }


}

