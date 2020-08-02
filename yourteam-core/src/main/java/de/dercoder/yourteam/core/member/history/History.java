package de.dercoder.yourteam.core.member.history;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import de.dercoder.yourteam.core.member.Member;

public final class History {
  private final Map<Member, Long> history;

  private History(Map<Member, Long> history) {
    this.history = history;
  }

  public void add(Member member, long timeMillis) {
    Preconditions.checkNotNull(member);
    history.put(member, timeMillis);
  }

  public Map<Member, Long> history() {
    return Map.copyOf(history);
  }

  public static History empty() {
    return new History(Maps.newHashMap());
  }

  public static History of(Map<Member, Long> history) {
    Preconditions.checkNotNull(history);
    return new History(Map.copyOf(history));
  }
}
