package im.dadoo.gale.http.router;

import im.dadoo.gale.http.annotation.GaleMapping;
import im.dadoo.gale.http.request.GaleRequest;
import im.dadoo.gale.http.uri.GalePattern;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 路由器，将url模板和对应的处理方法映射起来
 * @author codekitten
 * @see Routee
 */
@Component
public class GaleRouter {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(GaleRouter.class);
  
  private static final Logger ELOGGER = LoggerFactory.getLogger(Exception.class);
  
  private Set<Routee> routees;
  
  public void init(Collection<Object> apis) {
    this.routees = new HashSet<>();
    try {
      Assert.notNull(apis);
      
      for (Object api : apis) {
        Method[] callbacks = api.getClass().getMethods();
        for (Method callback : callbacks) {
          if (checkMethod(callback)) {
            GaleMapping mapping = callback.getAnnotation(GaleMapping.class);
            String url = mapping.value();
            String methodName = mapping.method();
            Routee routee = Routee.of(url, HttpMethod.valueOf(methodName), api, callback);
            Assert.notNull(routee, String.format("Routee is null.url{%s},httpMethod{%s},api{%s},callback{%s}", url, methodName, api, callback));
            this.routees.add(routee);
            LOGGER.info(String.format("url{%s},method{%s} bind to callback{%s}", url, methodName, callback.toGenericString()));
          }
        }
      }
    } catch (Exception e) {
      LOGGER.error(e.getLocalizedMessage());
      ELOGGER.error(this.getClass().getName(), e);
    }
  }
  
  /**
   * get the matchable routee from the routee set by url and requestMethod
   * @param request
   * @return 
   */
  public Routee getRoutee(GaleRequest request) {
    Routee result = null;
    for (Routee routee : routees) {
      GalePattern galePattern = routee.getPattern();
      
      Map<String, String> params = galePattern.matcher(request.getPath());
      if (params != null && routee.getMethod().equals(request.getMethod())) {
        for (String key : params.keySet()) {
          request.getParameters().add(key, params.get(key));
        }
        result = routee;
        break;
      }
    }
    return result;
  }
  
  /**
   * check whether the method is adaptable to route 
   * @param callback
   * @return 
   */
  private boolean checkMethod(Method callback) {
    boolean result = false;
    if (callback != null) {
      result = true && !Modifier.isStatic(callback.getModifiers());
      result = result && Modifier.isPublic(callback.getModifiers());
      result = result && callback.isAnnotationPresent(GaleMapping.class);
    }
    return result;
  }
}
