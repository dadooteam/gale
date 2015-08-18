package im.dadoo.gale.http.server.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import im.dadoo.gale.http.request.GaleRequest;
import im.dadoo.gale.http.server.GaleProcessor;
import im.dadoo.gale.http.util.GaleUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author codekitten
 */
public class GaleHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

  private static final Logger logger = LoggerFactory.getLogger(GaleHttpHandler.class);
  
  private static final Logger elogger = LoggerFactory.getLogger(Exception.class);
  
  private final GaleProcessor processor;
  
  public GaleHttpHandler(GaleProcessor processor) {
    this.processor = processor;
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
    GaleRequest galeRequest = GaleUtil.buildGaleRequest(request);
    FullHttpResponse response = new DefaultFullHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
    response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain;charset=UTF-8");
    try {
      if (!request.getMethod().equals(HttpMethod.GET)) {
        //将非get请求中的body参数解析出来,放到galeRequest的parameters中
        HttpPostRequestDecoder decoder = 
                new HttpPostRequestDecoder(new DefaultHttpDataFactory(true), request);
        Map<String, List<String>> postParameters = this.parsePostParameters(decoder.getBodyHttpDatas());
        galeRequest.getParameters().putAll(postParameters);
        decoder.destroy();
      }
      //自定义处理的核心部分
      String content = this.processor.process(galeRequest);
      if (content != null) {
        response.content().writeBytes(Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
      } else {
        response.setStatus(HttpResponseStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage());
      elogger.error("GaleHttpHandler error", e);
      response.content().writeBytes(Unpooled.wrappedBuffer(this.makeExceptionContent(e).getBytes("UTF-8")));
      response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
    } finally {
      if (response.content() != null) {
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
      }
      if (HttpHeaders.Values.KEEP_ALIVE.equals(request.headers().get(HttpHeaders.Names.CONNECTION))) {
        ctx.writeAndFlush(response);
      } else {
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
      }
    }
    
  }
  
  private Map<String, List<String>> parsePostParameters(List<InterfaceHttpData> datum) throws IOException {
    Map<String, List<String>> result = Maps.newHashMap();
    if (datum != null && !datum.isEmpty()) {
      for (InterfaceHttpData data : datum) {
        String name = data.getName();
        String value = null;
        if (data.getHttpDataType().equals(HttpDataType.Attribute)) {
          //解析普通参数
          Attribute attribute = (Attribute)data;
          value = attribute.getString();
        } else if (data.getHttpDataType().equals(HttpDataType.FileUpload)) {
          //解析文件
          FileUpload fileUpload = (FileUpload)data;
          value = fileUpload.getFile().getPath();
        }
        if (result.containsKey(name)) {
          result.get(name).add(value);
        } else {
          result.put(name, Lists.newArrayList(value));
        }
      }
    }
    return result;
  }
  
  private String makeExceptionContent(Exception e) {
    return String.format("{\"msg\":%s}", e.getLocalizedMessage());
  }
}
