package im.dadoo.gale.http.context;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import im.dadoo.gale.http.config.ServerConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("im.dadoo.gale")
public class GaleContext {

  @Bean
  public ServerConfig config() {
    return new ServerConfig();
  }
}
