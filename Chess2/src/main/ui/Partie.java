package main.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.inject.Inject;

import main.chess.Coordonnee;
import main.chess.Coup;
import main.chess.Echiquier;
import main.chess.Piece;
import main.chess.Promotion;
import main.ia.Ordinateur;
import main.service.PartieService;

public final class Partie extends JPanel implements Runnable, ComponentListener {

    private Plateau plateau;

    public static int taille = 8;

    private Ordinateur ordinateur;

    private static String messageVictoireBlanc = "Les Blancs ont gagn�s!!!";

    private static String messageVictoireNoir = "Les Noirs ont gagn�s!!!";

    private static String messagePat = "Il y a Pat, c'est au tour d'un joueur mais il ne peut pas jouer et il n'est pas en echec. \nPersonne ne remporte la partie.";

    private Icon iconVictoireBlanc;

    private Icon iconVictoireNoir;

    private Icon iconPat;

    private boolean finie;

    private Thread tourOrdinateur;

    private String name;

    private PartieService partieService;

    private main.model.Partie partie;

    private MovesDisplayer movesDisplayer;
    
    private PartieMenu menu;

    @Inject
    public Partie(PartieService partieService, ChessGameActionListener chessGameActionListener) {
	this.partieService = partieService;
	this.setLayout(new PartieLayout());
	GameLogicListener listener = new GameLogicListener(this);
	this.plateau = new Plateau(listener);
	this.movesDisplayer = new MovesDisplayer();
	this.add(movesDisplayer);
	this.add(this.plateau);
	chessGameActionListener.setPartie(this);
	this.menu = new PartieMenu(chessGameActionListener);
	this.add(menu);
	this.setOpaque(true);
	addComponentListener(this);
	this.name = null;
	this.finie = true;
    }

    public void nouvellePartie(String name) throws FileNotFoundException {
	this.name = name;
	this.finie = false;
	this.plateau.initPlateau(new Echiquier());
	this.movesDisplayer.update("");
	this.ordinateur = null;
	this.partie = new main.model.Partie();
	this.partie.setName(name);

	this.menu.setComputer(false);
    }

    public void nouvellePartie(int niveau, boolean couleurOrdinateur, String name) throws FileNotFoundException {
	this.nouvellePartie(name);
	this.menu.setComputer(true);
	this.ordinateur = new Ordinateur(niveau, couleurOrdinateur, this.plateau.getEchiquier(), true);
	if (couleurOrdinateur) {
	    Thread tourOrdinateur = new Thread(this);
	    tourOrdinateur.start();
	}
	this.plateau.setEnAttente(false);
    }

    public void chargerPartie(main.model.Partie partie) throws FileNotFoundException {
	this.name = partie.getName();
	this.finie = partie.isFinie();
	int finDePartie = Echiquier.PARTIE_CONTINUE;
	Echiquier echiquier = new Echiquier(partie.getFen());
	
	if (!partie.getComputer().equals("Joueur contre joueur")) {
	    int niveau = Integer.parseInt("" + partie.getComputer().charAt(partie.getComputer().length() - 1));
	    boolean couleurOrdinateur = false;
	    if (partie.getComputer().contains("Blanc")) {
		couleurOrdinateur = true;
	    }
	    this.menu.setComputer(true);
	    this.ordinateur = new Ordinateur(niveau, couleurOrdinateur, echiquier, true);
	} else {
	    this.menu.setComputer(false);
	    this.ordinateur = null;
	}

	this.plateau.initPlateau(echiquier);
	this.movesDisplayer.update(this.plateau.getEchiquier().toString());
	this.finPartie(finDePartie);
    }

    public void displayGraph() {
	this.ordinateur.displayGraph();
    }
    
    public boolean isFinie() {
	return finie;
    }

    private void joueOrdinateur() {

	this.plateau.setEnAttente(true);
	Coup coupOrdinateur = this.ordinateur.joue();
	int fin;
	if (this.plateau.isEnAttente()) {
	    fin = this.plateau.joue(coupOrdinateur);
	    this.sauvegarder();
	    this.plateau.updatePlateau();
	    this.repaint();
	    this.finPartie(fin);
	    this.plateau.setEnAttente(false);
	}
    }

    public void annuler() {
	if (this.ordinateur != null) {
	    if (this.plateau.isEnAttente()) {
		this.plateau.setEnAttente(false);
	    } else {
		this.plateau.annuler();
	    }
	}
	this.plateau.annuler();

	this.movesDisplayer.update(this.plateau.getEchiquier().toString());
    }

    public void sauvegarder() {

	if (name != null) {
	    String computer = "Joueur contre joueur";
	    if (this.ordinateur != null) {
		computer = this.ordinateur.isCouleur() ? "Ordinateur Blanc "
			: "Ordinateur Noir " + this.ordinateur.getNiveau();
	    }
	    this.partieService.save(this.name, computer, this.isFinie(), this.plateau.getEchiquier().getFEN());
	}
    }

    public void finPartie(int finDePartie) {
	this.movesDisplayer.update(this.plateau.getEchiquier().toString());
	switch (finDePartie) {
	case Echiquier.BLANC_GAGNE:
	    JOptionPane.showMessageDialog(this, this.messageVictoireBlanc, "Victoire des Blancs!!!",
		    JOptionPane.INFORMATION_MESSAGE, this.iconVictoireBlanc);
	    this.finie = true;
	    break;
	case Echiquier.NOIR_GAGNE:
	    JOptionPane.showMessageDialog(this, this.messageVictoireNoir, "Victoire des Noirs!!!",
		    JOptionPane.INFORMATION_MESSAGE, this.iconVictoireNoir);
	    this.finie = true;
	    break;
	case Echiquier.PAT:
	    JOptionPane.showMessageDialog(this, this.messagePat, "Pat", JOptionPane.INFORMATION_MESSAGE, this.iconPat);
	    this.finie = true;
	    break;
	case Echiquier.PARTIE_CONTINUE:
	    if (this.ordinateur != null && this.plateau.getEchiquier().isTour() == this.ordinateur.isCouleur()) {
		tourOrdinateur = new Thread(this);
		tourOrdinateur.start();
	    }
	    break;
	default:// L'echiquier n'a pas �t� modifi� soit aucun coup n'a �t�
		// jou� soit une
		// promotion de pion est en cours
	    if (this.plateau.isInPromotion()) {
		this.plateau.afficherChoixPromotion();
	    }
	    break;
	}
    }

    @Override
    public void run() {
	this.joueOrdinateur();
    }

    @Override
    public void componentResized(ComponentEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void componentMoved(ComponentEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void componentShown(ComponentEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void componentHidden(ComponentEvent e) {
	if (this.name != null)
	    this.sauvegarder();
    }

}
