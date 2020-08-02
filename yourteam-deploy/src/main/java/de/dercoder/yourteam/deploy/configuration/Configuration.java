package de.dercoder.yourteam.deploy.configuration;

public final class Configuration {
  private String jwtSecret;

  Configuration() {

  }

  Configuration(String jwtSecret) {
    this.jwtSecret = jwtSecret;
  }

  public void setJwtSecret(String jwtSecret) {
    this.jwtSecret = jwtSecret;
  }

  public String getJwtSecret() {
    return jwtSecret;
  }
}
