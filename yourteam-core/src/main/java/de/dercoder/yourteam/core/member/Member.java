package de.dercoder.yourteam.core.member;

import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import de.dercoder.yourteam.core.note.Note;
import de.dercoder.yourteam.core.note.NoteRegistry;

public final class Member {
  private final UUID id;
  private final String name;
  private final NoteRegistry noteRegistry;
  private int points;

  private Member(UUID id, String name, NoteRegistry noteRegistry, int points) {
    this.id = id;
    this.name = name;
    this.noteRegistry = noteRegistry;
    this.points = points;
  }

  public void addPoints(int points) {
    this.points += points;
  }

  public void removePoints(int points) {
    this.points -= points;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public UUID id() {
    return id;
  }

  public String name() {
    return name;
  }

  public NoteRegistry noteRegistry() {
    return noteRegistry;
  }

  public int points() {
    return points;
  }

  public static Member empty(UUID id, String name) {
    Preconditions.checkNotNull(id);
    Preconditions.checkNotNull(name);
    return new Member(id, name, NoteRegistry.empty(), 0);
  }

  public static Member ofModel(MemberModel model) {
    Preconditions.checkNotNull(model);
    var notes = model.getNotes()
      .stream()
      .map(Note::ofModel)
      .collect(Collectors.toUnmodifiableList());
    var noteRegistry = NoteRegistry.of(notes);
    return new Member(model.getId(),
      model.getName(),
      noteRegistry,
      model.getPoints()
    );
  }
}
