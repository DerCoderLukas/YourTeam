package de.dercoder.yourteam.deploy.configuration;

import com.google.common.base.Preconditions;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public final class ConfigurationRepository {
  private final Configuration configuration;

  private ConfigurationRepository(Configuration configuration) {
    this.configuration = configuration;
  }

  public String jwtSecret() {
    return configuration.getJwtSecret();
  }

  public byte[] jwtSecretBytes() {
    try {
      return configuration.getJwtSecret().getBytes("UTF-8");
    } catch (Exception exception) {
      exception.printStackTrace();
      return new byte[0];
    }
  }

  public Configuration configuration() {
    return configuration;
  }

  public static ConfigurationRepository forFile(
    ConfigurationFile configurationFile
  ) throws Exception {
    Preconditions.checkNotNull(configurationFile);
    var configuration = configurationFile.read();
    return new ConfigurationRepository(configuration);
  }
}
