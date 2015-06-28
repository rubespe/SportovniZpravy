package wa2.hibernate;



import org.hibernate.Session;
import org.hibernate.Transaction;
import wa2.Entity.Sport;
import wa2.Entity.Zapas;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by Petr on 15.6.2015.
 */
public class HibernateZapas {

    public static void saveEvent(Zapas e){
        final Session session = HibernateSessionFactory.getSession();
        try {

            Transaction tr = session.beginTransaction();
            session.save(e);
            session.flush();

            tr.commit();


        } finally {
            session.close();
        }


    }

    public static int saveEventWithC(Zapas e, List<Integer> cat){
        final Session session = HibernateSessionFactory.getSession();
        try {

            Transaction tr = session.beginTransaction();
            Set<Sport> listC = e.getCategories();
            for(int id: cat){
                Sport c = HibernateSport.getCategoryById(id);
                listC.add(c);
            }
            e.setCategories(listC);
            session.saveOrUpdate(e);
            session.flush();

            tr.commit();


        }catch(Exception ex){
            return -1;
        }
        finally {
            session.close();
        }

    return 1;
    }


    public static List getAllEvents(){
        final Session session = HibernateSessionFactory.getSession();
        List cars = new ArrayList<Sport>();
        try {

            Transaction tr = session.beginTransaction();
            cars = session.createQuery("FROM Zapas").list();
            session.flush();

            tr.commit();

        } finally {
            session.close();
        }

        return cars;

    }

    public static List getAllEventsForCategory(int categoryId){
        final Session session = HibernateSessionFactory.getSession();
        List events = new ArrayList<Zapas>();
        try {

            Transaction tr = session.beginTransaction();
            events = session.createQuery(
                    "SELECT s FROM Sport s1 " +
                            "join s1.zapasy as s " +
                            "where s1.id = " + categoryId).list();
            session.flush();

            tr.commit();

        } finally {
            session.close();
        }

        return events;

    }

    public static Zapas getEventById(int id){
        final Session session = HibernateSessionFactory.getSession();
        Zapas c= null;
        Transaction tr = session.beginTransaction();
        try {


            c = (Zapas) session.createQuery("FROM Zapas where id = '" + id + "'").list().get(0);
            session.flush();

            tr.commit();

        }catch (IndexOutOfBoundsException e){

            tr.rollback();
        } finally {
            session.close();
        }

        return c;

    }





}
