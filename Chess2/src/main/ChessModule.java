package main;

import com.google.inject.AbstractModule;

import main.DAO.PartieDAO;
import main.factory.HibernateUtil;
import main.service.PartieService;
import main.ui.Partie;
import main.ui.PopUpDeDebut;

import javax.swing.JFrame;

import org.hibernate.SessionFactory;

public class ChessModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SessionFactory.class).toInstance(HibernateUtil.getSessionFactory());
        bind(PartieDAO.class);
        bind(PartieService.class);
        bind(Partie.class).asEagerSingleton();
        bind(JFrame.class).asEagerSingleton();
        bind(PopUpDeDebut.class);
    }
}
