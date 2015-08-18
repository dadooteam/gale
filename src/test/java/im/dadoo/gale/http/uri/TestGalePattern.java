/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.dadoo.gale.http.uri;

import im.dadoo.gale.http.uri.GalePattern;
import org.junit.Test;

/**
 *
 * @author codekitten
 */
public class TestGalePattern {
  
  @Test
  public void testGalePattern() {
    String uri = "/book/{id}.{format}";
    GalePattern gPattern = GalePattern.compile(uri);
    System.out.println(gPattern.matcher("/book/1.json"));
  }
  
}
