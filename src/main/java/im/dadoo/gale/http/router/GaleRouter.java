package im.dadoo.gale.http.router;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.Lists;
import im.dadoo.gale.http.annotation.GaleMapping;
import im.dadoo.gale.http.request.GaleRequest;
import im.dadoo.gale.http.request.RequestMethod;
import im.dadoo.gale.http.uri.GalePattern;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 路由器，将url模板和对应的处理方法映射起来
 * @author codekitten
 * @see Routee
 */
@Component
public class GaleRouter {
  
  private static final Logger logger = LoggerFactory.getLogger(GaleRouter.class);
  
  private static final Logger elogger = LoggerFactory.getLogger(Exception.class);
  
  private Set<Routee> routees;
  
  public void init(Collection<Object> apis) {
    this.routees = new HashSet<>();
    try {
      checkNotNull(apis);
      
      for (Object api : apis) {
        Method[] methods = api.getClass().getMethods();
        for (Method method : methods) {
          if (checkMethod(method)) {
            GaleMapping mapping = method.getAnnotation(GaleMapping.class);
            String url = mapping.value();
            RequestMethod requestMethod = mapping.method();
            Routee routee = Routee.of(url, requestMethod, api, method);
            checkNotNull(routee, String.format("Routee is null.url{%s},requestMethod{%s},api{%s},method{%s}", url, requestMethod, api, method));
            this.routees.add(routee);
          }
        }
        logger.info(String.format("routees is %s", this.routees));
      }
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage());
      elogger.error("HttpRouter Construct Failed", e);
    }
  }
  
  /**
   * get the matchable routee from the routee set by url and requestMethod
   * @param galeRequest
   * @return 
   */
  public Routee getRoutee(GaleRequest galeRequest) {
    Routee result = null;
    for (Routee routee : routees) {
      GalePattern galePattern = routee.getPattern();
      
      Map<String, String> params = galePattern.matcher(galeRequest.getPath());
      if (params != null && routee.getMethod().equals(galeRequest.getMethod())) {
        for (String key : params.keySet()) {
          List<String> value = Lists.newArrayList(params.get(key));
          galeRequest.getParameters().put(key, value);
        }
        result = routee;
        break;
      }
    }
    return result;
  }
  
  /**
   * check whether the method is adaptable to route 
   * @param method
   * @return 
   */
  private boolean checkMethod(Method method) {
    boolean result = false;
    if (method != null) {
      result = true && !Modifier.isStatic(method.getModifiers());
      result = result && Modifier.isPublic(method.getModifiers());
      result = result && method.isAnnotationPresent(GaleMapping.class);
    }
    return result;
  }
}
