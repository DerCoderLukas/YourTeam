package de.dercoder.yourteam.core.user;

import com.google.common.base.Preconditions;

public final class User {
  private final String name;
  private final String password;

  private User(String name, String password) {
    this.name = name;
    this.password = password;
  }

  public String name() {
    return name;
  }

  public String password() {
    return password;
  }

  public static User create(String name, String password) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(password);
    return new User(name, password);
  }

  public static User ofModel(UserModel model) {
    Preconditions.checkNotNull(model);
    return new User(model.getName(), model.getPassword());
  }
}
