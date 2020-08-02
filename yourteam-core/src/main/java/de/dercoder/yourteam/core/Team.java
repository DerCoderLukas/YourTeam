package de.dercoder.yourteam.core;

import de.dercoder.yourteam.core.group.GroupRegistry;
import de.dercoder.yourteam.core.member.MemberRegistry;
import de.dercoder.yourteam.core.member.history.History;
import de.dercoder.yourteam.core.user.UserRegistry;

public final class Team {
  private final UserRegistry userRegistry;
  private final MemberRegistry memberRegistry;
  private final GroupRegistry groupRegistry;
  private final History memberHistory;

  private Team(
    UserRegistry userRegistry,
    MemberRegistry memberRegistry,
    GroupRegistry groupRegistry,
    History memberHistory
  ) {
    this.userRegistry = userRegistry;
    this.memberRegistry = memberRegistry;
    this.groupRegistry = groupRegistry;
    this.memberHistory = memberHistory;
  }

  public static Team empty() {
    return new Team(
      UserRegistry.empty(),
      MemberRegistry.empty(),
      GroupRegistry.empty(),
      History.empty()
    );
  }
}
