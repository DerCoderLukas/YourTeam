package de.dercoder.yourteam.core.member.history;

import java.util.Map;
import java.util.UUID;

public final class HistoryFileModel {
  private Map<UUID, Long> history;

  HistoryFileModel() {

  }

  HistoryFileModel(Map<UUID, Long> history) {
    this.history = history;
  }

  public void setHistory(Map<UUID, Long> history) {
    this.history = history;
  }

  public Map<UUID, Long> getHistory() {
    return history;
  }
}
