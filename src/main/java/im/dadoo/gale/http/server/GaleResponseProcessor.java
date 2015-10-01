package im.dadoo.gale.http.server;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import im.dadoo.gale.http.request.GaleRequest;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

/**
 * build FullHttpResponse with GaleRequest and content
 * @author codekitten
 * @since 0.4
 */
@Component
public class GaleResponseProcessor {

  public FullHttpResponse process(GaleRequest request, String content) {
    FullHttpResponse response = new DefaultFullHttpResponse(request.getVersion(), HttpResponseStatus.OK);
    response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json; charset=UTF-8");
    //keep alive
    if (request.getHeaders().contains(HttpHeaders.Names.CONNECTION)) {
      response.headers().set(HttpHeaders.Names.CONNECTION, request.getHeaders().get(HttpHeaders.Names.CONNECTION));
    }
    if (StringUtils.hasText(content)) {
      response.content().writeBytes(Unpooled.wrappedBuffer(content.getBytes(CharsetUtil.UTF_8)));
    } else {
      response.setStatus(HttpResponseStatus.NOT_FOUND);
    }
    return response;
  }
  
  public FullHttpResponse processException(GaleRequest request, Exception e) {
    FullHttpResponse response = new DefaultFullHttpResponse(request.getVersion(), HttpResponseStatus.INTERNAL_SERVER_ERROR);
    response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json; charset=UTF-8");
    //keep alive
    if (request.getHeaders().contains(HttpHeaders.Names.CONNECTION)) {
      response.headers().set(HttpHeaders.Names.CONNECTION, request.getHeaders().get(HttpHeaders.Names.CONNECTION));
    }
    response.content().writeBytes(Unpooled.wrappedBuffer(e.getLocalizedMessage().getBytes(CharsetUtil.UTF_8)));
    return response;
  }
}
