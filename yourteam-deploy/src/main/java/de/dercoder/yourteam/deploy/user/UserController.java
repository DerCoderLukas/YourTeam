package de.dercoder.yourteam.deploy.user;

import java.util.Map;

import com.google.common.base.Preconditions;

import de.dercoder.yourteam.core.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public final class UserController {
  private final UserService service;

  private UserController(UserService service) {
    this.service = service;
  }

  @RequestMapping(path = "/user/login", method = RequestMethod.POST)
  public Map<String, Object> loginUser(
    @RequestBody Map<String, Object> input
  ) {
    Preconditions.checkNotNull(input);
    var name = (String) input.get("userName");
    var password = (String) input.get("userPassword");
    return service.loginUser(name, password);
  }

  @RequestMapping(path = "/user/register", method = RequestMethod.POST)
  public void registerUser(
    @RequestBody Map<String, Object> input
  ) throws Exception {
    Preconditions.checkNotNull(input);
    service.checkAuthority(input);
    var name = (String) input.get("userName");
    var password = (String) input.get("userPassword");
    var user = User.create(name, password);
    service.userRepository().register(user);
  }

  @ExceptionHandler(value = ResponseStatusException.class)
  public ResponseEntity<Object> exception(ResponseStatusException exception) {
    Preconditions.checkNotNull(exception);
    return new ResponseEntity<>(exception.getReason(), exception.getStatus());
  }
}
