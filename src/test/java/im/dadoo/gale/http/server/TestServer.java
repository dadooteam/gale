/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
