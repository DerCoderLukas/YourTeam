package de.dercoder.yourteam.core.group;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class GroupRegistry {
  private final List<Group> groups;

  private GroupRegistry(List<Group> groups) {
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

  public List<Group> groups() {
    return List.copyOf(groups);
  }

  public static GroupRegistry empty() {
    return new GroupRegistry(Lists.newArrayList());
  }

  public static GroupRegistry of(List<Group> groups) {
    Preconditions.checkNotNull(groups);
    return new GroupRegistry(List.copyOf(groups));
  }
}
