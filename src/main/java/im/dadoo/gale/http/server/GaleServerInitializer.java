package im.dadoo.gale.http.server;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import im.dadoo.gale.http.config.ServerConfig;
import im.dadoo.gale.http.server.handler.GaleHttpHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
//import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 
 * @author codekitten
 * @since 0.1
 */
@Component
public class GaleServerInitializer extends ChannelInitializer<SocketChannel> {

  @Resource
  private GaleProcessor processor;
  
  @Resource
  private ServerConfig sc;
  
  @Resource
  private GaleHttpHandler galeHttpHandler;
  
  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    //inbound handler
    pipeline.addLast(new HttpRequestDecoder());
    pipeline.addLast(new HttpContentDecompressor());

    //outbound handler
    pipeline.addLast(new HttpResponseEncoder());
    pipeline.addLast(new HttpContentCompressor());
    //pipeline.addLast(new ChunkedWriteHandler());
    
    pipeline.addLast(new HttpObjectAggregator(this.sc.getSize()));
    pipeline.addLast(this.galeHttpHandler);
    
  }
  
}
