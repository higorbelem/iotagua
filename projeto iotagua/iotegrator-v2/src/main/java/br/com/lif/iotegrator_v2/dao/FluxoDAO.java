package br.com.lif.iotegrator_v2.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.com.lif.iotegrator_v2.modelo.Fluxo;
import br.com.lif.iotegrator_v2.utils.HibernateUtil;

public class FluxoDAO {
	public void add(Fluxo fluxo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(fluxo);
        session.getTransaction().commit();
        session.close();
        //HibernateUtil.shutdown();	
	}
	
	public Fluxo search(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
        try {
            TypedQuery<Fluxo> query = session.createQuery("FROM Fluxo WHERE id = " + id);
            List<Fluxo> list = query.getResultList();
            session.close();
            return list.get(0);
            //return Response.ok().entity(new Gson().toJson(studentList)).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
        //return Response.status(Response.Status.NO_CONTENT).build();
	}
}
