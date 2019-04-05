package br.com.lif.iotegrator_v2;

import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		System.out.println("Iniciando o  Iotegrator!");
		HttpServer server = startServer();
		System.out.println("Iotegrator Ready!");
		System.in.read();

		server.stop(); 
		
		System.out.println("Iotegrator Down!");
	}

	static HttpServer startServer() throws IOException {
		System.out.println("Preparando ambiente!");
		
		ResourceConfig config = new ResourceConfig();
		config.packages("br.com.lif.iotegrator_v2");
		URI uri = URI.create("http://192.168.0.106:8000/");
		//URI uri = URI.create("http://0.0.0.0:9998/");
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		//server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("src/main/webapp","/"));
		//server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("iotegrator"),"/"); 
	
		return server;
	}

}