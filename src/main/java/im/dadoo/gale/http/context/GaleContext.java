package im.dadoo.gale.http.context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableAspectJAutoProxy
@EnableAsync
@ComponentScan("im.dadoo.gale")
public class GaleContext {

  @Bean
  public ObjectMapper mapper() {
    return new ObjectMapper();
  }
  
  @Bean
  public ExecutorService controllerPool() {
    return Executors.newCachedThreadPool();
  }
}
