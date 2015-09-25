/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.gale.http.server;

import im.dadoo.gale.http.request.GaleRequest;
import im.dadoo.gale.http.router.GaleRouter;
import im.dadoo.gale.http.router.Routee;
import java.lang.reflect.Method;

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
  
  /**
   * 处理request，返回处理后的字符串结果
   * 
   * @param galeRequest
   * @return Return null if the request does not match any routee.
   * @throws Exception 
   */
  public String process(GaleRequest galeRequest) throws Exception {
    String result = null;
    if (galeRequest != null) {
      Routee routee = this.router.getRoutee(galeRequest);
      if (routee != null) {
        Object object = routee.getApi();
        Method callback = routee.getCallback();
        result = this.mapper.writeValueAsString(callback.invoke(object, galeRequest));
      }
    }
    return result;
  }
}
