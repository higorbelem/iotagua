package br.com.lif.iotegrator_v2.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
/**
 * Created by hamishdickson on 28/10/14.
 *
 */
public class HibernateUtil {
    private static SessionFactory SESSION_FACTORY = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
        	Configuration configuration = new Configuration().configure();
        	SessionFactory sessionFactory = configuration.buildSessionFactory();
        	/*ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        	SessionFactory sessionFactory= new Configuration().buildSessionFactory(serviceRegistry);*/
            return sessionFactory;
        } catch (Throwable ex) {
            System.err.println("Session factory configuration failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}