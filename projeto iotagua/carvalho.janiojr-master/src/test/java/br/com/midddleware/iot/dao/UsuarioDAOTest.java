/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.midddleware.iot.dao;

import br.com.middleware.iot.dao.UsuarioDAO;
import br.com.middleware.iot.domain.Usuario;
import br.com.middleware.iot.status.Status;
import br.com.middleware.iot.status.Tipo;
import java.util.Date;
import org.junit.Test;

/**
 *
 * @author Janio
 */

public class UsuarioDAOTest {
    @Test
    public void salvar(){
        
        Usuario usuario = new Usuario();
        usuario.setLogin("AdminIot");
        usuario.setSenha("adminiot");
        usuario.setEndereco("Rua x");
        usuario.setFormacao("X");
        usuario.setData(new Date());
        usuario.setMatriculaFuncional("201620039-x");
        usuario.setMatricula("201620039");
        usuario.setNome("Janio");
        usuario.setSetor("Exatas");
        usuario.setStatus(Status.FECHADO);
        usuario.setTipo(Tipo.ADMIN);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
       
        usuarioDAO.salvar(usuario);
        
    }
}
