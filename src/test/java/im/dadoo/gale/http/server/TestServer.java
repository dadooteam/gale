package im.dadoo.gale.http.server;

import im.dadoo.gale.http.GaleStarter;
import im.dadoo.gale.http.config.ServerConfig;
import im.dadoo.gale.http.context.TestContext;

/**
 *
 * @author codekitten
 */
public class TestServer {
  
  public static void main(String[] args) {
    ServerConfig config = new ServerConfig();
    config.setHost("127.0.0.1");
    GaleStarter.startup(config, TestContext.class);
  }
}
