/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.gale.http.controller;

import im.dadoo.gale.http.annotation.GaleApi;
import im.dadoo.gale.http.annotation.GaleMapping;
import im.dadoo.gale.http.request.GaleRequest;

/**
 *
 * @author codekitten
 */
@GaleApi
public class IndexController {
  
  @GaleMapping(value = "/book/{id}", method = "GET")
  public String index(GaleRequest request) throws Exception {
    Thread.sleep(100);
    return request.getParameters().toString();
  }
  
  @GaleMapping(value = "/book/{id}", method = "POST")
  public String post(GaleRequest request) {
    return request.getParameters().toString();
  }
}
