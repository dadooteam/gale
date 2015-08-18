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
 *
 * @author codekitten
 */
public class Routee {

  /** 用于解析uri */
  private GalePattern pattern;

  /** 当前请求方法 */
  private RequestMethod method;

  /** uri匹配后的处理对象 */
  private Object object;

  /** uri匹配后的处理方法 */
  private Method callback;

  public static Routee of(String uri, RequestMethod method, Object object, Method callback) {
    Routee result = null;
    if (StringUtils.isNotBlank(uri) && method != null && object != null && callback != null) {
      result = new Routee();
      result.pattern = GalePattern.compile(uri);
      result.method = method;
      result.object = object;
      result.callback = callback;
    }
    return result;
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

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }

  public Method getCallback() {
    return callback;
  }

  public void setMethod(Method callback) {
    this.callback = callback;
  }

}
