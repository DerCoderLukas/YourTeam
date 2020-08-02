package de.dercoder.yourteam.deploy.group;

import java.util.Map;

import com.google.common.base.Preconditions;

import de.dercoder.yourteam.core.group.Group;
import de.dercoder.yourteam.core.group.GroupModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public final class GroupController {
  private final GroupService service;

  private GroupController(GroupService service) {
    this.service = service;
  }

  @RequestMapping(path = "/group/register", method = RequestMethod.POST)
  public void registerGroup(
    @RequestBody Map<String, Object> input
  ) throws Exception {
    Preconditions.checkNotNull(input);
    service.userService().checkAuthority(input);
    var groupName = (String) input.get("groupName");
    var group = Group.empty(groupName);
    service.groupRepository().register(group);
  }

  @RequestMapping(path = "/group/find", method = RequestMethod.POST)
  public GroupModel findGroup(
    @RequestBody Map<String, Object> input
  ) throws Exception {
    Preconditions.checkNotNull(input);
    service.userService().checkAuthority(input);
    var groupName = (String) input.get("groupName");
    var group = service.findGroupByName(groupName);
    return GroupModel.fromGroup(group);
  }

  @ExceptionHandler(value = ResponseStatusException.class)
  public ResponseEntity<Object> exception(ResponseStatusException exception) {
    Preconditions.checkNotNull(exception);
    return new ResponseEntity<>(exception.getReason(), exception.getStatus());
  }
}
