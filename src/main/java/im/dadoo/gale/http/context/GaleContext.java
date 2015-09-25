package im.dadoo.gale.http.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan("im.dadoo.gale")
public class GaleContext {

  @Bean
  public ObjectMapper mapper() {
    return new ObjectMapper();
  }
}
