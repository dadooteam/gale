/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.gale.http.request;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpVersion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author codekitten
 */
public class GaleRequest {
  
  private String path;
  
  private RequestMethod method;
  
  private HttpVersion version;
  
  private HttpHeaders headers;
  
  private Map<String, List<String>> parameters;

  public GaleRequest() {
    this.parameters = new HashMap<>();
  }
  
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public RequestMethod getMethod() {
    return method;
  }

  public void setMethod(RequestMethod method) {
    this.method = method;
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  public void setHeaders(HttpHeaders headers) {
    this.headers = headers;
  }

  public Map<String, List<String>> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, List<String>> parameters) {
    this.parameters = parameters;
  }

  public HttpVersion getVersion() {
    return version;
  }

  public void setVersion(HttpVersion version) {
    this.version = version;
  }
  
}
