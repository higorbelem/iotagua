package br.com.lif.iotegrator;

import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

	public static void main(String[] args) throws IOException {
		System.out.println("Iniciando o  Iotegrator!");
		HttpServer server = startServer();
		System.out.println("Iotegrator Ready!");
		System.in.read();

		server.stop();
		
		System.out.println("Iotegrator Down!");
	}

	static HttpServer startServer() throws UnknownHostException {
		System.out.println("Preparando ambiente!");
		ResourceConfig config = new ResourceConfig().packages("br.com.lif.iotegrator");
		URI uri = URI.create("http://localhost:8000/");
		//URI uri = URI.create("http://0.0.0.0:9998/");
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		
		

		/*InetAddress localHost = InetAddress.getLocalHost();
	    String localHostAddr = localHost.getHostAddress();
	    System.out.println(localHostAddr);
	    NetworkListener localHostListener = new NetworkListener("localhost", localHostAddr, 8080);
	    server.addListener(localHostListener);
	    InetAddress loopback = InetAddress.getLoopbackAddress();
	    String loopbackAddr = loopback.getHostAddress();
	    System.out.println(loopbackAddr);
	    NetworkListener loopbackListener = new NetworkListener("loopback", loopbackAddr, 8080);
	    server.addListener(loopbackListener);*/
	
		return server;
	}

}