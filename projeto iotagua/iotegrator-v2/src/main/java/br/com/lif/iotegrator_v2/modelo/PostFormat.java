package br.com.lif.iotegrator_v2.modelo;

import java.util.ArrayList;


public class PostFormat {
	
	int idm;
	int ids;
	int idt;
	char tipo;
	String timeStamp;
	int nVar;
	String community;
	ArrayList<Value> medicoes;
	
	public PostFormat() {

	}
	
	public int getIdm() {
		return idm;
	}
	public void setIdm(int idm) {
		this.idm = idm;
	}
	public int getIds() {
		return ids;
	}
	public void setIds(int ids) {
		this.ids = ids;
	}
	public int getIdt() {
		return idt;
	}
	public void setIdt(int idt) {
		this.idt = idt;
	}
	public char getTipo() {
		return tipo;
	}
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getnVar() {
		return nVar;
	}
	public void setnVar(int nVar) {
		this.nVar = nVar;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public ArrayList<Value> getMedicoes() {
		return medicoes;
	}
	public void setMedicoes(ArrayList<Value> medicoes) {
		this.medicoes = medicoes;
	}
	
	

}
