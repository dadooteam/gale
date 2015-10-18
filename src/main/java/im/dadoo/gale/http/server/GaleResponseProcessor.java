package im.dadoo.gale.http.server;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

/**
 * build FullHttpResponse with FullHttpRequest and content
 * @author codekitten
 * @since 0.4
 */
@Component
public class GaleResponseProcessor {

  public GaleResponse preprocess(FullHttpRequest request) {
    GaleResponse galeResponse = new GaleResponse();
    galeResponse.setVersion(request.getProtocolVersion());
    galeResponse.setStatus(HttpResponseStatus.OK);
    galeResponse.getHeaders().put(HttpHeaders.Names.CONTENT_TYPE, "application/json; charset=UTF-8");
    if (request.headers().contains(HttpHeaders.Names.CONNECTION)) {
      galeResponse.getHeaders().put(HttpHeaders.Names.CONNECTION, request.headers().get(HttpHeaders.Names.CONNECTION));
    }
    return galeResponse;
  }
  
  public GaleResponse preprocessException(FullHttpRequest request, Exception e) {
    GaleResponse galeResponse = this.preprocess(request);
    galeResponse.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
    galeResponse.setContent(e.getLocalizedMessage());
    return galeResponse;
  }
  
  public FullHttpResponse process(GaleResponse galeResponse) {
    FullHttpResponse response = new DefaultFullHttpResponse(galeResponse.getVersion(), galeResponse.getStatus());
    Map<String, String> headers = galeResponse.getHeaders();
    for (String name : headers.keySet()) {
      response.headers().set(name, headers.get(name));
    }
    String content = galeResponse.getContent();
    if (StringUtils.hasText(content)) {
      response.content().writeBytes(Unpooled.wrappedBuffer(content.getBytes(CharsetUtil.UTF_8)));
    }
    return response;
  }
}
