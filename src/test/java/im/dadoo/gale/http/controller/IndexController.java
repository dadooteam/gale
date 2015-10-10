package im.dadoo.gale.http.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import im.dadoo.gale.http.annotation.GaleApi;
import im.dadoo.gale.http.annotation.GaleMapping;
import im.dadoo.gale.http.request.GaleRequest;

/**
 *
 * @author codekitten
 */
@GaleApi
public class IndexController {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
  
  @GaleMapping(value = "/book/{id}", method = "GET")
  public String index(GaleRequest request) throws Exception {
    //LOGGER.info(request.getHeaders().toString());
    //Thread.sleep(RandomUtils.nextInt(50, 100));
    return request.getParameters().toString();
  }
  
  @GaleMapping(value = "/book/{id}", method = "POST")
  public String post(GaleRequest request) {
    return request.getParameters().toString();
  }
}
