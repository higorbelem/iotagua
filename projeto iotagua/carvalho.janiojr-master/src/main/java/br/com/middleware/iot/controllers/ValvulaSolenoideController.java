package br.com.middleware.iot.controllers;

import br.com.middleware.iot.dao.ValvulaSolenoideDAO;
import br.com.middleware.iot.domain.AtualizarLog;
import br.com.middleware.iot.domain.ValvulaSolenoide;
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

@Path("crud-ValvulaSolenoide")
public class ValvulaSolenoideController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<ValvulaSolenoide> listValvula() {
        try {
            ValvulaSolenoideDAO v = new ValvulaSolenoideDAO();
            return v.listar();

        } catch (Exception ex) {
            Logger.getLogger(ValvulaSolenoideController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public ValvulaSolenoide getChamado(@PathParam("id") long id) {
        try {
            ValvulaSolenoideDAO vDAO = new ValvulaSolenoideDAO();
            return vDAO.buscar(id);
        } catch (Exception ex) {
            Logger.getLogger(ValvulaSolenoideController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response create(ValvulaSolenoide v) {
        try {
            ValvulaSolenoideDAO vDAO = new ValvulaSolenoideDAO();
            vDAO.salvar(v);
            
            
            AtualizarLog atualizarLog = new AtualizarLog();
            atualizarLog.setDescricao("Novo Valvula Solenoide instaciando");
            atualizarLog.setData(new Date());
            atualizarLog.setHorario(new Date()); 
            
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(ValvulaSolenoideController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(ValvulaSolenoide v) {
        try {
            //ValvulaSolenoide.setStatus(Status.PENDENTE);

            ValvulaSolenoideDAO vDAO = new ValvulaSolenoideDAO();
            vDAO.editar(v);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(ValvulaSolenoideController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") long id) {
        try {

            ValvulaSolenoideDAO vDAO = new ValvulaSolenoideDAO();
            ValvulaSolenoide v = vDAO.buscar(id);

            vDAO.excluir(v);

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(ValvulaSolenoideController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("{id}/")
    public Response concluir(@PathParam("id") long id) {
        try {
            ValvulaSolenoideDAO vDAO = new ValvulaSolenoideDAO();
            ValvulaSolenoide v = vDAO.buscar(id);

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(ValvulaSolenoideController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
