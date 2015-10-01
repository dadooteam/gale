package im.dadoo.gale.http.server;

import im.dadoo.gale.http.GaleStarter;
import im.dadoo.gale.http.context.TestContext;
import io.netty.util.ResourceLeakDetector;

/**
 *
 * @author codekitten
 */
public class TestServer {
  
  public static void main(String[] args) {
    ResourceLeakDetector.getLevel();
    GaleStarter.startup(TestContext.class);
  }
}
