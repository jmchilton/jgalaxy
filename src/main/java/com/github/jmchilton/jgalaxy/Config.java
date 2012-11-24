package com.github.jmchilton.jgalaxy;

import com.google.common.base.Optional;
import java.io.File;

public class Config {
  
  public static Optional<File> getInstancesFile() {
    final Optional<File> configDir = getConfigDir();
    final Optional<File> instancesFile;
    if(configDir.isPresent()) {
      instancesFile = Optional.of(new File(configDir.get(), "instances.json"));
    } else {
      instancesFile = Optional.absent();
    }
    return instancesFile;
  }
  
  private static Optional<File> getConfigDir() {
    final File homeDir = new File(System.getProperty("user.home"));
    final File configDir = new File(homeDir, ".jgalaxy");
    if(!configDir.exists() && homeDir.canWrite()) {
      configDir.mkdirs();
    }
    final Optional<File> configDirOption;
    if(configDir.exists() && configDir.canWrite()) {
      configDirOption = Optional.of(configDir);
    } else {
      configDirOption = Optional.absent();
    }
    return configDirOption;
  }
  
}
