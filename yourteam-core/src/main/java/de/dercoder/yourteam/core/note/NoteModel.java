package de.dercoder.yourteam.core.note;

import com.google.common.base.Preconditions;

public final class NoteModel {
  private String title;
  private String description;

  NoteModel() {

  }

  NoteModel(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public static NoteModel fromNote(Note note) {
    Preconditions.checkNotNull(note);
    return new NoteModel(note.title(), note.description());
  }
}
