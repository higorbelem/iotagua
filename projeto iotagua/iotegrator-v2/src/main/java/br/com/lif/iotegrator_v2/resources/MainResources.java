package br.com.lif.iotegrator_v2.resources;

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

import br.com.lif.iotegrator_v2.dao.Dht11DAO;
import br.com.lif.iotegrator_v2.dao.FluxoDAO;
import br.com.lif.iotegrator_v2.modelo.Dht11;
import br.com.lif.iotegrator_v2.modelo.Fluxo;
import br.com.lif.iotegrator_v2.modelo.PostFormat;

@Path("iotegrator")
public class MainResources {
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String search(@PathParam("id") long id) {
		Dht11 dht11 = new Dht11DAO().search(id);
		return dht11.toJson();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(String conteudo) {
		System.out.println(conteudo);
		Gson gson = new Gson();
		PostFormat post = gson.fromJson(conteudo, PostFormat.class);
		
		for(int i = 0 ; i < post.getMedicoes().size() ; i++) {
			switch (post.getMedicoes().get(i).getIdv()) {
			/*case 1: //dht11
				
				if(post.getTipo() == 'r') {
					
				}else {
					Dht11 dht11 = new Dht11(post.getMedicoes().get(i).getValor(),post.getMedicoes().get(1).getValor(),0);
					dht11.setNome("asd");
					dht11.setLocal("adad");
					dht11.setSensasaoCalor(1);
						            
					new Dht11DAO().add(dht11);
					URI uri = URI.create("/dht11/"+dht11.getId());
					System.out.println(dht11);
					return Response.created(uri).build();
				}
				
				break;*/
			case 1://fluxo1 vermeho
				
				if(post.getTipo() == 'r') {
					
				}else {
					Fluxo fluxo = new Fluxo(post.getMedicoes().get(i).getValor(),0,post.getMedicoes().get(i).getIdv());
					fluxo.setNome("asd");
					fluxo.setLocal("adad");
						
					new FluxoDAO().add(fluxo);
					URI uri = URI.create("/fluxo/"+fluxo.getId());
					System.out.println(fluxo);
					return Response.created(uri).build();
				}
				
				break;
			case 2://fluxo2
				
				if(post.getTipo() == 'r') {
					
				}else {
					Fluxo fluxo = new Fluxo(post.getMedicoes().get(i).getValor(),0,post.getMedicoes().get(i).getIdv());
					fluxo.setNome("asd");
					fluxo.setLocal("adad");
						
					new FluxoDAO().add(fluxo);
					URI uri = URI.create("/fluxo/"+fluxo.getId());
					System.out.println(fluxo);
					return Response.created(uri).build();
				}
				
				break;
			default:
				
				break;
			}
		}
		
		return null;
	}
}
