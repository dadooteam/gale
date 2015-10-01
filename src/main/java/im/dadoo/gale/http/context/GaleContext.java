package im.dadoo.gale.http.context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
  
  @Bean(destroyMethod = "shutdown")
  public ExecutorService controllerPool() {
//    return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    return Executors.newCachedThreadPool();
  }
}
