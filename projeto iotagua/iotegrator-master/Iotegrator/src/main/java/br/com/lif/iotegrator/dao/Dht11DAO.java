package br.com.lif.iotegrator.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import br.com.lif.iotegrator.modelo.Dht11;

public class Dht11DAO {
	
	private static Map<Long, Dht11> banco = new HashMap<Long, Dht11>();
	private static AtomicLong contador = new AtomicLong(1);

	 static {
	        banco.put(1l, new Dht11(22, 48 , 2014));
	        System.out.println(Dht11.class);
	        banco.put(2l, new Dht11(19, 82, 2012));
	    }
	
	public void adiciona(Dht11 dht11) {
		long id = contador.incrementAndGet();
		dht11.setId(id);
		banco.put(id, dht11);	
	}
	
	public Dht11 busca(Long id) {
		return banco.get(id);
	}
	
}
