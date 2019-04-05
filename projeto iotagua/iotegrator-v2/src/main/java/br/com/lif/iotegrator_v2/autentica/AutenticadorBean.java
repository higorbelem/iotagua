package br.com.lif.iotegrator_v2.autentica;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.lif.iotegrator_v2.utils.jsf.SessionUtil;

@RequestScoped
@ManagedBean
public class AutenticadorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String senha;

	public String autentica() {
		System.out.println("autentica..");
			
		if (email.equals("admin")&&senha.equals("admin")) {
			System.out.println("Confirmou  usuario e senha ...");		
			
			//ADD USUARIO NA SESSION
			
			Object b = new Object();
			
			SessionUtil.setParam("USUARIOLogado", b);
			
		return "/home.xhtml?faces-redirect=true";

		} else {
			
			return null;

		}

	}

	/**
	 * M�todo que efetua o logout
	 * 
	 * @return
	 */
	public String registraSaida() {

		//REMOVER USUARIO DA SESSION
		
		
		return "page/index.html?faces-redirect=true";
	}

	// GETTERS E SETTERS


	public String getSenha() {
		return senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
