package im.dadoo.gale.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;

/**
 * transform FullHttpRequest to GaleRequest
 * @author codekitten
 * @since 0.4
 */
@Component
public class GaleRequestProcessor {

  private static final Logger ELOGGER = LoggerFactory.getLogger(Exception.class);
  
  public GaleRequest process(FullHttpRequest request) {
    GaleRequest result = new GaleRequest();
    result.setUri(request.getUri());
    result.setMethod(request.getMethod());
    result.setHeaders(request.headers());
    result.setVersion(request.getProtocolVersion());
    
    //parse query parameters
    QueryStringDecoder queryDecoder = new QueryStringDecoder(request.getUri(), CharsetUtil.UTF_8);
    
    result.setPath(queryDecoder.path());
    result.getParameters().putAll(queryDecoder.parameters());
    //parse body parameters
    HttpPostRequestDecoder bodyDecoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(true), request);
    
    List<InterfaceHttpData> datum = bodyDecoder.getBodyHttpDatas();
    if (datum != null && !datum.isEmpty()) {
      for (InterfaceHttpData data : datum) {
        String name = data.getName();
        String value = null;
        if (data.getHttpDataType().equals(HttpDataType.Attribute)) {
          //do not parse file data
          Attribute attribute = (Attribute)data;
          try {
            value = attribute.getString(CharsetUtil.UTF_8);
            result.getParameters().add(name, value);
          } catch(Exception e) {
            ELOGGER.error(this.getClass().getName(), e);
          }
        }
      }
    }
    bodyDecoder.destroy();
    return result;
  }
  
}
