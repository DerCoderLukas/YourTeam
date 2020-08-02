package de.dercoder.yourteam.core.group;

import com.google.common.base.Preconditions;

import de.dercoder.yourteam.core.member.MemberRepository;

public final class GroupRepositoryFactory {
  private final MemberRepository memberRepository;

  private GroupRepositoryFactory(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public GroupRepository forFile(GroupFile file) throws Exception {
    Preconditions.checkNotNull(file);
    return GroupRepository.forFile(file, memberRepository);
  }

  public static GroupRepositoryFactory create(MemberRepository memberRepository) {
    Preconditions.checkNotNull(memberRepository);
    return new GroupRepositoryFactory(memberRepository);
  }
}
