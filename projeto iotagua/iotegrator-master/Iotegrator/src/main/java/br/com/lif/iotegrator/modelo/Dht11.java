package br.com.lif.iotegrator.modelo;

import com.google.gson.Gson;

public class Dht11 {
	
	private double temperatura;
	private double umidade;
	private double sensaoCalor;
	private String nome;
	private long id;
	private String local;
	
	public Dht11(double temperatura, double umidade, double sensaoCalor, String nome, long id, String local) {
		super();
		this.temperatura = temperatura;
		this.umidade = umidade;
		this.sensaoCalor = sensaoCalor;
		this.nome = nome;
		this.id = id;
		this.local = local;
	}
	
	public Dht11(double temperatura, double umidade, long id) {
		super();
		this.temperatura = temperatura;
		this.umidade = umidade;
		this.id = id;
	}

	public double getTemperatura() {
		return temperatura;
	}
	public double getUmidade() {
		return umidade;
	}
	public double getSensaoCalor() {
		return sensaoCalor;
	}
	public String getNome() {
		return nome;
	}
	public long getId() {
		return id;
	}
	public String getLocal() {
		return local;
	}

	public void setId(long id) {
		this.id =id;
	}
	
	public String toJson() {
		return new Gson().toJson(this);
	}
	
}
