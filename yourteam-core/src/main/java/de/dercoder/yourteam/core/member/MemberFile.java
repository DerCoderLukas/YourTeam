package de.dercoder.yourteam.core.member;

import java.io.File;
import java.nio.file.Path;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import com.fasterxml.jackson.databind.ObjectMapper;

@Singleton
public final class MemberFile {
  private final ObjectMapper objectMapper;
  private final Path memberFilePath;

  @Inject
  private MemberFile(
    ObjectMapper objectMapper, @Named("memberFilePath") Path memberFilePath
  ) {
    this.objectMapper = objectMapper;
    this.memberFilePath = memberFilePath;
  }

  public MemberFileModel read() throws Exception {
    var file = memberFilePath.toFile();
    if (handleFileExistence(file)) {
      throw new Exception("Can't create file");
    }
    try {
      return objectMapper.readValue(file, MemberFileModel.class);
    } catch (Exception exception) {
      var memberFileTemplate = createMemberFileTemplate();
      write(memberFileTemplate);
      return memberFileTemplate;
    }
  }

  private MemberFileModel createMemberFileTemplate() {
    return new MemberFileModel(Lists.newArrayList());
  }

  public void write(MemberFileModel fileModel) throws Exception {
    Preconditions.checkNotNull(fileModel);
    var file = memberFilePath.toFile();
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
