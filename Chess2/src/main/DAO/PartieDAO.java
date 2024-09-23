package main.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.inject.Inject;

import main.model.Partie;

public class PartieDAO {

    private final SessionFactory sessionFactory;

    @Inject
    public PartieDAO(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    public boolean save(Partie partie) {
	Session session = sessionFactory.openSession();
	Transaction tx = null;
	try {
	    tx = session.beginTransaction();
	    session.saveOrUpdate(partie);
	    tx.commit();
	    return true;
	} catch (HibernateException e) {
	    if (tx != null)
		tx.rollback();
	    e.printStackTrace();
	    return false;
	} finally {
	    session.close();
	}
    }
    
    public boolean delete(Partie partie) {
	Session session = sessionFactory.openSession();
	Transaction tx = null;
	try {
	    tx = session.beginTransaction();
	    session.delete(partie);
	    tx.commit();
	    return true;
	} catch (HibernateException e) {
	    if (tx != null)
		tx.rollback();
	    e.printStackTrace();
	    return false;
	} finally {
	    session.close();
	}
    }

    public Partie findById(Long id) {
	Session session = sessionFactory.openSession();
	Partie partie = null;
	try {
	    partie = session.get(Partie.class, id);
	} finally {
	    session.close();
	}
	return partie;
    }

    public List<Partie> findAll() {
	Session session = sessionFactory.openSession();
	List<Partie> games = null;
	try {
	    games = session.createQuery("FROM Partie", Partie.class).list();
	} finally {
	    session.close();
	}
	return games;
    }

    public List<String> getAllNames() {
	Session session = sessionFactory.openSession();
	List<String> games = null;
	try {
	    games = session.createQuery("SELECT name FROM Partie", String.class).list();
	} finally {
	    session.close();
	}
	return games;
    }
}
