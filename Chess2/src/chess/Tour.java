package chess;

import java.util.LinkedList;

public class Tour extends Piece {
	public Tour(boolean couleur){
		super(couleur);
	}

	public Tour(boolean blanc, boolean aBouge) {
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
		return retour;
	}
	@Override
	public Piece clone() {
		return new Tour(this.isBlanc(),this.isaBouge());
	}

	@Override
	public String toString() {
		return TOUR;
	}

	@Override
	public int valeur() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public double valeurBerliner() {
		// TODO Auto-generated method stub
		return 5.1;
	}

}
