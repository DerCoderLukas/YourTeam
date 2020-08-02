package de.dercoder.yourteam.core.member.history;

import com.google.common.base.Preconditions;

import de.dercoder.yourteam.core.member.MemberRepository;

public final class HistoryRepositoryFactory {
  private final MemberRepository memberRepository;

  private HistoryRepositoryFactory(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public HistoryRepository forFile(HistoryFile file) throws Exception {
    Preconditions.checkNotNull(file);
    return HistoryRepository.forFile(file, memberRepository);
  }

  public static HistoryRepositoryFactory create(MemberRepository memberRepository) {
    Preconditions.checkNotNull(memberRepository);
    return new HistoryRepositoryFactory(memberRepository);
  }
}
