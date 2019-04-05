package br.com.lif.iotegrator_v2.modelo;


import com.google.gson.Gson;
import javax.persistence.*;

import static javax.persistence.GenerationType.*;


@Entity
@Table(name = "Dht11",catalog = "test")
public class Dht11 {
	
	private double temperatura;
	private double umidade;
	private double sensasaoCalor;
	private String nome;
	private long id;
	private String local;
	
	public Dht11(double temperatura, double umidade, double sensasaoCalor, String nome, long id, String local) {
		super();
		this.temperatura = temperatura;
		this.umidade = umidade;
		this.sensasaoCalor = sensasaoCalor;
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
	
	public Dht11() {
		
	}
	
	@Column(name = "temperatura", nullable = false, length = 50)
	public double getTemperatura() {
		return temperatura;
	}
	
	@Column(name = "umidade", nullable = false, length = 50)
	public double getUmidade() {
		return umidade;
	}
	
	@Column(name = "sensasaoCalor", nullable = false, length = 50)
	public double getSensasaoCalor() {
		return sensasaoCalor;
	}
	
	@Column(name = "nome", nullable = false, length = 50)
	public String getNome() {
		return nome;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}
	
	@Column(name = "local", nullable = false, length = 50)
	public String getLocal() {
		return local;
	}

	public void setId(long id) {
		this.id =id;
	}
	
	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public void setUmidade(double umidade) {
		this.umidade = umidade;
	}

	public void setSensasaoCalor(double sensasaoCalor) {
		this.sensasaoCalor = sensasaoCalor;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
	
}
