package im.dadoo.gale.http.uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author codekitten
 */
public class GalePattern {
  
  private final List<String> keys;
  
  private Pattern pattern;
  
  private GalePattern() {
    this.keys = new ArrayList<>();
  }
  
  public static GalePattern compile(String uri) {
    GalePattern result = new GalePattern();

    String regex = "\\{(\\w+)\\}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(uri);
    while(matcher.find()) {
      result.keys.add(matcher.group(1));
    }
    String replacement = "(\\\\w+)";
    String compiledUri = matcher.replaceAll(replacement);
    result.pattern = Pattern.compile(compiledUri);
    return result;
  }
  
  /**
   * 
   * @param uri
   * @return 如果匹配不成功,返回null,如果匹配成功,返回提取的参数map
   */
  public Map<String, String> matcher(String uri) {
    Map<String, String> result = null;
    Matcher matcher = this.pattern.matcher(uri);
    if (matcher.find()) {
      result = new HashMap<>();
      int i = 0;
      for (String key : this.keys) {
        result.put(key, matcher.group(++i));
      }
    }
    return result;
  }
}
