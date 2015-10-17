package im.dadoo.gale.http.server;

import javax.annotation.Resource;

import im.dadoo.gale.http.config.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

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
  
  private static final Logger LOGGER = LoggerFactory.getLogger(GaleServer.class);
  
  private static final Logger ELOGGER = LoggerFactory.getLogger(Exception.class);
  
  @Resource
  private ServerConfig config;
  
  @Resource
  private GaleServerInitializer galeServerInitializer;
  
  public void start() {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup(8);
    try {
      
      ServerBootstrap b = new ServerBootstrap();
      b.option(ChannelOption.SO_BACKLOG, 1024);
      b.option(ChannelOption.SO_KEEPALIVE, true);
      b.option(ChannelOption.SO_REUSEADDR, true);
      b.group(workerGroup, bossGroup).channel(NioServerSocketChannel.class).childHandler(this.galeServerInitializer);
      
      Channel ch = b.bind(this.config.getHost(), this.config.getPort()).sync().channel();
      LOGGER.info(String.format("server is running on host %s, port %d", this.config.getHost(), this.config.getPort()));
      ch.closeFuture().sync();
    } catch (Exception e) {
      LOGGER.error(e.getLocalizedMessage());
      ELOGGER.error("GaleServer failed", e);
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
      LOGGER.info(String.format("server is stopping", this.config.getPort()));
    }
  }
}
