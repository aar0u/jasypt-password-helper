package helper.jasypt.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigUtil {
  public static JsonNode readConfig() {
    JsonNode jsonNode;
    try {
      ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
      jsonNode = objectMapper.readTree(getInput("config.yaml"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return jsonNode;
  }

  public static InputStream getInput(String fileName) {
    InputStream inputStream;
    try {
      inputStream = new FileInputStream(fileName);
    } catch (FileNotFoundException e) {
      log.info("{} not found, try to get from class path", fileName);
      inputStream = ConfigUtil.class.getClassLoader().getResourceAsStream(fileName);
    }
    return inputStream;
  }
}
