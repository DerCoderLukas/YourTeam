package de.dercoder.yourteam.deploy.member;

import java.util.Map;

import com.google.common.base.Preconditions;

import de.dercoder.yourteam.core.member.Member;
import de.dercoder.yourteam.core.member.MemberRepository;
import de.dercoder.yourteam.deploy.user.UserService;
import javax.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public final class MemberService {
  private final MemberRepository repository;
  private final UserService userService;

  private MemberService(
    MemberRepository repository, UserService userService
  ) {
    this.repository = repository;
    this.userService = userService;
  }

  public Member findMember(Map<String, Object> input) throws Exception {
    Preconditions.checkNotNull(input);
    var id = (String) input.get("memberId");
    return findMemberById(id);
  }

  private Member findMemberById(String id) throws Exception {
    return repository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Could not find Member"
      ));
  }

  public void handlePoints(Member member, String pointsMethod, int points) {
    Preconditions.checkNotNull(member);
    Preconditions.checkNotNull(pointsMethod);
    switch (pointsMethod) {
      case "add" -> member.addPoints(points);
      case "remove" -> member.removePoints(points);
      case "set" -> member.setPoints(points);
    }
  }

  public MemberRepository memberRepository() {
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
