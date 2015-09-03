/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.gale.http.controller;

import im.dadoo.gale.http.annotation.GaleApi;
import im.dadoo.gale.http.annotation.GaleMapping;
import im.dadoo.gale.http.request.GaleRequest;
import im.dadoo.gale.http.request.RequestMethod;

/**
 *
 * @author codekitten
 */
@GaleApi
public class IndexController {
  
  @GaleMapping(value = "/book/{id}", method = RequestMethod.GET)
  public String index(GaleRequest request) {
    return "index";
  }
  
  @GaleMapping(value = "/book/{id}", method = RequestMethod.POST)
  public String post(GaleRequest request) {
    return request.getParameters().toString();
  }
}
