package br.com.lif.iotegrator_v2.modelo;

public class Value {
	
	int idv;
	double valor;
	
	public Value(int idv, double valor) {
		super();
		this.idv = idv;
		this.valor = valor;
	}

	public Value() {

	}
	
	public int getIdv() {
		return idv;
	}
	public void setIdv(int idv) {
		this.idv = idv;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	

}
