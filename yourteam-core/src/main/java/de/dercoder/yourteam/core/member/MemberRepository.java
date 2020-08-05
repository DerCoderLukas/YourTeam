package de.dercoder.yourteam.core.member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import com.google.inject.Singleton;

@Singleton
public final class MemberRepository {
  private final MemberFile file;
  private final List<Member> members;

  private MemberRepository(MemberFile file, List<Member> members) {
    this.file = file;
    this.members = members;
  }

  public void register(Member member) {
    Preconditions.checkNotNull(member);
    members.add(member);
  }

  public void unregister(Member member) {
    Preconditions.checkNotNull(member);
    members.remove(member);
  }

  public void save() throws Exception {
    var memberList = parseMembers(members);
    var memberFileModel = new MemberFileModel(memberList);
    file.write(memberFileModel);
  }

  private List<MemberModel> parseMembers(List<Member> members) {
    return members.stream()
      .map(MemberModel::fromMember)
      .collect(Collectors.toUnmodifiableList());
  }

  public Optional<Member> findById(UUID id) {
    Preconditions.checkNotNull(id);
    return members.stream()
      .filter(member -> member.id().equals(id))
      .findFirst();
  }

  public Optional<Member> findById(String id) {
    Preconditions.checkNotNull(id);
    return members.stream()
      .filter(member -> member.id().toString().equals(id))
      .findFirst();
  }

  public List<Member> members() {
    return List.copyOf(members);
  }

  public static MemberRepository forFile(MemberFile file) throws Exception {
    Preconditions.checkNotNull(file);
    var members = file.read()
      .getMembers()
      .stream()
      .map(Member::ofModel)
      .collect(Collectors.toList());
    return new MemberRepository(file, members);
  }
}
