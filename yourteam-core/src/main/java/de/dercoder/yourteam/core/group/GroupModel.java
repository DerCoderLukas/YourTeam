package de.dercoder.yourteam.core.group;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import de.dercoder.yourteam.core.member.Member;
import de.dercoder.yourteam.core.note.Note;
import de.dercoder.yourteam.core.note.NoteModel;

public final class GroupModel {
  private String name;
  private List<UUID> members;
  private List<GroupModel> subgroups;
  private List<NoteModel> notes;

  GroupModel() {

  }

  GroupModel(
    String name,
    List<UUID> members,
    List<GroupModel> subgroups,
    List<NoteModel> notes
  ) {
    this.name = name;
    this.members = members;
    this.subgroups = subgroups;
    this.notes = notes;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setMembers(List<UUID> members) {
    this.members = members;
  }

  public List<UUID> getMembers() {
    return members;
  }

  public void setSubgroups(List<GroupModel> subgroups) {
    this.subgroups = subgroups;
  }

  public List<GroupModel> getSubgroups() {
    return subgroups;
  }

  public void setNotes(List<NoteModel> notes) {
    this.notes = notes;
  }

  public List<NoteModel> getNotes() {
    return notes;
  }

  public static GroupModel fromGroup(Group group) {
    Preconditions.checkNotNull(group);
    var members = parseMembers(group.memberRegistry().members());
    var subgroups = parseSubgroups(group.subgroupRegistry().groups());
    var notes = parseNotes(group.noteRegistry().notes());
    return new GroupModel(group.name(), members, subgroups, notes);
  }

  private static List<UUID> parseMembers(List<Member> members) {
    return members.stream()
      .map(Member::id)
      .collect(Collectors.toUnmodifiableList());
  }

  private static List<GroupModel> parseSubgroups(List<Group> subgroups) {
    return subgroups.stream()
      .map(GroupModel::fromGroup)
      .collect(Collectors.toUnmodifiableList());
  }

  private static List<NoteModel> parseNotes(List<Note> notes) {
    return notes.stream()
      .map(NoteModel::fromNote)
      .collect(Collectors.toUnmodifiableList());
  }
}
