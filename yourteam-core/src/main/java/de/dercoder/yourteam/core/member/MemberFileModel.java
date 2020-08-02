package de.dercoder.yourteam.core.member;

import java.util.List;

public final class MemberFileModel {
  private List<MemberModel> members;

  MemberFileModel() {

  }

  MemberFileModel(List<MemberModel> members) {
    this.members = members;
  }

  public void setMembers(List<MemberModel> members) {
    this.members = members;
  }

  public List<MemberModel> getMembers() {
    return members;
  }
}
