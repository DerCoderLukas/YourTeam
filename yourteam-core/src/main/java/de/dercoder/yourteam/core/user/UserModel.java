package de.dercoder.yourteam.core.user;

import com.google.common.base.Preconditions;

public final class UserModel {
  private String name;
  private String password;

  UserModel() {

  }

  UserModel(String name, String password) {
    this.name = name;
    this.password = password;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public static UserModel fromUser(User user) {
    Preconditions.checkNotNull(user);
    return new UserModel(user.name(), user.password());
  }
}
