package de.dercoder.yourteam.core.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

public final class UserRepository {
  private final UserFile file;
  private final List<User> users;

  private UserRepository(UserFile file, List<User> users) {
    this.file = file;
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

  public void save() throws Exception {
    var userList = parseUsers(users);
    var userFileModel = new UserFileModel(userList);
    file.write(userFileModel);
  }

  private List<UserModel> parseUsers(List<User> users) {
    return users.stream()
      .map(UserModel::fromUser)
      .collect(Collectors.toUnmodifiableList());
  }

  public Optional<User> findByName(String name) {
    Preconditions.checkNotNull(name);
    return users.stream().filter(user -> user.name().equals(name)).findFirst();
  }

  public List<User> users() {
    return List.copyOf(users);
  }

  public static UserRepository forFile(UserFile file) throws Exception {
    Preconditions.checkNotNull(file);
    var users = file.read()
      .getUsers()
      .stream()
      .map(User::ofModel)
      .collect(Collectors.toList());
    return new UserRepository(file, users);
  }
}
