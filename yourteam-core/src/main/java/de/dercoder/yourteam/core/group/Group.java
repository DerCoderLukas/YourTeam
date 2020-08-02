package de.dercoder.yourteam.core.group;

import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import de.dercoder.yourteam.core.member.MemberRegistry;
import de.dercoder.yourteam.core.member.MemberRepository;
import de.dercoder.yourteam.core.note.Note;
import de.dercoder.yourteam.core.note.NoteRegistry;

public final class Group {
  private final String name;
  private final MemberRegistry memberRegistry;
  private final GroupRegistry subgroupRegistry;
  private final NoteRegistry noteRegistry;

  private Group(
    String name,
    MemberRegistry memberRegistry,
    GroupRegistry subgroupRegistry,
    NoteRegistry noteRegistry
  ) {
    this.name = name;
    this.memberRegistry = memberRegistry;
    this.subgroupRegistry = subgroupRegistry;
    this.noteRegistry = noteRegistry;
  }

  public String name() {
    return name;
  }

  public MemberRegistry memberRegistry() {
    return memberRegistry;
  }

  public GroupRegistry subgroupRegistry() {
    return subgroupRegistry;
  }

  public NoteRegistry noteRegistry() {
    return noteRegistry;
  }

  public static Group empty(String name) {
    Preconditions.checkNotNull(name);
    return new Group(name,
      MemberRegistry.empty(),
      GroupRegistry.empty(),
      NoteRegistry.empty()
    );
  }

  public static Group ofModel(
    GroupModel model, MemberRepository memberRepository
  ) {
    Preconditions.checkNotNull(model);
    Preconditions.checkNotNull(memberRepository);
    var memberRegistry = extractMembers(model, memberRepository);
    var subgroupRegistry = extractSubgroups(model, memberRepository);
    var noteRegistry = extractNotes(model);
    return new Group(model.getName(),
      memberRegistry,
      subgroupRegistry,
      noteRegistry
    );
  }

  private static MemberRegistry extractMembers(
    GroupModel model, MemberRepository memberRepository
  ) {
    var subgroups = model.getMembers()
      .stream()
      .map(memberRepository::findById)
      .flatMap(Optional::stream)
      .collect(Collectors.toUnmodifiableList());
    return MemberRegistry.of(subgroups);
  }

  private static GroupRegistry extractSubgroups(
    GroupModel model, MemberRepository memberRepository
  ) {
    var subgroups = model.getSubgroups()
      .stream()
      .map(groupModel -> ofModel(model, memberRepository))
      .collect(Collectors.toUnmodifiableList());
    return GroupRegistry.of(subgroups);
  }

  private static NoteRegistry extractNotes(GroupModel model) {
    var notes = model.getNotes()
      .stream()
      .map(Note::ofModel)
      .collect(Collectors.toUnmodifiableList());
    return NoteRegistry.of(notes);
  }
}
