package main.chess;

import java.util.LinkedList;

public class Pion extends Piece {
	private int enPassant;
	public Pion(boolean couleur){
		super(couleur);
		this.enPassant=-100;
	}
	public Pion(boolean couleur,int enPassant,boolean aBouge) {
		super(couleur,aBouge);
		this.enPassant=enPassant;
	}
	
	public int getEnPassant() {
		return enPassant;
	}

	public void setEnPassant(int enPassant) {
		this.enPassant = enPassant;
	}
	

	@Override
	public LinkedList<Coordonnee> coupPossible(final int x,final int y,final Case[][] plateau) {
		LinkedList<Coordonnee> retour=new LinkedList<Coordonnee>();
		int xTemp,yTemp;
		if(this.isBlanc()) {
			xTemp=x;
			yTemp=y-1;
			if(yTemp>=0) {
				retour.add(new Coordonnee(xTemp,yTemp));
				if(y==6 && plateau[xTemp][yTemp].isVide()) {
					retour.add(new Coordonnee(xTemp,yTemp-1));
				}
				xTemp=x+1;
				if(xTemp<Echiquier.taille) {
					retour.add(new Coordonnee(xTemp,yTemp));
				}
				xTemp=x-1;
				if(xTemp>=0) {
					retour.add(new Coordonnee(xTemp,yTemp));
				}
			}
		}
		else {
			xTemp=x;
			yTemp=y+1;
			if(yTemp<Echiquier.taille) {
				retour.add(new Coordonnee(xTemp,yTemp));
				if(y==1 && plateau[xTemp][yTemp].isVide()) {
					retour.add(new Coordonnee(xTemp,yTemp+1));
				}
				xTemp=x+1;
				if(xTemp<Echiquier.taille) {
					retour.add(new Coordonnee(xTemp,yTemp));
				}
				xTemp=x-1;
				if(xTemp>=0) {
					retour.add(new Coordonnee(xTemp,yTemp));
				}
			}
		}
		return retour;
	}

	@Override
	public Piece clone() {
		return new Pion(this.isBlanc(),this.enPassant,this.isaBouge());
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return PION;
	}
	@Override
	public int valeur() {
		return 1;
	}
	@Override
	public double valeurBerliner() {
		return 1;
	}

	

}
