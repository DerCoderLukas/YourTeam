package de.dercoder.yourteam.core.group;

import org.junit.jupiter.api.BeforeEach;

final class GroupTest {
  private Group group;

  @BeforeEach
  void initialize() {
    group = Group.empty("Test");
  }

}
