/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.gale.http.server;

import com.google.common.collect.Lists;
import im.dadoo.gale.http.config.ServerConfig;
import io.netty.util.ResourceLeakDetector;

/**
 *
 * @author codekitten
 */
public class TestServer {
  
  public static void main(String[] args) {
    ResourceLeakDetector.getLevel();
    ServerConfig sc = new ServerConfig();
    sc.setPort(8080);
    sc.setSize(Integer.MAX_VALUE);
    sc.setPackageNames(Lists.newArrayList("im.dadoo.gale.http.controller"));
    GaleServer server = new GaleServer(sc);
    server.start();
  }
}
