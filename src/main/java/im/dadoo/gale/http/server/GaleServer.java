/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.gale.http.server;

import im.dadoo.gale.http.config.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author codekitten
 */
public class GaleServer {
  
  private static final Logger logger = LoggerFactory.getLogger(GaleServer.class);
  
  private static final Logger elogger = LoggerFactory.getLogger(Exception.class);
  
  private final ServerConfig sc;
  
  public GaleServer(ServerConfig sc) {
    this.sc = sc;
  }
  
  public void start() {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(workerGroup, bossGroup);
      b.option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_KEEPALIVE, true);
      b.channel(NioServerSocketChannel.class);
      b.handler(new LoggingHandler(LogLevel.DEBUG))
              .childHandler(new GaleServerInitializer(this.sc));
      Channel ch = b.bind(this.sc.getPort()).sync().channel();
      logger.info(String.format("server is running on port %d", this.sc.getPort()));
      ch.closeFuture().sync();
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage());
      elogger.error("GaleServer failed", e);
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
      logger.info(String.format("server is stopping on port %d", this.sc.getPort()));
    }
  }
}
