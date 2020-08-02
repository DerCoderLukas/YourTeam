package de.dercoder.yourteam.core.group;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import de.dercoder.yourteam.core.member.MemberRepository;

public final class GroupRepository {
  private final GroupFile file;
  private final List<Group> groups;

  private GroupRepository(GroupFile file, List<Group> groups) {
    this.file = file;
    this.groups = groups;
  }

  public void register(Group group) {
    Preconditions.checkNotNull(group);
    groups.add(group);
  }

  public void unregister(Group group) {
    Preconditions.checkNotNull(group);
    groups.remove(group);
  }

  public void save() throws Exception {
    var groupList = parseGroups(groups);
    var groupFileModel = new GroupFileModel(groupList);
    file.write(groupFileModel);
  }

  private List<GroupModel> parseGroups(List<Group> groups) {
    return groups.stream()
      .map(GroupModel::fromGroup)
      .collect(Collectors.toUnmodifiableList());
  }

  public Optional<Group> findByName(String name) {
    Preconditions.checkNotNull(name);
    return groups.stream()
      .filter(group -> group.name().equals(name))
      .findFirst();
  }

  public List<Group> groups() {
    return List.copyOf(groups);
  }

  public static GroupRepository forFile(
    GroupFile file, MemberRepository memberRepository
  ) throws Exception {
    Preconditions.checkNotNull(file);
    Preconditions.checkNotNull(memberRepository);
    var groups = file.read()
      .getGroups()
      .stream()
      .map(model -> Group.ofModel(model, memberRepository))
      .collect(Collectors.toList());
    return new GroupRepository(file, groups);
  }
}
