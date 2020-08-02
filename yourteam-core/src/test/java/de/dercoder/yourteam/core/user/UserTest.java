package de.dercoder.yourteam.core.user;

import org.junit.jupiter.api.BeforeEach;

final class UserTest {
  private User user;

  @BeforeEach
  void initialize() {
    user = User.create("Test", "123");
  }
}
