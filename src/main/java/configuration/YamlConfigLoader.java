package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import configuration.model.AppConfig;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class YamlConfigLoader implements ConfigLoader {

  @Override
  public AppConfig load(String path) {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    try {
      Path configPath = Paths.get(getClass().getResource("/" + path).toURI());
      String content = new String(Files.readAllBytes(configPath));
      return mapper.readValue(content, AppConfig.class);
    } catch (Exception e) {
      throw new RuntimeException("Could not read configuration file: " + path, e);
    }
  }
}
