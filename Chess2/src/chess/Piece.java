package chess;

import java.util.LinkedList;

public abstract class Piece {
	private boolean blanc;
	private boolean aBouge;
	public static final String CAVALIER="Cavalier";
	public static final String PION="Pion";
	public static final String ROI="Roi";
	public static final String REINE="Reine";
	public static final String FOU="Fou";
	public static final String TOUR="Tour";
	public Piece(boolean couleur)  {
		this.blanc=couleur;
		this.aBouge=false;
	}
	public Piece(boolean couleur, boolean aBouge) {
		this.blanc=couleur;
		this.aBouge=aBouge;
	}
	public boolean isBlanc() {
		return this.blanc;
	}
	public void setBlanc(boolean couleur) {
		this.blanc = couleur;
	}
	public boolean isaBouge() {
		return aBouge;
	}
	public void setaBouge(boolean aBouge) {
		this.aBouge = aBouge;
	}
	public abstract LinkedList<Coordonnee> coupPossible(final int x,final int y,final Case[][] plateau);//ajoute toute les cases ou la piece peut se déplacer et inclut les cases ou il y a une piece de la même couleur que la piece qu'on souhaite déplacer
	
	public abstract Piece clone();
	@Override
	public abstract String toString();
	
	
}
