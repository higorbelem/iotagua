/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.middleware.iot.Bean;

import br.com.middleware.iot.dao.UsuarioDAO;
import br.com.middleware.iot.domain.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;

/**
 *
 * @author Janio
 */
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

    private Usuario usuario;
    private List<Usuario> usuarios;

    public void salvar() {
        
    }

    public void excluir(ActionEvent evento) {
        try {
            usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.excluir(usuario);

            usuarios = usuarioDAO.listar();
            Messages.addGlobalInfo("Removido com sucesso!");
        } catch (RuntimeException e) {
            Messages.addGlobalError("Erro ao excluir");
            e.printStackTrace();
        }
    }

    public void editar() {

    }

    @PostConstruct
    public void listar() {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarios = usuarioDAO.listar();
        } catch (Exception e) {
            Messages.addGlobalError("Erro ao listar as cidades");
            e.printStackTrace();
        }

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    
}
