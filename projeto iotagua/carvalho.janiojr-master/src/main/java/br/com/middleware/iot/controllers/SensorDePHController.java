package br.com.middleware.iot.controllers;

import br.com.middleware.iot.dao.SensorDePHDAO;
import br.com.middleware.iot.domain.AtualizarLog;
import br.com.middleware.iot.domain.SensorDePH;
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

@Path("crud-Pessoa")
public class SensorDePHController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<SensorDePH> listPH() {
        try {
            SensorDePHDAO phDAO = new SensorDePHDAO();
            return phDAO.listar();

        } catch (Exception ex) {
            Logger.getLogger(SensorDePHController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public SensorDePH getChamado(@PathParam("id") long id) {
        try {
            SensorDePHDAO phDAO = new SensorDePHDAO();
            return phDAO.buscar(id);
        } catch (Exception ex) {
            Logger.getLogger(SensorDePHController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
 @POST
    @Path("/{id},{historico}, {local_operacao}")
    public Response RFM22(@PathParam("id") long id,
            @PathParam("historico") String historico,
            @PathParam("local_operacao") String local_operacao) {

        AtualizarLog atualizarLog = new AtualizarLog();
        atualizarLog.setHistorico(historico);
        atualizarLog.setDescricao(local_operacao);
        atualizarLog.setData(new Date());
        atualizarLog.setHorario(new Date());

        return Response.status(Response.Status.OK).build();
    } 

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(SensorDePH ph) {
        try {
            //ValvulaSolenoide.setStatus(Status.PENDENTE);

            SensorDePHDAO phDAO = new SensorDePHDAO();
            phDAO.editar(ph);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(SensorDePHController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") long id) {
        try {

            SensorDePHDAO phDAO = new SensorDePHDAO();
            SensorDePH ph = phDAO.buscar(id);

            phDAO.excluir(ph);

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(SensorDePHController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("{id}/")
    public Response concluir(@PathParam("id") long id) {
        try {
            SensorDePHDAO phDAO = new SensorDePHDAO();
            SensorDePH ph = phDAO.buscar(id);

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(SensorDePHController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
