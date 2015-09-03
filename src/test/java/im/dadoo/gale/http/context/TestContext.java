package im.dadoo.gale.http.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import im.dadoo.gale.http.config.ServerConfig;

@Configuration
@ComponentScan("im.dadoo.gale")
public class TestContext {

  @Bean
  public ServerConfig config() {
    return new ServerConfig();
  }
}
