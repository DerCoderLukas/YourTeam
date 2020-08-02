package de.dercoder.yourteam.core.note;

import com.google.common.base.Preconditions;

public final class Note {
  private final String title;
  private final String description;

  private Note(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public String title() {
    return title;
  }

  public String description() {
    return description;
  }

  public static Note create(String title, String description) {
    Preconditions.checkNotNull(title);
    Preconditions.checkNotNull(description);
    return new Note(title, description);
  }

  public static Note ofModel(NoteModel model) {
    Preconditions.checkNotNull(model);
    return new Note(model.getTitle(), model.getDescription());
  }
}
