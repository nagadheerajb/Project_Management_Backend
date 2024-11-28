package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.abstraction.UserRepository;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import java.util.List;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoImpl implements UserRepository {

  private final UserJpaRepo userRepository;

  public UserRepoImpl(UserJpaRepo userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User saveUser(User user) {
    userRepository.save(user);
    return userRepository.save(user);
  }

  @Override
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public void deleteUser(User user) {
    userRepository.delete(user);
  }

  @Override
  public Optional<User> findById(UUID id) {
    return Optional.ofNullable(userRepository.findById(id).orElse(null));
  }
}