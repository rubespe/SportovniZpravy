package wa2.hibernate;

/**
 * Created by Petr on 15.6.2015.
 */

import org.hibernate.Session;
import org.hibernate.Transaction;
import wa2.Entity.Sport;
import wa2.Entity.Udalost;

import java.util.ArrayList;
import java.util.List;

public class HibernateUdalost {

    public static void saveMessage(Udalost c){
        final Session session = HibernateSessionFactory.getSession();
        try {

            Transaction tr = session.beginTransaction();
            session.save(c);
            session.flush();

            tr.commit();


        } finally {
            session.close();
        }


    }

    public static void saveOrUpdateMessage(Udalost c){
        final Session session = HibernateSessionFactory.getSession();
        try {

            Transaction tr = session.beginTransaction();
            session.saveOrUpdate(c);
            session.flush();

            tr.commit();


        } finally {
            session.close();
        }


    }


    public static List getAllMessages(){
        final Session session = HibernateSessionFactory.getSession();
        List cars = new ArrayList<Sport>();
        try {

            Transaction tr = session.beginTransaction();
            cars = session.createQuery("FROM Udalost").list();
            session.flush();

            tr.commit();

        } finally {
            session.close();
        }

        return cars;

    }

    public static Udalost getMessageById(int id){
        final Session session = HibernateSessionFactory.getSession();
        Udalost c= null;
        Transaction tr = session.beginTransaction();
        try {


            c = (Udalost) session.createQuery("FROM Udalost where id = " + id).list().get(0);
            session.flush();

            tr.commit();

        }catch (IndexOutOfBoundsException e){

            tr.rollback();
        } finally {
            session.close();
        }

        return c;

    }

    public static Udalost getMessageBiggerId(int id, int zapasId){
        final Session session = HibernateSessionFactory.getSession();
        Udalost c= null;
        Transaction tr = session.beginTransaction();
        try {


            c = (Udalost) session.createQuery("FROM Udalost u where u.zapas =  " + zapasId +" and u.id > " + id ).list().get(0);
            session.flush();

            tr.commit();

        }catch (IndexOutOfBoundsException e){

            tr.rollback();
        } finally {
            session.close();
        }

        return c;

    }


    public static List getAllMessagesForEvent(int zapasId){
        final Session session = HibernateSessionFactory.getSession();
        List messages = new ArrayList<Udalost>();
        try {

            Transaction tr = session.beginTransaction();
            messages = session.createQuery(
                    "FROM Udalost u " +
                            "where u.zapas = " + zapasId).list();
            session.flush();

            tr.commit();

        } finally {
            session.close();
        }

        return messages;

    }

    public static List getAllMessagesForSport(int sportId){
        final Session session = HibernateSessionFactory.getSession();
        List<Udalost> messages = new ArrayList<Udalost>();
        try {

            Transaction tr = session.beginTransaction();

            messages = session.createQuery("FROM Udalost u join u.zapas z join z.categories c where c.id=" + sportId).list();
            session.flush();

            tr.commit();

        } finally {
            session.close();
        }

        for(int i=0; i<messages.size(); i++){
            System.out.println(messages.get(i).getText());
        }

        return messages;

    }


}
