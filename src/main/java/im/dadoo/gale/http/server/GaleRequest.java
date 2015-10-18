package im.dadoo.gale.http.server;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 
 * @author codekitten
 * @since 0.4
 */
public class GaleRequest {
  
  private String uri;
  
  private String path;
  
  private HttpMethod method;
  
  private HttpVersion version;
  
  private HttpHeaders headers;
  
  private MultiValueMap<String, String> parameters;

  public GaleRequest() {
    this.parameters = new LinkedMultiValueMap<>();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("GaleRequest [uri=");
    builder.append(uri);
    builder.append(", path=");
    builder.append(path);
    builder.append(", method=");
    builder.append(method);
    builder.append(", version=");
    builder.append(version);
    builder.append(", headers=");
    builder.append(headers);
    builder.append(", parameters=");
    builder.append(parameters);
    builder.append("]");
    return builder.toString();
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public HttpMethod getMethod() {
    return method;
  }

  public void setMethod(HttpMethod method) {
    this.method = method;
  }

  public HttpVersion getVersion() {
    return version;
  }

  public void setVersion(HttpVersion version) {
    this.version = version;
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  public void setHeaders(HttpHeaders headers) {
    this.headers = headers;
  }

  public MultiValueMap<String, String> getParameters() {
    return parameters;
  }

  public void setParameters(MultiValueMap<String, String> parameters) {
    this.parameters = parameters;
  }
  
}
