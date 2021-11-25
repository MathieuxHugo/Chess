package chess;

import java.util.LinkedList;

public class Case {
	private Piece piece;
	private LinkedList<Coordonnee>attaque;
	private LinkedList<Coordonnee>menace;
	private Coordonnee coordonnee;
	public Case(Piece piece, Coordonnee coordonnee) {
		super();
		this.piece = piece;
		this.attaque = new LinkedList<Coordonnee>();
		this.menace = new LinkedList<Coordonnee>();
		this.coordonnee = coordonnee;
	}
	
	
	private Case(Piece piece, LinkedList<Coordonnee> attaque, LinkedList<Coordonnee> menace, Coordonnee coordonnee) {
		super();
		if(piece!=null) {
			this.piece = piece.clone();
		}
		else {
			this.piece=null;
		}
		this.attaque = (LinkedList<Coordonnee>)attaque.clone();
		this.menace = (LinkedList<Coordonnee>)menace.clone();
		this.coordonnee = coordonnee;
	}


	public Piece getPiece() {
		return piece;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	public Coordonnee getCoordonnee() {
		return coordonnee;
	}
	public boolean isVide() {
		return this.piece==null;
	}
	public void updateMenace(Case [][] plateau){
		Case cible;
		for(Coordonnee c : this.attaque) {
			plateau[c.getX()][c.getY()].removeMenace((this.getCoordonnee()));
		}
		this.attaque.clear();
		if(this.piece!=null) {
			for(Coordonnee c : this.piece.coupPossible(this.coordonnee.getX(),this.coordonnee.getY(), plateau)) {
				cible=plateau[c.getX()][c.getY()];
				this.addAttaque(c);
				cible.addMenace(this.getCoordonnee());
			}
		}
	}
	public void removeMenace(Coordonnee c){
		this.menace.remove(c);
	}
	
	public LinkedList<Coordonnee> getMenace() {
		return menace;
	}
	private void addAttaque(Coordonnee c) {
		this.attaque.add(c);
	}
	public void addMenace(Coordonnee c) {
		this.menace.add(c);
	}
	public boolean memeCouleur(boolean couleur) {
		return this.piece!=null && this.piece.isBlanc()==couleur;
	}
	public boolean memeCouleur(Case c) {
		return this.piece!=null && c.memeCouleur(this.piece.isBlanc());
	}
	public LinkedList<Coordonnee> getAttaque() {
		return attaque;
	}
	public boolean isRoi() {
		return this.piece!=null && this.piece.getClass()==Roi.class;
	}
	public int getX() {
		return this.coordonnee.getX();
	}
	public int getY() {
		return this.coordonnee.getY();
	}
	public boolean estMenaceePar(boolean couleur, Case [][] plateau) {
		for(Coordonnee c : this.menace) {
			if(plateau[c.getX()][c.getY()].memeCouleur(couleur) && (plateau[c.getX()][c.getY()].getPiece().getClass()!=Pion.class || this.getX()!=c.getX())) {
				return true;
			}
		}
		return false;
	}
	public Case copie() {
		return new Case(this.piece,this.attaque,this.menace,this.coordonnee);
	}
}
