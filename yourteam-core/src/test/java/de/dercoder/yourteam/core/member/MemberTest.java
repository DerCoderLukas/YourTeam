package de.dercoder.yourteam.core.member;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class MemberTest {
  private Member member;

  @BeforeEach
  void initialize() {
    member = Member.empty(UUID.randomUUID(), "Test");
  }

  @Test
  void testPointAdding() {
    member.addPoints(10);
    assertEquals(10, member.points());
  }

  @Test
  void testPointRemoving() {
    member.removePoints(10);
    assertEquals(-10, member.points());
  }

  @Test
  void testPointSetting() {
    member.setPoints(10);
    assertEquals(10, member.points());
  }
}
