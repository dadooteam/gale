/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.gale.http.request;

/**
 *
 * @author codekitten
 */
public enum RequestMethod {
  
  GET("GET"),POST("POST"),PUT("PUT"),DELETE("DELETE");
  
  private final String name;
  
  private RequestMethod(String name) {
    this.name = name;
  }
  
  public String getName() {
    return this.name;
  }
}
