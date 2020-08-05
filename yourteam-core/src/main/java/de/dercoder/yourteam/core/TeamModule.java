package de.dercoder.yourteam.core;


import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import de.dercoder.yourteam.core.group.GroupFile;
import de.dercoder.yourteam.core.group.GroupRepository;
import de.dercoder.yourteam.core.member.MemberFile;
import de.dercoder.yourteam.core.member.MemberRepository;
import de.dercoder.yourteam.core.member.history.HistoryFile;
import de.dercoder.yourteam.core.member.history.HistoryRepository;
import de.dercoder.yourteam.core.user.UserFile;
import de.dercoder.yourteam.core.user.UserRepository;
import javax.inject.Singleton;

public class TeamModule extends AbstractModule {
  private final Path userFilePath;
  private final Path memberFilePath;
  private final Path historyFilePath;
  private final Path groupFilePath;

  protected TeamModule(
    Path userFilePath,
    Path memberFilePath,
    Path historyFilePath,
    Path groupFilePath
  ) {
    this.userFilePath = userFilePath;
    this.memberFilePath = memberFilePath;
    this.historyFilePath = historyFilePath;
    this.groupFilePath = groupFilePath;
  }

  @Provides
  @Singleton
  @Named("userFilePath")
  Path provideUserFilePath() {
    return userFilePath;
  }

  @Provides
  @Singleton
  UserRepository provideUserRepository(Injector injector) throws Exception {
    var fileInstance = injector.getInstance(UserFile.class);
    return UserRepository.forFile(fileInstance);
  }

  @Provides
  @Singleton
  @Named("memberFilePath")
  Path provideMemberFilePath() {
    return memberFilePath;
  }

  @Provides
  @Singleton
  MemberRepository provideMemberRepository(Injector injector) throws Exception {
    var fileInstance = injector.getInstance(MemberFile.class);
    return MemberRepository.forFile(fileInstance);
  }

  @Provides
  @Singleton
  @Named("historyFilePath")
  Path provideHistoryFilePath() {
    return historyFilePath;
  }

  @Provides
  @Singleton
  HistoryRepository provideHistoryRepository(
    Injector injector,
    MemberRepository memberRepository
  ) throws Exception {
    var fileInstance = injector.getInstance(HistoryFile.class);
    return HistoryRepository.forFile(fileInstance, memberRepository);
  }

  @Provides
  @Singleton
  @Named("groupFilePath")
  Path provideGroupFilePath() {
    return groupFilePath;
  }

  @Provides
  @Singleton
  GroupRepository provideGroupRepository(
    Injector injector,
    MemberRepository memberRepository
  ) throws Exception {
    var fileInstance = injector.getInstance(GroupFile.class);
    return GroupRepository.forFile(fileInstance, memberRepository);
  }

  public static TeamModule create() {
    var applicationPath = Paths.get("").toAbsolutePath().toString();
    var userFilePath = Path.of(applicationPath + "/user/users.yml");
    var memberFilePath = Path.of(applicationPath + "/member/members.yml");
    var historyFilePath = Path.of(applicationPath + "/member/history.yml");
    var groupFilePath = Path.of(applicationPath + "/group/groups.yml");
    return new TeamModule(
      userFilePath,
      memberFilePath,
      historyFilePath,
      groupFilePath
    );
  }
}
