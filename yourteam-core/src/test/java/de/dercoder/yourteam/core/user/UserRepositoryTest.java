package de.dercoder.yourteam.core.user;

import com.google.inject.Guice;

import de.dercoder.yourteam.core.TeamModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class UserRepositoryTest {
  private UserRepository repository;
  private User user;

  @BeforeEach
  void initialize() throws Exception {
    var module = TeamModule.create();
    var injector = Guice.createInjector(module);
    var file = injector.getInstance(UserFile.class);
    repository = UserRepository.forFile(file);
    user = User.create("Test", "123");
  }

  @Test
  void testUserRegistration() {
    repository.register(user);
    assertEquals(repository.users().size(), 2);
  }

  @Test
  void testUserFinding() {
    repository.register(user);
    var userOptional = repository.findByName(user.name());
    assertTrue(userOptional.isPresent());
  }
}
