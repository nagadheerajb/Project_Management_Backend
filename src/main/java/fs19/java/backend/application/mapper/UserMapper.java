package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.auth.AuthResponseDTO;
import fs19.java.backend.application.dto.user.UserCreateDTO;
import fs19.java.backend.application.dto.user.UserReadDTO;
import fs19.java.backend.domain.entity.User;

import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@UtilityClass
public class UserMapper {

  public static User toEntity(UserCreateDTO dto,String password) {
    User user = new User();
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setEmail(dto.getEmail());
    user.setPassword(password);
    user.setPhone(dto.getPhone());
    user.setProfileImage(dto.getProfileImage());
    return user;
  }

  public static UserReadDTO toReadDTO(User user) {
    UserReadDTO userReadDto = new UserReadDTO();
    userReadDto.setId(user.getId());
    userReadDto.setFirstName(user.getFirstName());
    userReadDto.setLastName(user.getLastName());
    userReadDto.setEmail(user.getEmail());
    userReadDto.setPhone(user.getPhone());
    userReadDto.setCreatedDate(user.getCreatedDate());
    userReadDto.setProfileImage(user.getProfileImage());
    return userReadDto;
  }

  public static AuthResponseDTO toAuthResponseDTO(User user, String token) {
    AuthResponseDTO userReadDto = new AuthResponseDTO();
    userReadDto.setId(user.getId());
    userReadDto.setFirstName(user.getFirstName());
    userReadDto.setLastName(user.getLastName());
    userReadDto.setEmail(user.getEmail());
    userReadDto.setPhone(user.getPhone());
    userReadDto.setCreatedDate(user.getCreatedDate());
    userReadDto.setProfileImage(user.getProfileImage());
    userReadDto.setAccessToken(token);
    return userReadDto;
  }
}
