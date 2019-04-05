package br.com.middleware.iot.controllers;

import br.com.middleware.iot.dao.BombadaAguaDAO;
import br.com.middleware.iot.domain.AtualizarLog;
import br.com.middleware.iot.domain.BombadaAgua;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.Path;

@Path("crud-BombadaAgua")
public class BombadaAguaController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<BombadaAgua> listBomba() {
        try {
            BombadaAguaDAO bAguaDAO = new BombadaAguaDAO();
            return bAguaDAO.listar();

        } catch (Exception ex) {
            Logger.getLogger(BombadaAguaController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public BombadaAgua getChamado(@PathParam("id") long id) {
        try {
            BombadaAguaDAO bAguaDAO = new BombadaAguaDAO();
            return bAguaDAO.buscar(id);
        } catch (Exception ex) {
            Logger.getLogger(BombadaAguaController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response create(BombadaAgua bAgua) {
        try {                      
            BombadaAguaDAO bAguaDAO = new BombadaAguaDAO();
            bAguaDAO.salvar(bAgua);
            
            
            AtualizarLog atualizarLog = new AtualizarLog();
            atualizarLog.setDescricao("Novo acionador de relé foi instaciando");
            atualizarLog.setData(new Date());
            atualizarLog.setHorario(new Date()); 
            
            return Response.status(Response.Status.OK).build();
            
        } catch (Exception ex) {
            Logger.getLogger(BombadaAguaController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            
            
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(BombadaAgua bAgua) {
        try {
            //ValvulaSolenoide.setStatus(Status.PENDENTE);

            BombadaAguaDAO bAguaDAO = new BombadaAguaDAO();
            bAguaDAO.editar(bAgua);

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(BombadaAguaController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") long id) {
        try {

            BombadaAguaDAO bAguaDAO = new BombadaAguaDAO();
            BombadaAgua bAgua = bAguaDAO.buscar(id);

            bAguaDAO.excluir(bAgua);

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(BombadaAguaController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("{id}/")
    public Response concluir(@PathParam("id") long id) {
        try {
            BombadaAguaDAO bAguaDAO = new BombadaAguaDAO();
            BombadaAgua bAgua = bAguaDAO.buscar(id);

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(BombadaAguaController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
