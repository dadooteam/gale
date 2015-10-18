package im.dadoo.gale.http.server;

import java.util.HashMap;
import java.util.Map;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * 
 * @author codekitten
 * @since 0.4
 */
public class GaleResponse {
  
  private HttpVersion version;
  
  private HttpResponseStatus status;
  
  private Map<String, String> headers; 

  private String content;

  public GaleResponse() {
    this.headers = new HashMap<>();
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("GaleResponse [version=");
    builder.append(version);
    builder.append(", status=");
    builder.append(status);
    builder.append(", headers=");
    builder.append(headers);
    builder.append(", content=");
    builder.append(content);
    builder.append("]");
    return builder.toString();
  }

  public HttpVersion getVersion() {
    return version;
  }

  public void setVersion(HttpVersion version) {
    this.version = version;
  }

  public HttpResponseStatus getStatus() {
    return status;
  }

  public void setStatus(HttpResponseStatus status) {
    this.status = status;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
