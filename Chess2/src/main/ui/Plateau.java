package main.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import main.chess.Coordonnee;
import main.chess.Coup;
import main.chess.Echiquier;
import main.chess.Piece;
import main.chess.Promotion;

public class Plateau extends JPanel {

    private LinkedList<Coup> coupsPossibles;

    private boolean changerDeSens;

    private boolean enAttente;

    private Case[][] tabCase;

    private final int taille = 8;

    private PlateauLayout plateauLayout;

    private Image[] iconPiecesBlanches;

    private Image[] iconPiecesNoires;

    private Image sablier;

    private Echiquier echiquier;

    private Promotion promotion;

    private static String[] tabNomPiece = { "Pion", "Tour", "Cavalier", "Fou", "Reine", "Roi" };

    public Plateau(GameLogicListener listener) {
	super();
	this.plateauLayout = new PlateauLayout(taille, taille);
	plateauLayout.setReversed(false);
	this.setLayout(this.plateauLayout);
	this.setBackground(Color.black);
	listener.setPlateau(this);
	this.tabCase = new Case[taille][taille];
	int i, j;
	for (i = 0; i < taille; i++) {
	    for (j = 0; j < taille; j++) {
		tabCase[j][i] = new Case(new Coordonnee(j, i));
		tabCase[j][i].addMouseListener(listener);
		tabCase[j][i].addMouseMotionListener(listener);
		this.add(tabCase[j][i]);
	    }
	}
    }

    public void initPlateau(Echiquier echiquier) throws FileNotFoundException {
	this.initIcon();
	this.promotion = null;
	this.echiquier = echiquier;
	this.coupsPossibles = new LinkedList<Coup>();
	this.changerDeSens = true;
	this.updatePlateau();
	for (Case tab[] : this.tabCase) {
	    for (Case temp : tab) {
		temp.freeDragIcon();
		temp.freeChoix();
		temp.setDernierCoup(false);
	    }
	}
	this.updateDernierCoup();
    }

    /*
     * Free all temporary icons in all the cases on the chessboard.
     */
    public void freeIcons() {
	for (Case tab[] : this.tabCase) {
	    for (Case temp : tab) {
		temp.freeDragIcon();
		temp.freeChoix();
	    }
	}
    }
    
    /*
     * Start Dragging a piece.
     */
    public void startDragging(Case c) {
	if (!this.coupsPossibles.isEmpty()) {
	    for (Coup coup : this.coupsPossibles) {
		this.getCase(coup.getArrivee()).setSubrillance(false);
	    }
	}
	this.coupsPossibles = this.echiquier.selectionne(c.getCoordonnee());
	c.setDraging(true);
	this.promotion = null;
    }
    
    public void drawDraggedIcon(Image icon, int x, int y, int t) {
	Case coinHG, coinBG, coinHD, coinBD;
	Component compo;

	compo = this.getComponentAt(x - t / 2, y - t / 2);
	if (compo != null && compo.getClass() == Case.class) {
	    coinHG = (Case) compo;
	    coinHG.setDragIcon(icon, x - t / 2 - coinHG.getX(), y - t / 2 - coinHG.getY());
	}
	compo = this.getComponentAt(x - t / 2, y + t / 2);
	if (compo != null && compo.getClass() == Case.class) {
	    coinBG = (Case) compo;
	    coinBG.setDragIcon(icon, x - t / 2 - coinBG.getX(), y - t / 2 - coinBG.getY());
	    // coinBG.repaint();
	}
	compo = this.getComponentAt(x + t / 2, y - t / 2);
	if (compo != null && compo.getClass() == Case.class) {
	    coinHD = (Case) compo;
	    coinHD.setDragIcon(icon, x - t / 2 - coinHD.getX(), y - t / 2 - coinHD.getY());
	    // coinHD.repaint();
	}
	compo = this.getComponentAt(x + t / 2, y + t / 2);
	if (compo != null && compo.getClass() == Case.class) {
	    coinBD = (Case) compo;
	    coinBD.setDragIcon(icon, x - t / 2 - coinBD.getX(), y - t / 2 - coinBD.getY());
	    // coinBD.repaint();
	}
    }

    public void updatePlateau() {
	int i, j;
	Piece temp;
	for (i = 0; i < taille; i++) {
	    for (j = 0; j < taille; j++) {
		temp = this.echiquier.getPiece(i, j);
		if (temp != null) {
		    if (temp.isBlanc()) {
			switch (temp.toString()) {
			case Piece.PION:
			    this.tabCase[i][j].setIcon(this.iconPiecesBlanches[0]);
			    break;
			case Piece.TOUR:
			    this.tabCase[i][j].setIcon(this.iconPiecesBlanches[1]);
			    break;
			case Piece.CAVALIER:
			    this.tabCase[i][j].setIcon(this.iconPiecesBlanches[2]);
			    break;
			case Piece.FOU:
			    this.tabCase[i][j].setIcon(this.iconPiecesBlanches[3]);
			    break;
			case Piece.REINE:
			    this.tabCase[i][j].setIcon(this.iconPiecesBlanches[4]);
			    break;
			case Piece.ROI:
			    this.tabCase[i][j].setIcon(this.iconPiecesBlanches[5]);
			    break;
			}
		    } else {
			switch (temp.toString()) {
			case Piece.PION:
			    this.tabCase[i][j].setIcon(this.iconPiecesNoires[0]);
			    break;
			case Piece.TOUR:
			    this.tabCase[i][j].setIcon(this.iconPiecesNoires[1]);
			    break;
			case Piece.CAVALIER:
			    this.tabCase[i][j].setIcon(this.iconPiecesNoires[2]);
			    break;
			case Piece.FOU:
			    this.tabCase[i][j].setIcon(this.iconPiecesNoires[3]);
			    break;
			case Piece.REINE:
			    this.tabCase[i][j].setIcon(this.iconPiecesNoires[4]);
			    break;
			case Piece.ROI:
			    this.tabCase[i][j].setIcon(this.iconPiecesNoires[5]);
			    break;
			}
		    }
		} else {
		    this.tabCase[i][j].setIcon(null);
		}
	    }
	}
    }

    private void initIcon() throws FileNotFoundException {
	int i = 0;
	ImageIcon imgIcon;
	File imageURL;
	String nomFichier;
	this.iconPiecesBlanches = new Image[6];
	this.iconPiecesNoires = new Image[6];
	for (i = 0; i < 6; i++) {
	    nomFichier = tabNomPiece[i] + "Blanc" + ".png";
	    imageURL = new File(JeuDEchec.cheminIcons + nomFichier);
	    if (!imageURL.exists()) {
		System.err.println("Resource not found: " + JeuDEchec.cheminIcons + nomFichier);
		throw new FileNotFoundException("Icone du " + tabNomPiece[i] + " Blanc : " + JeuDEchec.cheminIcons
			+ nomFichier + " non trouv�.");
	    } else {
		try {
		    imgIcon = new ImageIcon(imageURL.toURI().toURL(), tabNomPiece[i] + " " + "Blanc");
		    iconPiecesBlanches[i] = imgIcon.getImage();
		} catch (MalformedURLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	    nomFichier = tabNomPiece[i] + "Noir" + ".png";
	    imageURL = new File(JeuDEchec.cheminIcons + nomFichier);
	    if (!imageURL.exists()) {
		System.err.println("Resource not found: " + JeuDEchec.cheminIcons + nomFichier);
		throw new FileNotFoundException("Icone du " + tabNomPiece[i] + " Noir : " + JeuDEchec.cheminIcons
			+ nomFichier + " non trouv�.");
	    } else {
		try {
		    imgIcon = new ImageIcon(imageURL.toURI().toURL(), tabNomPiece[i] + " " + "Noir");
		    iconPiecesNoires[i] = imgIcon.getImage();
		} catch (MalformedURLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}
	imageURL = new File(JeuDEchec.cheminIcons + "sablier.png");
	if (!imageURL.exists()) {
	    System.err.println("Resource not found: " + JeuDEchec.cheminIcons + "sablier.png");
	    throw new FileNotFoundException("Icone du sablier : " + JeuDEchec.cheminIcons + "sablier.png non trouv�e.");
	} else {
	    try {
		imgIcon = new ImageIcon(imageURL.toURI().toURL(), "Sablier");
		this.sablier = imgIcon.getImage();
	    } catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	int gap = this.getX();
	int i, t = (this.getWidth() - 2 * gap) / taille;
	gap = gap + this.plateauLayout.gethGap();
	int margin = this.plateauLayout.getMargin();	
	int tailleCase = (this.getWidth()-margin*2)/taille;
	g.setColor(Color.white);
	g.setFont(new Font(null, Font.BOLD, margin / 2));
	if(this.plateauLayout.isReversed()) {
	    for(i = 0; i < taille; i++) {
		g.drawString(String.valueOf((char) ('A' + taille - i)), margin+i*tailleCase+tailleCase/2,margin);
		g.drawString(String.valueOf((char) ('A' + taille - i)), margin+i*tailleCase+tailleCase/2,this.getWidth()-margin);
		g.drawString(String.valueOf(i+1), 0+margin/4,margin+i*tailleCase+tailleCase/2);
		g.drawString(String.valueOf(i+1), this.getWidth()-3*margin/4,margin+i*tailleCase+tailleCase/2);
	    }
	}
	else {
	    for(i = 0; i < taille; i++) {
		g.drawString(String.valueOf((char) ('A' + i)), margin+i*tailleCase+tailleCase/2,0+margin/2);
		g.drawString(String.valueOf((char) ('A' + i)), margin+i*tailleCase+tailleCase/2,this.getWidth()-margin/2);
		g.drawString(String.valueOf(taille - i), 0+margin/4,margin+i*tailleCase+tailleCase/2);
		g.drawString(String.valueOf(taille - i), this.getWidth()-3*margin/4,margin+i*tailleCase+tailleCase);
	    }
	}
	if (this.enAttente) {
	    g.drawImage(this.sablier, 0, 0, gap, gap, this);
	    g.drawImage(this.sablier, this.getWidth() - gap, 0, gap, gap, this);
	    g.drawImage(this.sablier, 0, this.getHeight() - gap, gap, gap, this);
	    g.drawImage(this.sablier, this.getWidth() - gap, this.getHeight() - gap, gap, gap, this);
	}
    }

    private Case getCase(Coordonnee c) {
	return this.tabCase[c.getX()][c.getY()];
    }

    public Echiquier getEchiquier() {
	return echiquier;
    }

    public void afficherChoixPromotion() {
	Image icon[] = this.promotion.getpDepart().isBlanc() ? this.iconPiecesBlanches : this.iconPiecesNoires;
	if (this.promotion.getArrivee().getX() == 0) {
	    this.getCase(this.promotion.getArrivee()).setChoixIcon(Piece.REINE, icon[4]);
	    this.getCase(this.promotion.getArrivee().droite()).setChoixIcon(Piece.FOU, icon[3]);
	    this.getCase(this.promotion.getArrivee().droite().droite()).setChoixIcon(Piece.CAVALIER, icon[2]);
	    this.getCase(this.promotion.getArrivee().droite().droite().droite()).setChoixIcon(Piece.TOUR, icon[1]);
	} else {
	    if (this.promotion.getArrivee().getX() == taille - 1) {
		this.getCase(this.promotion.getArrivee()).setChoixIcon(Piece.REINE, icon[4]);
		this.getCase(this.promotion.getArrivee().gauche()).setChoixIcon(Piece.FOU, icon[3]);
		this.getCase(this.promotion.getArrivee().gauche().gauche()).setChoixIcon(Piece.CAVALIER, icon[2]);
		this.getCase(this.promotion.getArrivee().gauche().gauche().gauche()).setChoixIcon(Piece.TOUR, icon[1]);
	    } else {
		if (this.promotion.getArrivee().getX() == taille - 2) {
		    this.getCase(this.promotion.getArrivee()).setChoixIcon(Piece.REINE, icon[4]);
		    this.getCase(this.promotion.getArrivee().gauche()).setChoixIcon(Piece.FOU, icon[3]);
		    this.getCase(this.promotion.getArrivee().gauche().gauche()).setChoixIcon(Piece.CAVALIER, icon[2]);
		    this.getCase(this.promotion.getArrivee().droite()).setChoixIcon(Piece.TOUR, icon[1]);
		} else {
		    this.getCase(this.promotion.getArrivee()).setChoixIcon(Piece.REINE, icon[4]);
		    this.getCase(this.promotion.getArrivee().droite()).setChoixIcon(Piece.FOU, icon[3]);
		    this.getCase(this.promotion.getArrivee().droite().droite()).setChoixIcon(Piece.CAVALIER, icon[2]);
		    this.getCase(this.promotion.getArrivee().gauche()).setChoixIcon(Piece.TOUR, icon[1]);
		}
	    }
	}
    }

    public int joue(Coup c) {
	Coup dernier = this.echiquier.getDernierCoup();
	if (dernier != null) {
	    this.getCase(dernier.getArrivee()).setDernierCoup(false);
	    this.getCase(dernier.getDepart()).setDernierCoup(false);
	}
	this.getCase(c.getArrivee()).setDernierCoup(true);
	this.getCase(c.getDepart()).setDernierCoup(true);
	int etatPartie = this.echiquier.joue(c);
	return etatPartie;
    }
    
    public void updateDernierCoup() {
	Coup dernier;
	dernier = this.echiquier.getDernierCoup();
	if (dernier != null) {
	    this.getCase(dernier.getArrivee()).setDernierCoup(true);
	    this.getCase(dernier.getDepart()).setDernierCoup(true);
	}
    }

    public void annuler() {
	Coup dernier;
	dernier = this.echiquier.getDernierCoup();
	if (dernier != null) {
	    this.getCase(dernier.getArrivee()).setDernierCoup(false);
	    this.getCase(dernier.getDepart()).setDernierCoup(false);
	}
	this.echiquier.annuler();
	this.updateDernierCoup();
	for (Coup coup : this.coupsPossibles) {
	    this.getCase(coup.getArrivee()).setSubrillance(false);
	}
	this.coupsPossibles.clear();
	this.updatePlateau();
	this.repaint();
    }

    public int chercheCoup(Coordonnee destination) {// cherche le coup ayant
						    // pour destination
						    // "destination dans la
						    // liste coupsPossibles
						    // renvoie -1 si il
						    // n'existe pas sinon
						    // renvoie le code
						    // finDePartie
	Coup coup;
	int retour = -1, i = 1;
	if (!this.coupsPossibles.isEmpty()) {
	    coup = this.coupsPossibles.get(0);
	    while (!coup.getArrivee().equals(destination) && i < this.coupsPossibles.size()) {
		coup = this.coupsPossibles.get(i);
		i++;
	    }
	    if (coup.getArrivee().equals(destination)) {
		if (coup.getClass() == Promotion.class) {// Si le coup est une
							 // promotion il faut
							 // que le joueur
							 // choisissent en
							 // quoi il veut
							 // promouvoir son
							 // pion
		    this.promotion = (Promotion) coup;
		} else {
		    retour = this.joue(coup);
		}
	    }
	}
	return retour;
    }

    public boolean isInCoupsPossibles(Coordonnee c) {
	if (!this.coupsPossibles.isEmpty()) {
	    for (Coup coup : this.coupsPossibles) {
		if (coup.getArrivee().equals(c)) {
		    return true;
		}
	    }
	}
	return false;
    }

    public boolean isEnAttente() {
	return enAttente;
    }

    public void setEnAttente(boolean enAttente) {
	this.enAttente = enAttente;
    }

    public boolean isInPromotion() {
	return this.promotion != null;
    }

    public int movePiece(Case c, int x, int y) {
	Case cDest;
	Component compo;
	int finDePartie = -1;
	if (c.isDraging()) {
	    compo = this.getComponentAt(x + c.getX(), y + c.getY());
	    if (compo != null && compo.getClass() == Case.class) {
		cDest = (Case) compo;
		finDePartie = this.chercheCoup(cDest.getCoordonnee());
		cDest.setSubrillance(false);
	    }
	    for (Case tab[] : this.tabCase) {
		for (Case temp : tab) {
		    temp.freeDragIcon();
		}
	    }
	    c.setDraging(false);
	    this.coupsPossibles.clear();
	    ;
	} else {
	    if (!this.coupsPossibles.isEmpty()) {
		for (Coup coup : this.coupsPossibles) {
		    this.getCase(coup.getArrivee()).setSubrillance(false);
		}
		finDePartie = this.chercheCoup(c.getCoordonnee());
		this.coupsPossibles = this.echiquier.selectionne(c.getCoordonnee());
		if (!this.coupsPossibles.isEmpty()) {
		    for (Coup coup : this.coupsPossibles) {
			this.getCase(coup.getArrivee()).setSubrillance(true);
		    }
		}
	    } else {
		if (this.promotion != null) {
		    if (c.isChoix()) {
			this.promotion.choixPromo(c.getChoix());
			c.setIcon(c.getChoixIcon());
			finDePartie = this.joue(this.promotion);
			this.coupsPossibles.clear();
			;
		    }
		    for (Case tab[] : this.tabCase) {
			for (Case temp : tab) {
			    temp.freeChoix();
			}
		    }
		    this.promotion = null;
		}
		this.coupsPossibles = this.echiquier.selectionne(c.getCoordonnee());
		if (!this.coupsPossibles.isEmpty()) {
		    for (Coup coup : this.coupsPossibles) {
			this.getCase(coup.getArrivee()).setSubrillance(true);
		    }
		}
	    }
	}
	this.updatePlateau();
	this.repaint();
	return finDePartie;
    }

}
