package im.dadoo.gale.http.server.handler;

import im.dadoo.gale.http.request.GaleRequest;
import im.dadoo.gale.http.server.GaleProcessor;
import im.dadoo.gale.http.server.GaleRequestProcessor;
import im.dadoo.gale.http.server.GaleResponseProcessor;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 处理请求的类
 * @author codekitten
 */
@Component
@Sharable
public class GaleHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

  private static final Logger LOGGER = LoggerFactory.getLogger(GaleHttpHandler.class);
  
  private static final Logger ELOGGER = LoggerFactory.getLogger(Exception.class);
  
  @Resource
  private GaleRequestProcessor galeRequestProcessor;
  
  @Resource
  private GaleResponseProcessor galeResponseProcessor;
  
  @Resource
  private GaleProcessor processor;
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
    GaleRequest galeRequest = null;
    FullHttpResponse response = null;
    try {
       galeRequest = this.galeRequestProcessor.process(request);
      //自定义处理的核心部分
      String content = this.processor.process(galeRequest);
      response = this.galeResponseProcessor.process(galeRequest, content);
    } catch (Exception e) {
      LOGGER.error(e.getLocalizedMessage());
      ELOGGER.error(this.getClass().getName(), e);
      response = this.galeResponseProcessor.processException(galeRequest, e);
    } finally {
      if (HttpHeaders.isKeepAlive(request)) {
        ctx.writeAndFlush(response);
      } else {
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
      }
    }
  }
}
