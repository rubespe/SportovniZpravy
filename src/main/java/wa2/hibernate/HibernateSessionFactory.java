package wa2.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateSessionFactory {

    private static final SessionFactory sf;
    private static final ServiceRegistry sr;

    public static Session getSession() throws HibernateException {
        return sf.openSession();
    }

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            sr = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sf = configuration.buildSessionFactory(sr);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

}
