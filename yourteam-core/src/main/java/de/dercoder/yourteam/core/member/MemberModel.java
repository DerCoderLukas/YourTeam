package de.dercoder.yourteam.core.member;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import de.dercoder.yourteam.core.note.Note;
import de.dercoder.yourteam.core.note.NoteModel;

public final class MemberModel {
  private UUID id;
  private String name;
  private List<NoteModel> notes;
  private int points;

  MemberModel() {

  }

  MemberModel(UUID id, String name, List<NoteModel> notes, int points) {
    this.id = id;
    this.name = name;
    this.notes = notes;
    this.points = points;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setNotes(List<NoteModel> notes) {
    this.notes = notes;
  }

  public List<NoteModel> getNotes() {
    return notes;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public int getPoints() {
    return points;
  }

  public static MemberModel fromMember(Member member) {
    Preconditions.checkNotNull(member);
    var notes = parseNotes(member.noteRegistry().notes());
    return new MemberModel(member.id(), member.name(), notes, member.points());
  }

  private static List<NoteModel> parseNotes(List<Note> notes) {
    return notes.stream()
      .map(NoteModel::fromNote)
      .collect(Collectors.toUnmodifiableList());
  }
}
