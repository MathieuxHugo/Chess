package main.chess;

public class Coup implements Cloneable {

    private Coordonnee depart;

    private Coordonnee arrivee;

    private Piece pDepart;

    private Piece prise;

    private boolean echec;

    protected Coup(Case c, Case cDest) {
	this.depart = c.getCoordonnee();
	this.arrivee = cDest.getCoordonnee();
	this.pDepart = c.getPiece().clone();
	this.prise = cDest.getPiece();
	this.echec = false;
	if (this.prise != null) {
	    this.prise = this.prise.clone();
	}
    }

    protected Coup(Coordonnee depart, Coordonnee arrivee, Piece p, Piece prise) {
	this.depart = depart;
	this.arrivee = arrivee;
	this.pDepart = p.clone();
	if (prise != null) {
	    this.prise = prise.clone();
	} else {
	    this.prise = null;
	}
    }

    public boolean isPrise() {
	return prise != null;
    }

    public Coordonnee getDepart() {
	return depart;
    }

    public Coordonnee getArrivee() {
	return arrivee;
    }

    public Piece getpDepart() {
	return pDepart.clone();
    }

    public Piece getPrise() {
	if (prise != null) {
	    return prise.clone();
	}
	return prise;
    }

    public void setPrise(Piece prise) {
	this.prise = prise;
    }

    public void setEchec(boolean echec) {
	this.echec = echec;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
	// TODO Auto-generated method stub
	return super.clone();
    }

    @Override
    public String toString() {
	return this.pDepart.toString() + this.depart + (this.isPrise() ? "x" : "") + this.arrivee
		+ (this.echec ? "+" : "");
    }

    public String updateFEN(String fen) {
	String []fentab = fen.split(" ");
	
	String []board = fentab[0].split("/");
	
	//this
		
	//TODO Finish 
	return fen;
    }
    
    private int getPositionBonus() {

	// Central squares (e4, d4, e5, d5)
	if ((arrivee.getX() == 3 || arrivee.getX() == 4) && (arrivee.getY() == 3 || arrivee.getY() == 4)) {
	    return 3;
	}
	// Near-center squares
	if ((arrivee.getX() >= 2 && arrivee.getX() <= 5) && (arrivee.getY() >= 2 && arrivee.getY() <= 5)) {
	    return 2;
	}
	// Edge squares
	return 1;
    }

    public int importance() {
	int importance = 0;

	// Capture bonus
	if (prise != null) {
	    importance += prise.valeur();
	}

	// Check bonus
	if (echec) {
	    importance += 5;
	}

	// Position bonus
	importance += getPositionBonus();

	return importance;
    }

}
