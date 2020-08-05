package de.dercoder.yourteam.deploy;

import com.google.inject.Guice;
import com.google.inject.Inject;
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
  @Inject
  private ConfigurationRepository configurationRepository;
  @Inject
  private UserRepository userRepository;
  @Inject
  private MemberRepository memberRepository;
  @Inject
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
    var injector = Guice.createInjector(module);
    injector.injectMembers(this);
  }
}
