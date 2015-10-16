package im.dadoo.gale.http;

import java.util.Map;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import im.dadoo.gale.http.annotation.GaleApi;
import im.dadoo.gale.http.config.ServerConfig;
import im.dadoo.gale.http.context.GaleContext;
import im.dadoo.gale.http.router.GaleRouter;
import im.dadoo.gale.http.server.GaleServer;

public final class GaleStarter {

  private GaleStarter() {}
  
  public static final void startup(Class<?>... annotatedClasses) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(GaleContext.class);
    context.register(annotatedClasses);
    context.refresh();
    //使用@GaleApi中的映射信息初始化router
    GaleRouter router = context.getBean(GaleRouter.class);
    Map<String, Object> beanMap = context.getBeansWithAnnotation(GaleApi.class);
    router.init(beanMap.values());
    //启动server
    ServerConfig config = context.getBean(ServerConfig.class);
    Assert.notNull(config);
    GaleServer server = context.getBean(GaleServer.class);
    server.start(config);
    context.close();
  }
}
