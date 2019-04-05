package br.com.middleware.iot.controllers;

import br.com.middleware.iot.dao.SFADAO;
import br.com.middleware.iot.domain.AtualizarLog;
import br.com.middleware.iot.domain.SFA;
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

@Path("crud-SFA")
public class SFAController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<SFA> listSFA() {
        try {
            SFADAO sfaDAO = new SFADAO();
            return sfaDAO.listar();

        } catch (Exception ex) {
            Logger.getLogger(SFAController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public SFA getChamado(@PathParam("id") long id) {
        try {
            SFADAO sfaDAO = new SFADAO();
            return sfaDAO.buscar(id);
        } catch (Exception ex) {
            Logger.getLogger(SFAController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response create(SFA sfa) {
        try {
            SFADAO sfaDAO = new SFADAO();
            sfaDAO.salvar(sfa);
            
            
            AtualizarLog atualizarLog = new AtualizarLog();
            atualizarLog.setDescricao("Novo Sensor de Fluxo de água foi instaciando");
            atualizarLog.setData(new Date());
            atualizarLog.setHorario(new Date()); 
            
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(SFAController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(SFA sfa) {
        try {
            //ValvulaSolenoide.setStatus(Status.PENDENTE);

            SFADAO sfaDAO = new SFADAO();
            sfaDAO.editar(sfa);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(SFAController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") long id) {
        try {

            SFADAO sfaDAO = new SFADAO();
            SFA sfa = sfaDAO.buscar(id);

            sfaDAO.excluir(sfa);

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(SFAController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("{id}/")
    public Response concluir(@PathParam("id") long id) {
        try {
            SFADAO sfaDAO = new SFADAO();
            SFA sfa = sfaDAO.buscar(id);

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(ValvulaSolenoideController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
