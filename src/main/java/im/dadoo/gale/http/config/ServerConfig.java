package im.dadoo.gale.http.config;

/**
 * 服务器配置信息
 * @author codekitten
 */
public class ServerConfig {
  
  private int port = 9090;
  
  //the max size of the post body,default 4G Byte
  private int size = Integer.MAX_VALUE;

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
}
