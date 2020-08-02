package de.dercoder.yourteam.core.user;

import java.util.List;

public final class UserFileModel {
  private List<UserModel> users;

  UserFileModel() {

  }

  UserFileModel(List<UserModel> users) {
    this.users = users;
  }

  public void setUsers(List<UserModel> users) {
    this.users = users;
  }

  public List<UserModel> getUsers() {
    return users;
  }
}
