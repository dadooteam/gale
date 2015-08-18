/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.gale.http.util;

import com.google.common.base.Charsets;
import im.dadoo.gale.http.request.GaleRequest;
import im.dadoo.gale.http.request.RequestMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

/**
 *
 * @author codekitten
 */
public final class GaleUtil {
  
  private GaleUtil() {}
  
  public static GaleRequest buildGaleRequest(HttpRequest request) {
    GaleRequest result = null;
    if (request != null) {
      result = new GaleRequest();
      QueryStringDecoder decoder = new QueryStringDecoder(request.getUri(), Charsets.UTF_8);
      result.setPath(decoder.path());
      result.getParameters().putAll(decoder.parameters());
      result.setMethod(RequestMethod.valueOf(request.getMethod().name()));
      result.setHeaders(request.headers());
      result.setVersion(request.getProtocolVersion());
    }
    return result;
  }
  
  
}
