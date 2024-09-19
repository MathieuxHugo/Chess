package main.chess;

import java.util.LinkedList;

public abstract class Piece {
	private boolean blanc;
	private boolean aBouge;
	public static final String CAVALIER="N";
	public static final String PION="";
	public static final String ROI="K";
	public static final String REINE="Q";
	public static final String FOU="B";
	public static final String TOUR="R";
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
	public abstract LinkedList<Coordonnee> coupPossible(final int x,final int y,final Case[][] plateau);//ajoute toute les cases ou la piece peut se d�placer et inclut les cases ou il y a une piece de la m�me couleur que la piece qu'on souhaite d�placer
	
	public abstract Piece clone();
	@Override
	public abstract String toString();
	public abstract int valeur();
	public abstract double valeurBerliner();
	
}
