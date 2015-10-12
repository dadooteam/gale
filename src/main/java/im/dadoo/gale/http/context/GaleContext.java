package im.dadoo.gale.http.context;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("im.dadoo.gale")
public class GaleContext {
  
  @Bean
  public Table<String, String, AtomicLong> uriStatTable() {
    return TreeBasedTable.create();
  }

}
