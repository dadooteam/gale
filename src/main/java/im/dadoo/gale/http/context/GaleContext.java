package im.dadoo.gale.http.context;

import javax.annotation.Resource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan("im.dadoo.gale")
public class GaleContext {

  @Resource
  public ObjectMapper mapper() {
    return new ObjectMapper();
  }
}
