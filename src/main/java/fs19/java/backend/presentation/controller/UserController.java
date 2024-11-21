package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.user.UserCreateDTO;
import fs19.java.backend.application.UserServiceImpl;
import fs19.java.backend.application.dto.user.UserReadDTO;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/users")
public class UserController {

  private final UserServiceImpl userService;

  public UserController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<GlobalResponse<UserReadDTO>> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
    UserReadDTO createdUser = userService.createUser(userCreateDTO);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdUser), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<GlobalResponse<List<UserReadDTO>>> getAllUsers() {
    List<UserReadDTO> users = userService.findAllUsers();
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), users), HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<GlobalResponse<UserReadDTO>> getUserById(@PathVariable UUID userId) {
    UserReadDTO user = userService.findUserById(userId);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), user), HttpStatus.OK);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<GlobalResponse<UserReadDTO>> updateUser(@PathVariable UUID userId, @Valid @RequestBody UserReadDTO userToUpdateDTO) {
    UserReadDTO updatedUser = userService.updateUser(userId, userToUpdateDTO);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedUser), HttpStatus.OK);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<GlobalResponse<Void>> deleteUser(@PathVariable  UUID userId) {
    userService.deleteUser(userId);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null), HttpStatus.NO_CONTENT);
  }
}
