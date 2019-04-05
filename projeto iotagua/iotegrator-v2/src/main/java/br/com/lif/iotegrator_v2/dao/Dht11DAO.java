package br.com.lif.iotegrator_v2.dao;


import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import br.com.lif.iotegrator_v2.modelo.Dht11;
import br.com.lif.iotegrator_v2.utils.HibernateUtil;

public class Dht11DAO {
	
	public void add(Dht11 dht11) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(dht11);
        session.getTransaction().commit();
        session.close();
        //HibernateUtil.shutdown();	
	}
	
	public Dht11 search(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
        try {
            TypedQuery<Dht11> query = session.createQuery("FROM Dht11 WHERE id = " + id);
            List<Dht11> list = query.getResultList();
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
