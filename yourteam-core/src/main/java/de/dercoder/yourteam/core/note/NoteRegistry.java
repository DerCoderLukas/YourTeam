package de.dercoder.yourteam.core.note;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class NoteRegistry {
  private final List<Note> notes;

  private NoteRegistry(List<Note> notes) {
    this.notes = notes;
  }

  public void register(Note note) {
    Preconditions.checkNotNull(note);
    notes.add(note);
  }

  public void unregister(Note note) {
    Preconditions.checkNotNull(note);
    notes.remove(note);
  }

  public List<Note> notes() {
    return List.copyOf(notes);
  }

  public static NoteRegistry empty() {
    return new NoteRegistry(Lists.newArrayList());
  }

  public static NoteRegistry of(List<Note> notes) {
    Preconditions.checkNotNull(notes);
    return new NoteRegistry(List.copyOf(notes));
  }
}
