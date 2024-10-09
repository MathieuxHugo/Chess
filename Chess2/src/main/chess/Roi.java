package main.chess;

import java.util.LinkedList;

public class Roi extends Piece {
	public Roi(boolean couleur){
		super(couleur);
		// TODO Auto-generated constructor stub
	}

	public Roi(boolean blanc, boolean aBouge) {
		super(blanc,aBouge);
	}
	@Override
	public LinkedList<Coordonnee> coupPossible(int x, int y, Case[][] plateau) {
		LinkedList<Coordonnee> retour=new LinkedList<Coordonnee>();
		int xTemp=x+1,yTemp=y+1;
		if(xTemp<Echiquier.taille && yTemp<Echiquier.taille) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x;
		yTemp=y+1;
		if(yTemp<Echiquier.taille) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x-1;
		yTemp=y+1;
		if(yTemp<Echiquier.taille && xTemp>=0) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x-1;
		yTemp=y;
		if(xTemp>=0) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x-1;
		yTemp=y-1;
		if(xTemp>=0 && yTemp>=0) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x;
		yTemp=y-1;
		if(yTemp>=0) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x+1;
		yTemp=y-1;
		if(yTemp>=0 && xTemp<Echiquier.taille) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x+1;
		yTemp=y;
		if(xTemp<Echiquier.taille) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		if(!this.isaBouge() && x==4) {
			retour.add(new Coordonnee(x-2,y));
			retour.add(new Coordonnee(x+2,y));
		}
		return retour;
	}
	@Override
	public Piece clone() {
		return new Roi(this.isBlanc(),this.isaBouge());
	}

	@Override
	public String toString() {
		return ROI;
	}

	@Override
	public int valeur() {
		return 0;
	}

	@Override
	public double valeurBerliner() {
		return 0;
	}

	@Override
	public char fenCode() {
	    return this.isBlanc() ? 'K' : 'k';
	}
}
