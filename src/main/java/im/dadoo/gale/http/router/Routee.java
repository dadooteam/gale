/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.gale.http.router;

import im.dadoo.gale.http.request.RequestMethod;
import im.dadoo.gale.http.uri.GalePattern;
import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;

/**
 * 保存编译后的url匹配模式和处理方法之间的对应关系
 * @author codekitten
 */
public class Routee {

  /** 用于解析uri */
  private GalePattern pattern;

  /** 当前请求方法 */
  private RequestMethod method;

  /** uri匹配后的处理对象 */
  private Object api;

  /** uri匹配后的处理方法 */
  private Method callback;

  public static Routee of(String uri, RequestMethod method, Object api, Method callback) {
    Routee result = null;
    if (StringUtils.isNotBlank(uri) && method != null && api != null && callback != null) {
      result = new Routee();
      result.pattern = GalePattern.compile(uri);
      result.method = method;
      result.api = api;
      result.callback = callback;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Routee [pattern=");
    builder.append(pattern);
    builder.append(", method=");
    builder.append(method);
    builder.append(", api=");
    builder.append(api);
    builder.append(", callback=");
    builder.append(callback);
    builder.append("]");
    return builder.toString();
  }

  public GalePattern getPattern() {
    return pattern;
  }

  public void setPattern(GalePattern pattern) {
    this.pattern = pattern;
  }

  public RequestMethod getMethod() {
    return method;
  }

  public void setMethod(RequestMethod method) {
    this.method = method;
  }

  public Object getApi() {
    return api;
  }

  public void setApi(Object api) {
    this.api = api;
  }

  public Method getCallback() {
    return callback;
  }

  public void setMethod(Method callback) {
    this.callback = callback;
  }

}
