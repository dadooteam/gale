package im.dadoo.gale.http.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAspectJAutoProxy
@EnableAsync
@ComponentScan("im.dadoo.gale")
public class GaleContext {
  
}
