package de.dercoder.yourteam.deploy;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.dercoder.yourteam.core.group.GroupFile;
import de.dercoder.yourteam.core.group.GroupRepository;
import de.dercoder.yourteam.core.member.MemberFile;
import de.dercoder.yourteam.core.member.MemberRepository;
import de.dercoder.yourteam.core.user.UserFile;
import de.dercoder.yourteam.core.user.UserRepository;
import de.dercoder.yourteam.deploy.configuration.ConfigurationFile;
import de.dercoder.yourteam.deploy.configuration.ConfigurationRepository;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamDeployApplicationConfiguration {
  private Injector injector;
  private ConfigurationRepository configurationRepository;
  private UserRepository userRepository;
  private MemberRepository memberRepository;
  private GroupRepository groupRepository;

  @Bean
  public ConfigurationRepository configurationRepository() {
    return configurationRepository;
  }

  @Bean
  public UserRepository userRepository() {
    return userRepository;
  }

  @Bean
  public MemberRepository memberRepository() {
    return memberRepository;
  }

  @Bean
  public GroupRepository groupRepository() {
    return groupRepository;
  }

  @PostConstruct
  private void createModuleInjector() throws Exception {
    var module = TeamDeployModule.create();
    injector = Guice.createInjector(module);
    createConfigurationRepository();
    createUserRepository();
    createMemberRepository();
    createGroupRepository();
  }

  private void createConfigurationRepository() throws Exception {
    var file = injector.getInstance(ConfigurationFile.class);
    configurationRepository = ConfigurationRepository.forFile(file);
  }

  private void createUserRepository() throws Exception {
    var file = injector.getInstance(UserFile.class);
    userRepository = UserRepository.forFile(file);
  }

  private void createMemberRepository() throws Exception {
    var file = injector.getInstance(MemberFile.class);
    memberRepository = MemberRepository.forFile(file);
  }

  private void createGroupRepository() throws Exception {
    var file = injector.getInstance(GroupFile.class);
    groupRepository = GroupRepository.forFile(file, memberRepository);
  }
}
