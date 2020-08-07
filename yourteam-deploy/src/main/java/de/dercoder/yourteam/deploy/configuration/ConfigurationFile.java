package de.dercoder.yourteam.deploy.configuration;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

import com.google.common.base.Preconditions;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import com.fasterxml.jackson.databind.ObjectMapper;

@Singleton
public final class ConfigurationFile {
  private final ObjectMapper objectMapper;
  private final Path configurationFilePath;

  @Inject
  private ConfigurationFile(
    ObjectMapper objectMapper,
    @Named("configurationFilePath") Path configurationFilePath
  ) {
    this.objectMapper = objectMapper;
    this.configurationFilePath = configurationFilePath;
  }

  public Configuration read() throws Exception {
    var file = configurationFilePath.toFile();
    if (handleFileExistence(file)) {
      throw new Exception("Can't create file");
    }
    try {
      return objectMapper.readValue(file, Configuration.class);
    } catch (Exception exception) {
      var configuration = createConfigurationTemplate();
      write(configuration);
      return configuration;
    }
  }

  private Configuration createConfigurationTemplate() {
    var jwsSecret = UUID.randomUUID().toString().replace("-", "");
    return new Configuration(jwsSecret);
  }

  public void write(Configuration configuration) throws Exception {
    Preconditions.checkNotNull(configuration);
    var file = configurationFilePath.toFile();
    if (handleFileExistence(file)) {
      throw new Exception("Can't create file");
    }
    objectMapper.writeValue(file, configuration);
  }

  private boolean handleFileExistence(File file) throws Exception {
    if (file.exists()) {
      return false;
    }
    file.getParentFile().mkdirs();
    return !file.createNewFile();
  }
}
