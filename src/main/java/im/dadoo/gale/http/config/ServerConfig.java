/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.gale.http.config;

import java.util.List;

/**
 *
 * @author codekitten
 */
public class ServerConfig {
  
  private int port = 9090;
  
  //the max size of the post body,default 2M
  private int size = 2 * 1024 * 1024;
  
  //package names for @Controller annotation
  private List<String> packageNames;

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public List<String> getPackageNames() {
    return packageNames;
  }

  public void setPackageNames(List<String> packageNames) {
    this.packageNames = packageNames;
  }
  
  
}
