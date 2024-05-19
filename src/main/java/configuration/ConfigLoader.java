package configuration;

import configuration.model.AppConfig;

public interface ConfigLoader {

  AppConfig load(String path);

}
