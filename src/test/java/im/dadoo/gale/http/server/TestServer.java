package im.dadoo.gale.http.server;

import im.dadoo.gale.http.GaleStarter;
import im.dadoo.gale.http.context.TestContext;

/**
 *
 * @author codekitten
 */
public class TestServer {
  
  public static void main(String[] args) {
    GaleStarter.startup(TestContext.class);
  }
}
