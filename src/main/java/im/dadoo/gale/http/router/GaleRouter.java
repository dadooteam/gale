/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.gale.http.router;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import im.dadoo.gale.http.annotation.Controller;
import im.dadoo.gale.http.annotation.RequestMapping;
import im.dadoo.gale.http.request.GaleRequest;
import im.dadoo.gale.http.request.RequestMethod;
import im.dadoo.gale.http.uri.GalePattern;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author codekitten
 */
public class GaleRouter {
  
  private static final Logger logger = LoggerFactory.getLogger(GaleRouter.class);
  
  private static final Logger elogger = LoggerFactory.getLogger(Exception.class);
  
  private final Set<Routee> routees;
  
  public GaleRouter(List<String> packageNames) {
    this.routees = new HashSet<>();
    try {
      ClassPath cp = ClassPath.from(ClassLoader.getSystemClassLoader());
      for (String packageName : packageNames) {
        if (StringUtils.isNotBlank(packageName)) {
          ImmutableSet<ClassInfo> classInfos = cp.getTopLevelClasses(packageName);
          for (ClassInfo classInfo : classInfos) {
            Class<?> c = classInfo.load();
            if (c.isAnnotationPresent(Controller.class)) {
              Object object = c.newInstance();
              Method[] methods = c.getMethods();
              for (Method method : methods) {
                if (checkMethod(method)) {
                  RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                  String url = mapping.value();
                  RequestMethod requestMethod = mapping.method();
                  Routee routee = Routee.of(url, requestMethod, object, method);
                  checkNotNull(routee, String.format("Routee is null.url{%s},requestMethod{%s},object{%s},method{%s}", url, requestMethod, object, method));
                  this.routees.add(routee);
                }
              }
            }
          }
        }
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
      result = result && method.isAnnotationPresent(RequestMapping.class);
    }
    return result;
  }
}
