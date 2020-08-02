package de.dercoder.yourteam.deploy.member;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.base.Preconditions;

import de.dercoder.yourteam.core.member.Member;
import de.dercoder.yourteam.core.member.MemberModel;
import de.dercoder.yourteam.core.note.Note;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public final class MemberController {
  private final MemberService service;

  private MemberController(MemberService service) {
    this.service = service;
  }

  @RequestMapping(path = "/member/register", method = RequestMethod.POST)
  public void registerMember(
    @RequestBody Map<String, Object> input
  ) throws Exception {
    Preconditions.checkNotNull(input);
    service.userService().checkAuthority(input);
    var inputId = (String) input.get("memberId");
    var id = UUID.fromString(inputId);
    var name = (String) input.get("memberName");
    var member = Member.empty(id, name);
    service.memberRepository().register(member);
  }

  @RequestMapping(path = "/member/find", method = RequestMethod.POST)
  public MemberModel findMember(
    @RequestBody Map<String, Object> input
  ) throws Exception {
    Preconditions.checkNotNull(input);
    service.userService().checkAuthority(input);
    var member = service.findMember(input);
    return MemberModel.fromMember(member);
  }

  @RequestMapping(path = "/member/points/adjust", method = RequestMethod.POST)
  public void adjustMemberPoints(
    @RequestBody Map<String, Object> input
  ) throws Exception {
    Preconditions.checkNotNull(input);
    service.userService().checkAuthority(input);
    var member = service.findMember(input);
    var pointMethod = (String) input.get("pointMethod");
    var points = (int) input.get("points");
    service.handlePoints(member, pointMethod, points);
  }

  @RequestMapping(path = "/member/points/obtain", method = RequestMethod.POST)
  public int obtainMemberPoints(
    @RequestBody Map<String, Object> input
  ) throws Exception {
    Preconditions.checkNotNull(input);
    service.userService().checkAuthority(input);
    var member = service.findMember(input);
    return member.points();
  }

  @RequestMapping(path = "/member/notes/adjust", method = RequestMethod.POST)
  public void adjustMemberNotes(
    @RequestBody Map<String, Object> input
  ) throws Exception {
    Preconditions.checkNotNull(input);
    service.userService().checkAuthority(input);
    var member = service.findMember(input);
    var noteTitle = (String) input.get("noteTitle");
    var noteDescription = (String) input.get("noteDescription");
    var note = Note.create(noteTitle, noteDescription);
    member.noteRegistry().register(note);
  }

  @RequestMapping(path = "/member/notes/obtain", method = RequestMethod.POST)
  public List<Note> obtainMemberNotes(
    @RequestBody Map<String, Object> input
  ) throws Exception {
    Preconditions.checkNotNull(input);
    service.userService().checkAuthority(input);
    var member = service.findMember(input);
    return member.noteRegistry().notes();
  }

  @ExceptionHandler(value = ResponseStatusException.class)
  public ResponseEntity<Object> exception(ResponseStatusException exception) {
    Preconditions.checkNotNull(exception);
    return new ResponseEntity<>(exception.getReason(), exception.getStatus());
  }
}
