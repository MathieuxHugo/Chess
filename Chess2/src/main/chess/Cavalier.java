package main.chess;

import java.util.LinkedList;

public class Cavalier extends Piece {

	public Cavalier(boolean couleur) {
		super(couleur);
		// TODO Auto-generated constructor stub
	}


	public Cavalier(boolean blanc, boolean aBouge) {
		super(blanc,aBouge);
	}


	@Override
	public LinkedList<Coordonnee> coupPossible(int x, int y, Case[][] plateau) {
		LinkedList<Coordonnee> retour=new LinkedList<Coordonnee>();
		int xTemp=x+2,yTemp=y+1;
		if(xTemp<Echiquier.taille && yTemp<Echiquier.taille) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x+1;
		yTemp=y+2;
		if(xTemp<Echiquier.taille && yTemp<Echiquier.taille) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x-1;
		yTemp=y+2;
		if(xTemp>=0 && yTemp<Echiquier.taille) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x-2;
		yTemp=y+1;
		if(xTemp>=0 && yTemp<Echiquier.taille) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x+1;
		yTemp=y-2;
		if(xTemp<Echiquier.taille && yTemp>=0) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x+2;
		yTemp=y-1;
		if(xTemp<Echiquier.taille && yTemp>=0) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x-2;
		yTemp=y-1;
		if(xTemp>=0 && yTemp>=0) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x-1;
		yTemp=y-2;
		if(xTemp>=0 && yTemp>=0) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		return retour;
	}
	@Override
	public Piece clone() {
		return new Cavalier(this.isBlanc(),this.isaBouge());
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return CAVALIER;
	}


	@Override
	public int valeur() {
		return 3;
	}


	@Override
	public double valeurBerliner() {
		return 3.2;
	}
}
