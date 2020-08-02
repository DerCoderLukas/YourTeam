package de.dercoder.yourteam.core.member;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class MemberRegistry {
  private final List<Member> members;

  private MemberRegistry(List<Member> members) {
    this.members = members;
  }

  public void register(Member member) {
    Preconditions.checkNotNull(member);
    members.add(member);
  }

  public void unregister(Member member) {
    Preconditions.checkNotNull(member);
    members.remove(member);
  }

  public List<Member> members() {
    return List.copyOf(members);
  }

  public static MemberRegistry empty() {
    return new MemberRegistry(Lists.newArrayList());
  }

  public static MemberRegistry of(List<Member> members) {
    Preconditions.checkNotNull(members);
    return new MemberRegistry(List.copyOf(members));
  }
}
