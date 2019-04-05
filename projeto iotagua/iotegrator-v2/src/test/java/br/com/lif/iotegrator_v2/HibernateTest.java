package br.com.lif.iotegrator_v2;

import org.hibernate.Session;

import br.com.lif.iotegrator_v2.modelo.Dht11;
import br.com.lif.iotegrator_v2.utils.HibernateUtil;

public class HibernateTest {
	public static void main(String[] args) {

		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Dht11 dht11 = new Dht11(10,10,10,"as",2,"sasas");

        session.save(dht11);
        session.getTransaction().commit();
        session.close();
        
        HibernateUtil.shutdown();
	}
}
