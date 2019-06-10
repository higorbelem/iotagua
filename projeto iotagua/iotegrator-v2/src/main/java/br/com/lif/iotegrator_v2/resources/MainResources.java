package br.com.lif.iotegrator_v2.resources;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

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
		/*try {
			sendPost();
		}catch(Exception e){
			System.out.println(e.toString());		
		}*/
		
		Gson gson = new Gson();
		
		PostFormat post = new PostFormat();
		try {
			post = gson.fromJson(conteudo, PostFormat.class);
		}catch (Exception e) {
			System.out.println(e.toString());
			System.exit(0);
		}
		
		if(post.getTipo() == 'r') {
			
		}else if(post.getTipo() == 'w'){
			
		}else if(post.getTipo() == 'c'){
			try {
				String resp = sendPostGetSensores(post.getIds());
				return Response.accepted(resp).build();
			}catch(Exception e) {
				
			}
		}
				
		/*for(int i = 0 ; i < post.getMedicoes().size() ; i++) {
			switch (post.getMedicoes().get(i).getIdv()) {
			case 1://fluxo1 vermeho
				
				if(post.getTipo() == 'r') {
					
				}else {
					Fluxo fluxo = new Fluxo(post.getMedicoes().get(i).getValor(),0,post.getMedicoes().get(i).getIdv());
					fluxo.setNome("asd");
					fluxo.setLocal("adad");
						
					try {
						sendPost(post.getMedicoes().get(i).getValor());
					}catch(Exception e){
						
					}
					
					//new FluxoDAO().add(fluxo);
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
		}*/
		
		return null;
	}
	
	/*static {
	    //for localhost testing only
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){

	        public boolean verify(String hostname,
	                javax.net.ssl.SSLSession sslSession) {
	            if (hostname.equals("localhost")) {
	                return true;
	            }
	            return false;
	        }
	    });
	}*/
	
	private void sendPostNivel(double valor) throws Exception {

		String url = "http://iotagua/IoT%c3%81gua%20-%20Modded/php/setNivel.php";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		// add header
		//post.setHeader("User-Agent", USER_AGENT);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("valor", "" + valor));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
		System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		System.out.println(result.toString());

	}
	
	private String sendPostGetSensores(int idSistema) throws Exception {

		String url = "http://iotagua/IoT%c3%81gua%20-%20Modded/php/getSensores.php";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		// add header
		//post.setHeader("User-Agent", USER_AGENT);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("id-sistema", "" + idSistema));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		System.out.println(result.toString());
		
		return result.toString();
	}
}
