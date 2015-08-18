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

/**
 *
 * @author codekitten
 */
public class GaleProcessor {
  
  private final GaleRouter router;
  
  public GaleProcessor(GaleRouter router) {
    this.router = router;
  }
  
  /**
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
        Object object = routee.getObject();
        Method callback = routee.getCallback();
        result = (String)callback.invoke(object, galeRequest);
      }
    }
    return result;
  }
}
