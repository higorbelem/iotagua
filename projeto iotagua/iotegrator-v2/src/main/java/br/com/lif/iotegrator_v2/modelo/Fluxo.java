package br.com.lif.iotegrator_v2.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Fluxo")
public class Fluxo {
	
	private double value;
	private String nome;
	private long id;
	private long idSensor;
	private String local;
	
	public Fluxo(double value, String nome, long id, Long idSensor, String local) {
		super();
		this.value = value;
		this.nome = nome;
		this.id = id;
		this.idSensor = idSensor;
		this.local = local;
	}

	public Fluxo(double value, long id, long idSensor) {
		super();
		this.value = value;
		this.id = id;

		this.idSensor = idSensor;
	}
	
	public Fluxo() {
		
	}

	@Column(name = "value", nullable = false, length = 50)
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Column(name = "nome", nullable = false, length = 50)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "local", nullable = false, length = 50)
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
	@Column(name = "idSensor", nullable = false, length = 50)
	public long getIdSensor() {
		return idSensor;
	}

	public void setIdSensor(long idSensor) {
		this.idSensor = idSensor;
	}
	
	
	
}
