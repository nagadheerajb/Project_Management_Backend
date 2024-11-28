package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.AuthServiceImpl;
import fs19.java.backend.application.dto.auth.AuthResponseDTO;
import fs19.java.backend.application.dto.auth.LoginRequestDTO;
import fs19.java.backend.application.dto.auth.SignupRequestDTO;
import fs19.java.backend.application.dto.user.UserReadDTO;
import fs19.java.backend.application.mapper.UserMapper;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Tag(name = "Auth", description = "Manage Auth Actions")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

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
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
    AuthResponseDTO authenticateDto = authService.authenticate(request);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), authenticateDto), HttpStatus.OK);

  }
}
