package main.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.inject.Inject;

public class JeuDEchec implements Runnable{

    private CardLayout cards;

    private JPanel cardHolder;

    private JPanel menu;

    private PopUpDeDebut debut;

    public static String cheminIcons = "icons/";

    private Partie partie;

    private ChessGameActionListener chessGameActionListener;
    
    private PartiePanel partiePanel;

    @Inject
    public JeuDEchec(Partie partie, JFrame fenetre, PopUpDeDebut popUpDeDebut, ChessGameActionListener chessGameActionListener, PartiePanel partiePanel) {
	this.debut = popUpDeDebut;
	this.partie = partie;
	this.chessGameActionListener = chessGameActionListener;
	this.chessGameActionListener.setJeu(this);
	cards = new CardLayout();
	menu = createMenu();
	cardHolder = new JPanel();
	fenetre.setTitle("Jeu d'Echec");
	fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
	fenetre.setMinimumSize(new Dimension(500, 500));
	fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	fenetre.setVisible(true);
	fenetre.add(cardHolder);
	cardHolder.setLayout(cards);
	cardHolder.add(menu, ChessGameActionListener.MENU);
	cardHolder.add(this.reprendreMenu(), ChessGameActionListener.REPRENDRE_MENU);
	cardHolder.add(partie, ChessGameActionListener.PARTIE);
	this.partiePanel = partiePanel;
	cardHolder.add(partiePanel, ChessGameActionListener.CHARGER_PARTIE);
	cards.first(cardHolder);
    }

    private JPanel createMenu() {
	JPanel menu = new JPanel();
	menu.setName("Menu");
	menu.setVisible(true);
	menu.setOpaque(true);
	menu.setBackground(Color.BLACK);
	menu.setLayout(new MenuLayout());
	menu.add(this.chessGameActionListener.createButton(ChessGameActionListener.NOUVELLE_PARTIE));
	menu.add(this.chessGameActionListener.createButton(ChessGameActionListener.NOUVELLE_PARTIE_C_O));
	menu.add(this.chessGameActionListener.createButton(ChessGameActionListener.CHARGER_PARTIE));
	menu.add(this.chessGameActionListener.createButton(ChessGameActionListener.QUITTER));
	return menu;
    }

    private JPanel reprendreMenu() {
	JPanel menu = new JPanel();
	menu.setName("Menu");
	menu.setVisible(true);
	menu.setOpaque(true);
	menu.setBackground(Color.BLACK);
	menu.setLayout(new MenuLayout());
	menu.add(this.chessGameActionListener.createButton(ChessGameActionListener.REPRENDRE_PARTIE));
	menu.add(this.chessGameActionListener.createButton(ChessGameActionListener.NOUVELLE_PARTIE));
	menu.add(this.chessGameActionListener.createButton(ChessGameActionListener.NOUVELLE_PARTIE_C_O));
	menu.add(this.chessGameActionListener.createButton(ChessGameActionListener.CHARGER_PARTIE));
	menu.add(this.chessGameActionListener.createButton(ChessGameActionListener.QUITTER));
	return menu;
    }

    public void show(String card) {
	cards.show(cardHolder, card);
    }

    public void nouvellePartie(boolean b) {
	if (this.debut.ouvrir(b)) {
	    cards.show(cardHolder, ChessGameActionListener.PARTIE);
	}
    }

    public void charger(main.model.Partie partieCharger) {
	try {
	    this.partie.chargerPartie(partieCharger);

	} catch (FileNotFoundException e1) {
	    //JOptionPane.showMessageDialog(this, e1.getMessage());
	    e1.printStackTrace();
	}
	this.show(ChessGameActionListener.PARTIE);
    }

    public void displayPartiePanel() {
	this.partiePanel.refreshParties();
	this.show(ChessGameActionListener.CHARGER_PARTIE);
    }

    @Override
    public void run() {
	// TODO Auto-generated method stub
	
    }

}
