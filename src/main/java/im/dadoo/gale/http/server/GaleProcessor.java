package im.dadoo.gale.http.server;

import im.dadoo.gale.http.router.GaleRouter;
import im.dadoo.gale.http.router.Routee;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * 根据request中的请求url，路由到相应的处理方法中
 * @author codekitten
 */
@Component
public class GaleProcessor {
  
  @Resource
  private GaleRouter router;
  
  /**
   * 处理request，返回处理后的字符串结果
   * 
   * @param request http request wrapper
   * @param response http response wrapper
   * @throws Exception invoke error 
   */
  public void process(GaleRequest request, GaleResponse response) throws Exception {
    Routee routee = this.router.getRoutee(request);
    if (routee != null) {
      final Object target = routee.getApi();
      final Method callback = routee.getCallback();
      callback.invoke(target, request, response);
    } else {
      response.setStatus(HttpResponseStatus.NOT_FOUND);
    }
  }
}
