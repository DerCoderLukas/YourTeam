package de.dercoder.yourteam.core.member;

import java.util.UUID;

import com.google.inject.Guice;

import de.dercoder.yourteam.core.TeamModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class MemberRepositoryTest {
  private MemberRepository repository;
  private Member member;

  @BeforeEach
  void initialize() throws Exception {
    var module = TeamModule.create();
    var injector = Guice.createInjector(module);
    var file = injector.getInstance(MemberFile.class);
    repository = MemberRepository.forFile(file);
    member = Member.empty(UUID.randomUUID(), "Test");
  }

  @Test
  void testMemberRegistration() {
    repository.register(member);
    assertEquals(repository.members().size(), 1);
  }

  @Test
  void testMemberFinding() {
    repository.register(member);
    var memberOptional = repository.findById(member.id());
    assertTrue(memberOptional.isPresent());
  }
}
