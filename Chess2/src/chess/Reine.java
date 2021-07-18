package chess;

import java.util.LinkedList;

public class Reine extends Piece {

	public Reine(boolean couleur){
		super(couleur);
		// TODO Auto-generated constructor stub
	}

	public Reine(boolean blanc, boolean aBouge) {
		super(blanc,aBouge);
	}

	@Override
	public LinkedList<Coordonnee> coupPossible(int x, int y, Case[][] plateau) {
		int xTemp=x+1, yTemp;
		LinkedList<Coordonnee> retour=new LinkedList<Coordonnee>();
		while(xTemp<Echiquier.taille && plateau[xTemp][y].isVide()) {//test vers la Droite
			retour.add(new Coordonnee(xTemp,y));
			xTemp++;
		}
		if(xTemp<Echiquier.taille) {
			retour.add(new Coordonnee(xTemp,y));
		}
		xTemp=x-1;
		while(xTemp>=0 && plateau[xTemp][y].isVide()) {//test vers la Gauche
			retour.add(new Coordonnee(xTemp,y));
			xTemp--;
		}
		if(xTemp>=0) {
			retour.add(new Coordonnee(xTemp,y));
		}
		yTemp=y-1;
		while(yTemp>=0 && plateau[x][yTemp].isVide()) {//test vers le Haut
			retour.add(new Coordonnee(x,yTemp));
			yTemp--;
		}
		if(yTemp>=0) {
			retour.add(new Coordonnee(x,yTemp));
		}
		yTemp=y+1;
		while(yTemp<Echiquier.taille && plateau[x][yTemp].isVide()) {//test vers le Bas
			retour.add(new Coordonnee(x,yTemp));
			yTemp++;
		}
		if(yTemp<Echiquier.taille) {
			retour.add(new Coordonnee(x,yTemp));
		}
		xTemp=x+1;
		yTemp=y+1;
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
		return new Reine(this.isBlanc(),this.isaBouge());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return REINE;
	}

}
