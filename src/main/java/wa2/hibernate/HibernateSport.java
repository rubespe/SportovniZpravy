package wa2.hibernate;

/**
 * Created by Petr on 15.6.2015.
 */

import org.hibernate.Session;
import org.hibernate.Transaction;
import wa2.Entity.Sport;

import java.util.ArrayList;
import java.util.List;

public class HibernateSport {

    public static void saveCategory(Sport c){
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

    public static void saveOrUpdateCategory(Sport c){
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


    public static List getAllCategories(){
        final Session session = HibernateSessionFactory.getSession();
        List cars = new ArrayList<Sport>();
        try {

            Transaction tr = session.beginTransaction();
            cars = session.createQuery("FROM Sport").list();
            session.flush();

            tr.commit();

        } finally {
            session.close();
        }

        return cars;

    }

    public static Sport getCategoryById(int id){
        final Session session = HibernateSessionFactory.getSession();
        Sport c= null;
        Transaction tr = session.beginTransaction();
        try {


            c = (Sport) session.createQuery("FROM Sport where id = '" + id + "'").list().get(0);
            session.flush();

            tr.commit();

        }catch (IndexOutOfBoundsException e){

            tr.rollback();
        } finally {
            session.close();
        }

        return c;

    }

    public static Sport getCategoryByName(String name){
        final Session session = HibernateSessionFactory.getSession();
        Sport c= null;
        Transaction tr = session.beginTransaction();
        try {


            c = (Sport) session.createQuery("FROM Sport where name = '" + name + "'").list().get(0);
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
