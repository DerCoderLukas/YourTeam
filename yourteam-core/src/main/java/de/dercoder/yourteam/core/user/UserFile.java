package de.dercoder.yourteam.core.user;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import com.fasterxml.jackson.databind.ObjectMapper;

@Singleton
public final class UserFile {
  private final ObjectMapper objectMapper;
  private final Path userFilePath;

  @Inject
  private UserFile(
    ObjectMapper objectMapper, @Named("userFilePath") Path userFilePath
  ) {
    this.objectMapper = objectMapper;
    this.userFilePath = userFilePath;
  }

  public UserFileModel read() throws Exception {
    var file = userFilePath.toFile();
    if (handleFileExistence(file)) {
      throw new Exception("Can't create file");
    }
    if (file.length() == 0) {
      var userFileTemplate = createUserFileTemplate();
      write(userFileTemplate);
      return userFileTemplate;
    }
    return objectMapper.readValue(file, UserFileModel.class);
  }

  private UserFileModel createUserFileTemplate() {
    var userList = Lists.<UserModel>newArrayList();
    var standardModel = createStandardModel();
    userList.add(standardModel);
    return new UserFileModel(userList);
  }

  private UserModel createStandardModel() {
    var name = "Administrator";
    var password = UUID.randomUUID().toString().replace("-", "");
    return new UserModel(name, password);
  }

  public void write(UserFileModel fileModel) throws Exception {
    Preconditions.checkNotNull(fileModel);
    var file = userFilePath.toFile();
    if (handleFileExistence(file)) {
      throw new Exception("Can't create file");
    }
    objectMapper.writeValue(file, fileModel);
  }

  private boolean handleFileExistence(File file) throws Exception {
    if (file.exists()) {
      return false;
    }
    file.getParentFile().mkdirs();
    return !file.createNewFile();
  }
}
