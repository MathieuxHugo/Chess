package chess;

import java.util.LinkedList;

public class Fou extends Piece {

	public Fou(boolean couleur) {
		super(couleur);
		// TODO Auto-generated constructor stub
	}
	public Fou(boolean blanc,boolean aBouge) {
		super(blanc,aBouge);
	}
	@Override
	public LinkedList<Coordonnee> coupPossible(int x, int y, Case[][] plateau) {
		LinkedList<Coordonnee> retour=new LinkedList<Coordonnee>();
		int xTemp=x+1,yTemp=y+1;
		while(xTemp<Echiquier.taille && yTemp<Echiquier.taille && plateau[xTemp][yTemp].isVide()) {// Diagonale en bas à droite
			retour.add(new Coordonnee(xTemp,yTemp));
			xTemp++;
			yTemp++;
		}
		if(xTemp<Echiquier.taille && yTemp<Echiquier.taille) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x+1;
		yTemp=y-1;
		while(xTemp<Echiquier.taille && yTemp>=0 && plateau[xTemp][yTemp].isVide()) {// Diagonale en haut à droite
			retour.add(new Coordonnee(xTemp,yTemp));
			xTemp++;
			yTemp--;
		}
		if(xTemp<Echiquier.taille && yTemp>=0) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x-1;
		yTemp=y-1;
		while(xTemp>=0 && yTemp>=0 && plateau[xTemp][yTemp].isVide()) {
			retour.add(new Coordonnee(xTemp,yTemp));
			xTemp--;
			yTemp--;
		}
		if(xTemp>=0 && yTemp>=0) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		xTemp=x-1;
		yTemp=y+1;
		while(xTemp>=0 && yTemp<Echiquier.taille && plateau[xTemp][yTemp].isVide()) {
			retour.add(new Coordonnee(xTemp,yTemp));
			xTemp--;
			yTemp++;
		}
		if(xTemp>=0 && yTemp<Echiquier.taille) {
			retour.add(new Coordonnee(xTemp,yTemp));
		}
		return retour;
	}

	@Override
	public Piece clone() {
		return new Fou(this.isBlanc(),this.isaBouge());
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return FOU;
	}
	
}
