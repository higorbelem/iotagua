package br.com.middleware.iot.controllers;

import br.com.middleware.iot.dao.UsuarioDAO;
import br.com.middleware.iot.domain.AtualizarLog;
import br.com.middleware.iot.domain.Usuario;
import br.com.middleware.iot.status.Status;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("crud-Usuario")
public class UsuarioController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<Usuario> listUsuario() {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            return usuarioDAO.listar();
            
        } catch (Exception ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public Usuario getChamado(@PathParam("id") long id) {
        try {
            UsuarioDAO uDAO = new UsuarioDAO();
            return uDAO.buscar(id);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response create(Usuario u) {
        try {
            UsuarioDAO uDAO = new UsuarioDAO();
            uDAO.salvar(u);
            u.setStatus(Status.NOVO);
            
            
            AtualizarLog atualizarLog = new AtualizarLog();
            atualizarLog.setDescricao("Novo Usuario foi instaciando");
            atualizarLog.setData(new Date());
            atualizarLog.setHorario(new Date()); 
            
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(Usuario u) {
        try {
            //ValvulaSolenoide.setStatus(Status.PENDENTE);

            UsuarioDAO uDAO = new UsuarioDAO();
            uDAO.editar(u);
            
            u.setStatus(Status.PENDENTE);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") long id) {
        try {
            
            UsuarioDAO uDAO = new UsuarioDAO();
            Usuario u = uDAO.buscar(id);
            
            uDAO.excluir(u);
            
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PUT
    @Path("{id}/")
    public Response concluir(@PathParam("id") long id) {
        try {
            UsuarioDAO uDAO = new UsuarioDAO();
            Usuario u = uDAO.buscar(id);
            u.setStatus(Status.FECHADO);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
