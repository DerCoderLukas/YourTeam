package de.dercoder.yourteam.core.user;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class UserRegistry {
  private final List<User> users;

  private UserRegistry(List<User> users) {
    this.users = users;
  }

  public void register(User user) {
    Preconditions.checkNotNull(user);
    users.add(user);
  }

  public void unregister(User user) {
    Preconditions.checkNotNull(user);
    users.remove(user);
  }

  public List<User> users() {
    return List.copyOf(users);
  }

  public static UserRegistry empty() {
    return new UserRegistry(Lists.newArrayList());
  }

  public static UserRegistry of(List<User> users) {
    Preconditions.checkNotNull(users);
    return new UserRegistry(List.copyOf(users));
  }
}
