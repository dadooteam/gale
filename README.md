# gale
A high  performance RPC framework based on HTTP and Netty

# Hello World
	
###Controller
	package im.dadoo.gale.example.controller;

	import im.dadoo.gale.http.annotation.Controller;
	import im.dadoo.gale.http.annotation.RequestMapping;
	import im.dadoo.gale.http.request.GaleRequest;
	import im.dadoo.gale.http.request.RequestMethod;

	@Controller
	public class BookController {
	  
	  @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
	  public String get(GaleRequest request) {
	    return "Hello,World";
	  }

	}

###App
	import com.google.common.collect.Lists;
	import im.dadoo.gale.http.config.ServerConfig;
	import io.netty.util.ResourceLeakDetector;

	public class App {
	  
	  public static void main(String[] args) {
	    ServerConfig sc = new ServerConfig();
	    sc.setPackageNames(Lists.newArrayList("im.dadoo.gale.example.controller"));
	    GaleServer server = new GaleServer(sc);
	    server.start();
	  }
	}

###Run
	$ curl localhost:9090/book/3
	$ Hello,World
