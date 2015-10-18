package im.dadoo.gale.http.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import im.dadoo.gale.http.annotation.GaleApi;
import im.dadoo.gale.http.annotation.GaleMapping;
import im.dadoo.gale.http.server.GaleRequest;
import im.dadoo.gale.http.server.GaleResponse;

/**
 *
 * @author codekitten
 */
@GaleApi
public class IndexController {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
  
  @GaleMapping(value = "/book/{id}", method = "GET")
  public void index(GaleRequest request, GaleResponse response) throws Exception {
    response.setContent(request.getParameters().toString());
  }
  
}
