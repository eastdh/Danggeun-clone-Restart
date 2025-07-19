package io.github.restart.gmo_danggeun.config;

import java.security.Principal;

public class StompPrincipal implements Principal {

  private final String name;

  public StompPrincipal(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }
}
