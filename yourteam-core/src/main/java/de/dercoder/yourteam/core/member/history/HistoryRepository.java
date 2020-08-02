package de.dercoder.yourteam.core.member.history;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import de.dercoder.yourteam.core.member.Member;
import de.dercoder.yourteam.core.member.MemberRepository;

public final class HistoryRepository {
  private final History history;

  private HistoryRepository(History history) {
    this.history = history;
  }

  public History history() {
    return history;
  }

  public static HistoryRepository forFile(
    HistoryFile file, MemberRepository memberRepository
  ) throws Exception {
    Preconditions.checkNotNull(file);
    Preconditions.checkNotNull(memberRepository);
    var historyMap = extractMemberMap(file, memberRepository);
    var history = History.of(historyMap);
    return new HistoryRepository(history);
  }

  private static Map<Member, Long> extractMemberMap(
    HistoryFile file, MemberRepository memberRepository
  ) throws Exception {
    var modelHistoryMap = file.read().getHistory();
    return modelHistoryMap.keySet()
      .stream()
      .map(memberRepository::findById)
      .flatMap(Optional::stream)
      .collect(Collectors.toUnmodifiableMap(member -> member,
        member -> modelHistoryMap.get(member.id())
      ));
  }
}
