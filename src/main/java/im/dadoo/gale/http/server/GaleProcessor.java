package im.dadoo.gale.http.server;

import im.dadoo.gale.http.request.GaleRequest;
import im.dadoo.gale.http.router.GaleRouter;
import im.dadoo.gale.http.router.Routee;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 根据request中的请求url，路由到相应的处理方法中
 * @author codekitten
 */
@Component
public class GaleProcessor {
  
  @Resource
  private GaleRouter router;
  
  @Resource
  private ObjectMapper mapper;
  
  @Resource
  private ExecutorService controllerPool;
  
  /**
   * 处理request，返回处理后的字符串结果
   * 
   * @param request
   * @return Return null if the request does not match any routee.
   * @throws Exception 
   */
  public String process(final GaleRequest request) throws Exception {
    String result = null;
    Routee routee = this.router.getRoutee(request);
    if (routee != null) {
      final Object object = routee.getApi();
      final Method callback = routee.getCallback();
//      result = mapper.writeValueAsString(callback.invoke(object, request));
      Future<String> future = this.controllerPool.submit(new Callable<String>() {
        @Override
        public String call() throws Exception {
          return mapper.writeValueAsString(callback.invoke(object, request));
        }
        
      });
      result = future.get();
    }
    return result;
  }
}
