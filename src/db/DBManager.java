package db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import primitives.EmailEntity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import static Utilities.DateUtils.convertToDateFormat;
import static Utilities.DateUtils.getCurrentDate;


public class DBManager
{

    private final SessionFactory factory;

    public DBManager()
    {
        try
        {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex)
        {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public synchronized Integer addEmail(String email)
    {

        List<EmailEntity> searchRes = getEmail(email);
        if(searchRes.size() != 0)
        {
            System.out.println("email: " + email + " has been added previously");
            return -1;
        }

        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try
        {
            tx = session.beginTransaction();

            Timestamp currentDate = getCurrentDate();

            System.out.println("email: " + email + "\t" + convertToDateFormat(currentDate));
            EmailEntity emailEntity = new EmailEntity(email, currentDate);


            employeeID = (Integer) session.save(emailEntity);
            tx.commit();
        } catch (HibernateException e)
        {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally
        {
            session.close();
        }
        return employeeID;
    }

    public void listEmails()
    {
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            List emails = session.createQuery("FROM EmailEntity").list();
            for (Iterator iterator =
                 emails.iterator(); iterator.hasNext(); )
            {
                EmailEntity emailEntity = (EmailEntity) iterator.next();
                System.out.println("id: " + emailEntity.getId() + "\taddress: " + emailEntity.getAddress() + "\ttime inserted: " + convertToDateFormat(emailEntity.getAddedTime()));
            }
            tx.commit();
        } catch (HibernateException e)
        {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally
        {
            session.close();
        }
    }

    public List getEmailEntities()
    {
        List emails = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            emails = session.createQuery("FROM EmailEntity").list();
            tx.commit();
        } catch (HibernateException e)
        {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally
        {
            session.close();
        }
        return emails;
    }

    public List getEmail(String email)
    {
        List emails = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            emails = session.createQuery("FROM EmailEntity where address='" + email + "'").list();
            tx.commit();
        } catch (HibernateException e)
        {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally
        {
            session.close();
        }
        return emails;
    }
}
