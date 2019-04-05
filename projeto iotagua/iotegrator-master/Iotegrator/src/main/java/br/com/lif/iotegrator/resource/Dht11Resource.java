package br.com.lif.iotegrator.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.lif.iotegrator.dao.Dht11DAO;
import br.com.lif.iotegrator.modelo.Dht11;

@Path("dht11")
public class Dht11Resource {

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String busca(@PathParam("id") long id) {
		Dht11 dht11 = new Dht11DAO().busca(id);
		return dht11.toJson();
	}
	
	@POST@Consumes(MediaType.APPLICATION_JSON)
	public Response adiciona(String conteudo) {
		System.out.println(conteudo);
		Gson gson = new Gson();
		Dht11 dht11 = gson.fromJson(conteudo, Dht11.class);
		new Dht11DAO().adiciona(dht11);
		URI uri = URI.create("/dht11/"+dht11.getId());
		System.out.println(dht11);
		return Response.created(uri).build();
	}
}
