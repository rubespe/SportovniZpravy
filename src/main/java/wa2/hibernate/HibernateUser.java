package wa2.hibernate;



import org.hibernate.Session;
import org.hibernate.Transaction;
import wa2.Entity.User;
import wa2.Entity.TomcatUser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateUser {

    public static void saveUser(User u){
        final Session session = HibernateSessionFactory.getSession();
        try {
            System.out.println(u.getUser_name());
            Logger.getLogger("MyLog").log(Level.ALL,u.getUser_name()+"bla");
            Transaction tr = session.beginTransaction();
            session.save(u);
            TomcatUser ur = new TomcatUser(u.getUser_name(),u.getUser_role());
            session.save(ur);
            session.flush();

            tr.commit();


        } finally {
            session.close();
        }


    }

    public static User getUserByLogin(String name){
        final Session session = HibernateSessionFactory.getSession();
        User c = null;
        Transaction tr = session.beginTransaction();
        try {


            c = (User) session.createQuery("FROM User where user_name = '" + name +  "'").list().get(0);
            session.flush();

            tr.commit();

        }catch (IndexOutOfBoundsException e){
            System.out.println("No data for user " + name);
            tr.rollback();
        } finally {
            session.close();
        }

        return c;

    }

}
