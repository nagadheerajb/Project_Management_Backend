package fs19.java.backend.domain.abstraction;

import fs19.java.backend.domain.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserRepository {
  User saveUser(User user);
  List<User> findAllUsers();
  Optional<User> findById(UUID id);
  void deleteUser(User user);
}
