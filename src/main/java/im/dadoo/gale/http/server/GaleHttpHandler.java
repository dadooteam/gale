package im.dadoo.gale.http.server;

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
    GaleRequest galeRequest = this.galeRequestProcessor.process(request);
    GaleResponse galeResponse = this.galeResponseProcessor.preprocess(request);
    try {
      this.processor.process(galeRequest, galeResponse);
    } catch (Exception e) {
      LOGGER.error(e.getLocalizedMessage());
      ELOGGER.error(this.getClass().getName(), e);
      galeResponse = this.galeResponseProcessor.preprocessException(request, e);
    } finally {
      FullHttpResponse response = this.galeResponseProcessor.process(galeResponse);
      if (HttpHeaders.isKeepAlive(request)) {
        ctx.writeAndFlush(response);
      } else {
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
      }
    }
  }
}
