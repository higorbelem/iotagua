package br.com.middleware.iot.controllers;

import br.com.middleware.iot.dao.SNADAO;
import br.com.middleware.iot.domain.AtualizarLog;
import br.com.middleware.iot.domain.SNA;
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

@Path("crud-SNA")
public class SNAController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<SNA> listSNA() {
        try {
            SNADAO snaDAO = new SNADAO();
            return snaDAO.listar();

        } catch (Exception ex) {
            Logger.getLogger(SNAController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public SNA getChamado(@PathParam("id") long id) {
        try {
            SNADAO snaDAO = new SNADAO();
            return snaDAO.buscar(id);
        } catch (Exception ex) {
            Logger.getLogger(SNAController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response create(SNA sna) {
        try {
            SNADAO snaDAO = new SNADAO();
            snaDAO.salvar(sna);
            
            
            AtualizarLog atualizarLog = new AtualizarLog();
            atualizarLog.setDescricao("Novo Sensor de Nível de água foi instaciando");
            atualizarLog.setData(new Date());
            atualizarLog.setHorario(new Date()); 
            
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(SNAController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(SNA sna) {
        try {
            //ValvulaSolenoide.setStatus(Status.PENDENTE);

            SNADAO snaDAO = new SNADAO();
            snaDAO.editar(sna);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(SNAController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") long id) {
        try {

            SNADAO snaDAO = new SNADAO();
            SNA sna = snaDAO.buscar(id);

            snaDAO.excluir(sna);

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(SNAController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("{id}/")
    public Response concluir(@PathParam("id") long id) {
        try {
            SNADAO snaDAO = new SNADAO();
            SNA sna = snaDAO.buscar(id);

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(SNAController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
