package kr.co.onmediagroup.onoffapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class OnOffApiApplication {

  public static void main(String[] args) {
      SpringApplication springApplication = new SpringApplication(OnOffApiApplication.class);
      springApplication.addListeners(new ApplicationPidFileWriter());
      springApplication.run(args);
  }
}
