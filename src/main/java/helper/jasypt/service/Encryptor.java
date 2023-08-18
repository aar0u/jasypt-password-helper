package helper.jasypt.service;

import com.fasterxml.jackson.databind.JsonNode;
import helper.jasypt.util.ConfigUtil;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class Encryptor {
  private final PooledPBEStringEncryptor encryptor;

  public Encryptor() {
    JsonNode jsonNode = ConfigUtil.readConfig();

    encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword(jsonNode.get("password").asText());
    config.setAlgorithm(jsonNode.get("algorithm").asText());
    JsonNode keyObtentionIterations = jsonNode.get("keyObtentionIterations");
    if (keyObtentionIterations != null)
      config.setKeyObtentionIterations(keyObtentionIterations.asText());
    config.setPoolSize("1");
    config.setProviderName("SunJCE");
    JsonNode saltGeneratorClassName = jsonNode.get("saltGeneratorClassName");
    if (saltGeneratorClassName != null)
      config.setSaltGeneratorClassName(saltGeneratorClassName.asText());
    JsonNode ivGeneratorClassName = jsonNode.get("ivGeneratorClassName");
    if (ivGeneratorClassName != null) config.setIvGeneratorClassName(ivGeneratorClassName.asText());
    config.setStringOutputType("base64");
    encryptor.setConfig(config);
  }

  public String encrypt(String input) {
    return encryptor.encrypt(input);
  }

  public String decrypt(String input) {
    String output = "N/A";
    // remove ENC()
    try {
      output = encryptor.decrypt(input.substring(4, input.length() - 1));
    } catch (RuntimeException e) {
      System.out.println("ignored error: " + e);
    }
    return output;
  }
}
