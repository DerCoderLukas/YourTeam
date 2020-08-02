package de.dercoder.yourteam.core.group;

import java.io.File;
import java.nio.file.Path;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import com.fasterxml.jackson.databind.ObjectMapper;

@Singleton
public final class GroupFile {
  private final ObjectMapper objectMapper;
  private final Path groupFilePath;

  @Inject
  private GroupFile(
    ObjectMapper objectMapper, @Named("groupFilePath") Path groupFilePath
  ) {
    this.objectMapper = objectMapper;
    this.groupFilePath = groupFilePath;
  }

  public GroupFileModel read() throws Exception {
    var file = groupFilePath.toFile();
    if (handleFileExistence(file)) {
      throw new Exception("Can't create file");
    }
    if (file.length() == 0) {
      var groupFileTemplate = createGroupFileTemplate();
      write(groupFileTemplate);
      return groupFileTemplate;
    }
    return objectMapper.readValue(file, GroupFileModel.class);
  }

  private GroupFileModel createGroupFileTemplate() {
    return new GroupFileModel(Lists.newArrayList());
  }

  public void write(GroupFileModel fileModel) throws Exception {
    Preconditions.checkNotNull(fileModel);
    var file = groupFilePath.toFile();
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
