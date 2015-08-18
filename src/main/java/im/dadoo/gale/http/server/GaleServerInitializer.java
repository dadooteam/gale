/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.gale.http.server;

import im.dadoo.gale.http.config.ServerConfig;
import im.dadoo.gale.http.router.GaleRouter;
import im.dadoo.gale.http.server.handler.GaleHttpHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 *
 * @author codekitten
 */
public class GaleServerInitializer extends ChannelInitializer<SocketChannel> {

  private final GaleProcessor processor;
  
  private final ServerConfig sc;
  
  public GaleServerInitializer(ServerConfig sc) {
    this.sc = sc;
    this.processor = new GaleProcessor(new GaleRouter(sc.getPackageNames()));
  }
  
  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    //inbound handler
    pipeline.addLast(new HttpRequestDecoder());
    pipeline.addLast(new HttpContentDecompressor());
    
    //outbound handler
    pipeline.addLast(new HttpResponseEncoder());
    pipeline.addLast(new ChunkedWriteHandler());
    pipeline.addLast(new HttpContentCompressor());
    
    pipeline.addLast(new HttpObjectAggregator(this.sc.getSize()));
    pipeline.addLast(new GaleHttpHandler(this.processor));
  }
  
}
