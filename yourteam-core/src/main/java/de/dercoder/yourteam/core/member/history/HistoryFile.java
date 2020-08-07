package de.dercoder.yourteam.core.member.history;

import java.io.File;
import java.nio.file.Path;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import com.fasterxml.jackson.databind.ObjectMapper;

@Singleton
public final class HistoryFile {
  private final ObjectMapper objectMapper;
  private final Path historyFilePath;

  @Inject
  private HistoryFile(
    ObjectMapper objectMapper, @Named("historyFilePath") Path historyFilePath
  ) {
    this.objectMapper = objectMapper;
    this.historyFilePath = historyFilePath;
  }

  public HistoryFileModel read() throws Exception {
    var file = historyFilePath.toFile();
    if (!handleFileExistence(file)) {
      throw new Exception("Can't create file");
    }
    try {
      return objectMapper.readValue(file, HistoryFileModel.class);
    } catch (Exception exception) {
      var historyFileTemplate = createHistoryFileTemplate();
      write(historyFileTemplate);
      return historyFileTemplate;
    }
  }

  private HistoryFileModel createHistoryFileTemplate() {
    return new HistoryFileModel(Maps.newHashMap());
  }

  public void write(HistoryFileModel fileModel) throws Exception {
    Preconditions.checkNotNull(fileModel);
    var file = historyFilePath.toFile();
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
    return file.createNewFile();
  }
}
