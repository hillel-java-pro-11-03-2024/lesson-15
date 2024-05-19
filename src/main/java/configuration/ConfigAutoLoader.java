package configuration;

import configuration.model.AppConfig;

public class ConfigAutoLoader {

  public AppConfig load(String filename) {
    String[] fileParts = filename.split("\\.(?=[^\\.]+$)");
    if (fileParts.length != 2) {
      throw new IllegalArgumentException("Could not read configuration filename, please check");
    }

    String extension = fileParts[1];
    return switch (extension) {
      case "yaml", "yml" -> new YamlConfigLoader().load(filename);
      default -> throw new IllegalArgumentException(
          "Could not work with '" + extension + "' configuration");
    };
  }
}
