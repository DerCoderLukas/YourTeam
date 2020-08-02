package de.dercoder.yourteam.core.group;

import java.util.List;

public final class GroupFileModel {
  private List<GroupModel> groups;

  GroupFileModel() {

  }

  GroupFileModel(List<GroupModel> groups) {
    this.groups = groups;
  }

  public void setGroups(List<GroupModel> groups) {
    this.groups = groups;
  }

  public List<GroupModel> getGroups() {
    return groups;
  }
}
