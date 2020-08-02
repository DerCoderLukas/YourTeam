package de.dercoder.yourteam.core;


import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

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
  @Named("memberFilePath")
  Path provideMemberFilePath() {
    return memberFilePath;
  }

  @Provides
  @Singleton
  @Named("historyFilePath")
  Path provideHistoryFilePath() {
    return historyFilePath;
  }

  @Provides
  @Singleton
  @Named("groupFilePath")
  Path provideGroupFilePath() {
    return groupFilePath;
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
