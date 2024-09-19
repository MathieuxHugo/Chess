package main.chess;

import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

public class Echiquier {

    private Case[][] plateau;

    public static int taille = 8;

    private Coordonnee roiNoir;

    private Coordonnee roiBlanc;

    private boolean tour;

    private int cptTour;

    private LinkedList<Coup> coupsPossibles;

    private LinkedList<Coup> listCoupsJoues;

    public static final int BLANC_GAGNE = 1;

    public static final int NOIR_GAGNE = 2;

    public static final int PAT = 3;

    public static final int PARTIE_CONTINUE = 0;

    public Echiquier() {
	super();
	this.plateau = new Case[taille][taille];
	this.tour = true;
	this.cptTour = 0;
	this.coupsPossibles = new LinkedList<Coup>();
	this.initPlateau();
	this.updateCoupsPossibles();
	this.listCoupsJoues = new LinkedList<Coup>();
    }

    private static Case[][] copier(Case[][] tab) {
	int i, j;
	Case retour[][] = new Case[tab.length][tab.length];
	for (i = 0; i < tab.length; i++) {
	    for (j = 0; j < tab.length; j++) {
		retour[i][j] = tab[i][j].copie();
	    }
	}
	return retour;
    }

    private Echiquier(Case[][] plateau, Coordonnee roiNoir, Coordonnee roiBlanc, boolean tour, int cptTour,
	    LinkedList<Coup> coupsPossibles, LinkedList<Coup> listCoupsJoues) {
	super();
	this.plateau = copier(plateau);
	this.roiNoir = roiNoir;
	this.roiBlanc = roiBlanc;
	this.tour = tour;
	this.cptTour = cptTour;
	this.coupsPossibles = (LinkedList<Coup>) coupsPossibles.clone();
	this.listCoupsJoues = (LinkedList<Coup>) listCoupsJoues.clone();
    }

    private void initPlateau() {
	int i;
	this.roiBlanc = new Coordonnee(4, 7);
	this.roiNoir = new Coordonnee(4, 0);
	for (i = 0; i < taille * taille; i++) {
	    this.plateau[i % taille][i / taille] = new Case(null, new Coordonnee(i % taille, i / taille));
	}
	this.setCase(new Coordonnee(0, 0), new Tour(false));
	this.setCase(new Coordonnee(1, 0), new Cavalier(false));
	this.setCase(new Coordonnee(2, 0), new Fou(false));
	this.setCase(new Coordonnee(3, 0), new Reine(false));
	this.setCase(new Coordonnee(4, 0), new Roi(false));
	this.setCase(new Coordonnee(5, 0), new Fou(false));
	this.setCase(new Coordonnee(6, 0), new Cavalier(false));
	this.setCase(new Coordonnee(7, 0), new Tour(false));
	this.setCase(new Coordonnee(0, 1), new Pion(false));
	this.setCase(new Coordonnee(1, 1), new Pion(false));
	this.setCase(new Coordonnee(2, 1), new Pion(false));
	this.setCase(new Coordonnee(3, 1), new Pion(false));
	this.setCase(new Coordonnee(4, 1), new Pion(false));
	this.setCase(new Coordonnee(5, 1), new Pion(false));
	this.setCase(new Coordonnee(6, 1), new Pion(false));
	this.setCase(new Coordonnee(7, 1), new Pion(false));
	this.setCase(new Coordonnee(0, 7), new Tour(true));
	this.setCase(new Coordonnee(1, 7), new Cavalier(true));
	this.setCase(new Coordonnee(2, 7), new Fou(true));
	this.setCase(new Coordonnee(3, 7), new Reine(true));
	this.setCase(new Coordonnee(4, 7), new Roi(true));
	this.setCase(new Coordonnee(5, 7), new Fou(true));
	this.setCase(new Coordonnee(6, 7), new Cavalier(true));
	this.setCase(new Coordonnee(7, 7), new Tour(true));
	this.setCase(new Coordonnee(0, 6), new Pion(true));
	this.setCase(new Coordonnee(1, 6), new Pion(true));
	this.setCase(new Coordonnee(2, 6), new Pion(true));
	this.setCase(new Coordonnee(3, 6), new Pion(true));
	this.setCase(new Coordonnee(4, 6), new Pion(true));
	this.setCase(new Coordonnee(5, 6), new Pion(true));
	this.setCase(new Coordonnee(6, 6), new Pion(true));
	this.setCase(new Coordonnee(7, 6), new Pion(true));
    }

    private void initTest() {
	int i;
	for (i = 0; i < taille * taille; i++) {
	    this.plateau[i % taille][i / taille] = new Case(null, new Coordonnee(i % taille, i / taille));
	}

	this.roiBlanc = new Coordonnee(4, 7);
	this.roiNoir = new Coordonnee(4, 0);
	this.setCase(new Coordonnee(4, 0), new Roi(false));
	this.setCase(new Coordonnee(4, 7), new Roi(true));
	this.setCase(new Coordonnee(6, 6), new Tour(true));
	this.setCase(new Coordonnee(3, 6), new Fou(false));
	this.setCase(new Coordonnee(2, 1), new Tour(false));
	this.setCase(new Coordonnee(5, 2), new Tour(false));
    }

    protected void chgTour() {
	this.tour = !tour;
    }

    protected void annulerAux(Coup c) {
	if (c.getClass() == PriseEnPassant.class) {
	    PriseEnPassant p = (PriseEnPassant) c;
	    this.setCase(p.getArrivee(), null);
	    this.setCase(p.getcPrise(), p.getPrise());
	    this.setCase(p.getDepart(), p.getpDepart());
	} else {
	    if (c.getClass() == Roque.class) {
		Roque r = (Roque) c;
		this.setCase(r.getDepart(), r.getpDepart());
		this.setCase(r.getArrivee(), null);
		this.setCase(r.getArriveeTour(), null);
		this.setCase(r.getDepartTour(), r.getTour());
	    } else {
		this.setCase(c.getDepart(), c.getpDepart());
		this.setCase(c.getArrivee(), c.getPrise());
	    }
	}
	if (this.getCase(c.getDepart()).isRoi()) {
	    if (this.getCase(c.getDepart()).memeCouleur(true)) {// true==blanc
		this.roiBlanc = c.getDepart();
	    } else {
		this.roiNoir = c.getDepart();
	    }
	}
    }

    private Coup deplacementRoi(Coordonnee c, Coordonnee cDest) {
	Coup retour = null;
	Roi roi = (Roi) this.getCase(c).getPiece();
	Coordonnee traverseeParRoi, caseTour;
	if (c.differenceX(cDest) != 2) {
	    retour = new Coup(c, cDest, roi, this.getPiece(cDest));
	    roi.setaBouge(true);
	    this.setCase(cDest, roi);
	    this.setCase(c, null);
	    if (roi.isBlanc()) {
		this.roiBlanc = cDest;
	    } else {
		this.roiNoir = cDest;
	    }
	} else {
	    traverseeParRoi = c.entreX(cDest);
	    if (c.getX() < cDest.getX()) {
		caseTour = new Coordonnee(taille - 1, c.getY());
	    } else {
		if (!this.plateau[1][c.getY()].isVide()) {
		    return null;
		}
		caseTour = new Coordonnee(0, c.getY());
	    }
	    if (!this.getCase(c).estMenaceePar(!roi.isBlanc(), plateau) && this.getCase(traverseeParRoi).isVide()
		    && !this.getCase(traverseeParRoi).estMenaceePar(!roi.isBlanc(), this.plateau)
		    && this.getCase(cDest).isVide() && !this.getCase(cDest).estMenaceePar(!roi.isBlanc(), this.plateau)
		    && !this.getCase(caseTour).isVide() && this.getPiece(caseTour).getClass() == Tour.class
		    && !this.getPiece(caseTour).isaBouge()) {
		retour = new Roque(this.getCase(c), this.getCase(cDest), this.getCase(caseTour),
			this.getCase(traverseeParRoi));
		roi.setaBouge(true);
		this.getPiece(caseTour).setaBouge(true);
		this.setCase(cDest, roi);
		this.setCase(traverseeParRoi, this.getPiece(caseTour));
		this.setCase(c, null);
		this.setCase(caseTour, null);
		if (roi.isBlanc()) {
		    this.roiBlanc = cDest;
		} else {
		    this.roiNoir = cDest;
		}
	    }
	}
	return retour;
    }

    private Coup deplacementPion(Coordonnee c, Coordonnee cDest) {
	Coup retour = null;
	Pion pion = (Pion) this.getPiece(c);
	if (cDest.getY() == taille - 1 || cDest.getY() == 0) {
	    if (cDest.getX() == c.getX()) {
		if (this.isVide(cDest)) {
		    retour = new Promotion(c, cDest, pion, null);
		    this.setCase(cDest, pion);
		    this.setCase(c, null);
		}
	    } else {
		if (!this.isVide(cDest)) {
		    retour = new Promotion(c, cDest, pion, this.getPiece(cDest));
		    this.setCase(cDest, pion);
		    this.setCase(c, null);
		}
	    }
	} else {
	    if (cDest.getX() == c.getX()) {
		if (this.isVide(cDest)) {
		    retour = new Coup(c, cDest, pion, null);
		    this.setCase(cDest, pion);
		    this.setCase(c, null);
		}
	    } else {
		Piece temp = this.plateau[cDest.getX()][c.getY()].getPiece();
		if (!this.isVide(cDest) || (temp != null && temp.getClass() == Pion.class)) {
		    if (this.isVide(cDest)) {
			Pion pion2 = (Pion) temp;
			if (!this.getCase(c).memeCouleur(pion2.isBlanc()) && pion2.getEnPassant() == this.cptTour - 1) {
			    retour = new PriseEnPassant(this.getCase(c), this.getCase(cDest),
				    this.plateau[cDest.getX()][c.getY()]);
			    this.setCase(cDest, pion);
			    this.setCase(c, null);
			    ;
			    this.setCase(cDest.getX(), c.getY(), null);
			}
		    } else {
			retour = new Coup(c, cDest, pion, this.getPiece(cDest));
			this.setCase(cDest, pion);
			this.setCase(c, null);
		    }
		}
	    }
	}
	return retour;
    }

    private boolean isVide(Coordonnee c) {
	return plateau[c.getX()][c.getY()].isVide();
    }

    protected Coup deplacerAux(Coordonnee c, Coordonnee cDest) {
	Coup retour = null;
	Piece temp = this.getPiece(c);
	if (!this.getCase(cDest).memeCouleur(temp.isBlanc())) {
	    if (temp.getClass() == Pion.class || temp.getClass() == Roi.class) {
		if (temp.getClass() == Pion.class) {
		    retour = this.deplacementPion(c, cDest);
		} else {
		    retour = this.deplacementRoi(c, cDest);
		}
	    } else {
		retour = new Coup(c, cDest, temp, this.getPiece(cDest));
		this.getPiece(c).setaBouge(true);
		this.setCase(cDest, temp);
		this.setCase(c, null);
	    }
	}
	return retour;
    }

    protected Coup protege(boolean couleur, Coordonnee c, Coordonnee cDest) {
	Coup retour = this.deplacerAux(c, cDest);
	boolean menaceNoir = false, menaceBlanc = false;
	if (retour != null) {
	    menaceBlanc = this.getCase(roiBlanc).estMenaceePar(!couleur, plateau);
	    menaceNoir = this.getCase(roiNoir).estMenaceePar(!couleur, plateau);
	    this.annulerAux(retour);
	    if (couleur) {
		if (menaceBlanc) {
		    return null;
		} else {
		    retour.setEchec(menaceNoir);
		}
	    } else {
		if (menaceNoir) {
		    return null;
		} else {
		    retour.setEchec(menaceBlanc);
		}
	    }
	}
	return retour;
    }

    private void updateCoupsPossibles() {
	int i, j;
	Case c;
	this.coupsPossibles = new LinkedList<Coup>();
	Coup coup;
	Coordonnee destination;
	for (i = 0; i < taille; i++) {
	    for (j = 0; j < taille; j++) {
		c = this.getCase(i, j);
		if (!c.isVide() && c.getPiece().isBlanc() == tour) {
		    for (Object o : c.getAttaque().toArray()) {
			destination = (Coordonnee) o;
			coup = this.protege(tour, c.getCoordonnee(), destination);
			if (coup != null) {
			    this.coupsPossibles.add(coup);
			}
		    }
		}
	    }
	}
    }

    protected int getCptTour() {
	return cptTour;
    }

    public boolean isTour() {
	return tour;
    }

    public boolean isTour(boolean t) {
	return tour == t;
    }

    protected Case getCase(Coordonnee c) {
	return plateau[c.getX()][c.getY()];
    }

    public Case getCase(int x, int y) {
	return plateau[x][y];
    }

    protected void setCase(Coordonnee c, Piece p) {
	Case temp, cible = plateau[c.getX()][c.getY()];
	cible.setPiece(p);
	for (Object o : cible.getMenace().toArray()) {
	    temp = this.getCase((Coordonnee) o);
	    temp.updateMenace(plateau);
	}
	cible.updateMenace(plateau);
    }

    protected void setCase(int x, int y, Piece p) {
	Case temp, cible = plateau[x][y];
	cible.setPiece(p);
	for (Object o : cible.getMenace().toArray()) {
	    temp = this.getCase((Coordonnee) o);
	    temp.updateMenace(plateau);
	}
	cible.updateMenace(plateau);
    }

    protected Case[][] getPlateau() {
	return plateau;
    }

    public Piece getPiece(Coordonnee c) {
	return this.plateau[c.getX()][c.getY()].getPiece();
    }

    public Piece getPiece(int x, int y) {
	return this.plateau[x][y].getPiece();
    }

    public LinkedList<Coup> selectionne(Coordonnee c) {
	LinkedList<Coup> selection = new LinkedList<Coup>();
	for (Coup coup : this.coupsPossibles) {
	    if (coup.getDepart().equals(c)) {
		selection.add(coup);
	    }
	}
	return selection;
    }

    public int joue(Coup c) {
	PriseEnPassant p;
	Promotion promo;
	Roque r;
	Piece piece = this.getPiece(c.getDepart());
	Pion pion;
	piece.setaBouge(true);
	if (c.getClass() == Coup.class) {
	    this.setCase(c.getArrivee(), piece);
	    this.setCase(c.getDepart(), null);
	    if (piece.getClass() == Pion.class && Math.abs(c.getArrivee().getY() - c.getDepart().getY()) == 2) {
		pion = (Pion) piece;
		pion.setEnPassant(cptTour);
	    }
	} else {
	    if (c.getClass() == Roque.class) {
		r = (Roque) c;
		this.setCase(r.getArrivee(), piece);
		this.setCase(r.getDepart(), null);
		this.setCase(r.getArriveeTour(), this.getPiece(r.getDepartTour()));
		this.setCase(r.getDepartTour(), null);
	    } else {
		if (c.getClass() == Promotion.class) {
		    promo = (Promotion) c;
		    this.setCase(promo.getDepart(), null);
		    this.setCase(promo.getArrivee(), promo.getPromo());
		} else {
		    p = (PriseEnPassant) c;
		    this.setCase(p.getArrivee(), piece);
		    this.setCase(p.getDepart(), null);
		    this.setCase(p.getcPrise(), null);
		}
	    }
	}
	if (this.getPiece(c.getArrivee()).getClass() == Roi.class) {
	    if (tour) {
		this.roiBlanc = c.getArrivee();
	    } else {
		this.roiNoir = c.getArrivee();
	    }
	}
	this.listCoupsJoues.add(c);
	this.tour = !this.tour;
	this.cptTour++;
	this.updateCoupsPossibles();
	if (this.coupsPossibles.isEmpty()) {
	    if (tour) {
		if (this.getCase(this.roiBlanc).estMenaceePar(false, plateau)) {
		    return Echiquier.NOIR_GAGNE;
		} else {
		    return Echiquier.PAT;
		}
	    } else {
		if (this.getCase(this.roiNoir).estMenaceePar(true, plateau)) {
		    return Echiquier.BLANC_GAGNE;
		} else {
		    return Echiquier.PAT;
		}
	    }
	}
	return Echiquier.PARTIE_CONTINUE;
    }

    public void annuler() {
	if (!this.listCoupsJoues.isEmpty()) {
	    this.annulerAux(this.listCoupsJoues.removeLast());
	    tour = !tour;
	    this.cptTour--;
	    this.updateCoupsPossibles();
	}
    }

    public void annulerLeger() {
	if (!this.listCoupsJoues.isEmpty()) {
	    this.annulerAux(this.listCoupsJoues.removeLast());
	    tour = !tour;
	    this.cptTour--;
	}
    }

    public double scoreDomination(boolean couleur) {
	int i, j, sommeMenaceBlanche = 0, sommeMenaceNoire = 0;
	Case temp;
	for (i = 0; i < taille; i++) {
	    for (j = 0; j < taille; j++) {
		temp = this.getCase(i, j);
		if (temp.getPiece() != null) {
		    if (temp.getPiece().isBlanc()) {
			sommeMenaceBlanche = temp.getAttaque().size() + sommeMenaceBlanche;
		    } else {
			sommeMenaceNoire = temp.getAttaque().size() + sommeMenaceNoire;
		    }
		}
	    }
	}
	if (couleur) {
	    return sommeMenaceBlanche - sommeMenaceNoire;
	} else {
	    return sommeMenaceNoire - sommeMenaceBlanche;
	}
    }

    public int score(boolean couleur) {
	int i, j, sommePieceBlanche = 0, sommePieceNoire = 0;
	Piece temp;
	for (i = 0; i < taille; i++) {
	    for (j = 0; j < taille; j++) {
		temp = this.getCase(i, j).getPiece();
		if (temp != null) {
		    if (temp.isBlanc()) {
			sommePieceBlanche = temp.valeur() + sommePieceBlanche;
		    } else {
			sommePieceNoire = temp.valeur() + sommePieceNoire;
		    }
		}
	    }
	}
	if (couleur) {
	    return sommePieceBlanche - sommePieceNoire;
	} else {
	    return sommePieceNoire - sommePieceBlanche;
	}
    }

    public double scoreBerliner(boolean couleur) {
	int i, j;
	double sommePieceBlanche = 0, sommePieceNoire = 0;
	Piece temp;
	for (i = 0; i < taille; i++) {
	    for (j = 0; j < taille; j++) {
		temp = this.getCase(i, j).getPiece();
		if (temp != null) {
		    if (temp.isBlanc()) {
			sommePieceBlanche = temp.valeurBerliner() + sommePieceBlanche;
		    } else {
			sommePieceNoire = temp.valeurBerliner() + sommePieceNoire;
		    }
		}
	    }
	}
	if (couleur) {
	    return sommePieceBlanche - sommePieceNoire;
	} else {
	    return sommePieceNoire - sommePieceBlanche;
	}
    }

    public LinkedList<Coup> getCoupsPossibles() {
	return coupsPossibles;
    }

    public LinkedList<Coup> getListCoupsJoues() {
	return listCoupsJoues;
    }

    public Coup getDernierCoup() {
	try {
	    return this.listCoupsJoues.getLast();
	} catch (java.util.NoSuchElementException e) {
	    return null;
	}
    }

    @Override
    public Echiquier clone() {
	return new Echiquier(this.plateau, this.roiNoir, this.roiBlanc, this.tour, this.cptTour, this.coupsPossibles,
		this.listCoupsJoues);
    }

    @Override
    public String toString() {
	String coups = "";
	int compteur = 0;
	for(Coup c : this.listCoupsJoues) {
	    if(compteur%2 == 0) {
		coups += (1+compteur/2)+". ";
	    }
	    coups+=c.toString() + ",";
	    compteur++;
	}
	return coups;
    }

}
