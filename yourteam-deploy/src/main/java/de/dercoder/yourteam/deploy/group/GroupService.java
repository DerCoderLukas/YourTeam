package de.dercoder.yourteam.deploy.group;

import com.google.common.base.Preconditions;

import de.dercoder.yourteam.core.group.Group;
import de.dercoder.yourteam.core.group.GroupRepository;
import de.dercoder.yourteam.deploy.user.UserService;
import javax.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public final class GroupService {
  private final GroupRepository repository;
  private final UserService userService;

  private GroupService(
    GroupRepository repository, UserService userService
  ) {
    this.repository = repository;
    this.userService = userService;
  }

  public Group findGroupByName(String name) throws Exception {
    Preconditions.checkNotNull(name);
    return repository.findByName(name)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Could not find Group"
      ));
  }

  public GroupRepository groupRepository() {
    return repository;
  }

  public UserService userService() {
    return userService;
  }

  @PostConstruct
  public void init() {
    Runtime.getRuntime().addShutdownHook(new Thread(this::saveRepository));
  }

  private void saveRepository() {
    try {
      repository.save();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
