package im.dadoo.gale.http.server;

import javax.annotation.Resource;

import im.dadoo.gale.http.config.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 服务器主类，执行start()方法启动服务器，
 * 内包含了对netty的启动过程
 * 
 * @author codekitten
 */
@Component
public class GaleServer {
  
  private static final Logger logger = LoggerFactory.getLogger(GaleServer.class);
  
  private static final Logger elogger = LoggerFactory.getLogger(Exception.class);
  
  @Resource
  private ServerConfig config;
  
  @Resource
  private GaleServerInitializer galeServerInitializer;
  
  public void start(ServerConfig config) {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    
    try {
      
      ServerBootstrap b = new ServerBootstrap();
      b.group(workerGroup, bossGroup);
      b.option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_KEEPALIVE, true);
      b.channel(NioServerSocketChannel.class);
      b.handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(this.galeServerInitializer);
      Channel ch = b.bind(this.config.getPort()).sync().channel();
      logger.info(String.format("server is running on port %d", this.config.getPort()));
      ch.closeFuture().sync();
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage());
      elogger.error("GaleServer failed", e);
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
      logger.info(String.format("server is stopping", this.config.getPort()));
    }
  }
}
