package io.github.restart.gmo_danggeun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// TODO: 프론트 완료 후 exclude 제거
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class GmoDanggeunApplication {

  public static void main(String[] args) {
    SpringApplication.run(GmoDanggeunApplication.class, args);
  }

}
