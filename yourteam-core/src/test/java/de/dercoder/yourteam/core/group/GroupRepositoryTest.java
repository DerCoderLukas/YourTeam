package de.dercoder.yourteam.core.group;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.dercoder.yourteam.core.TeamModule;
import de.dercoder.yourteam.core.member.MemberFile;
import de.dercoder.yourteam.core.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GroupRepositoryTest {
  private GroupRepository repository;
  private Group group;

  @BeforeEach
  void initialize() throws Exception {
    var module = TeamModule.create();
    var injector = Guice.createInjector(module);
    var memberRepository = initializeMemberRepository(injector);
    var file = injector.getInstance(GroupFile.class);
    var repositoryFactory = GroupRepositoryFactory.create(memberRepository);
    repository = repositoryFactory.forFile(file);
    group = Group.empty("Test");
  }

  MemberRepository initializeMemberRepository(Injector injector) throws Exception {
    var file = injector.getInstance(MemberFile.class);
    return MemberRepository.forFile(file);
  }

  @Test
  void testGroupRegistration() {
    repository.register(group);
    assertEquals(repository.groups().size(), 1);
  }

  @Test
  void testGroupFinding() {
    repository.register(group);
    var groupOptional = repository.findByName(group.name());
    assertTrue(groupOptional.isPresent());
  }
}
