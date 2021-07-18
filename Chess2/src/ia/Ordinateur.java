package ia;

import java.util.LinkedList;
import java.util.Set;

import chess.Coordonnee;
import chess.Coup;
import chess.Echiquier;
import chess.Piece;
import interfaceGraphique.Case;

public class Ordinateur {
	private int profondeurMax;
	private boolean couleur;
	private Echiquier echiquier;
	private int nombreCoupTest;
	public Ordinateur(int niveau, boolean couleur, Echiquier echiquier) {
		this.profondeurMax = niveau*2;
		this.couleur=couleur;
		this.echiquier=echiquier;
		this.nombreCoupTest=0;
	}
	
	public Coup joue() {
		Object[] pieceDeplacable=this.echiquier.pieceDeplacable().toArray();;
		LinkedList<Coup> coupsPossibles;
		int miniMax,temp;
		Coup decision=null;
		this.nombreCoupTest=0;
		if(this.profondeurMax==0) {//joue un coup aléatoire
			coupsPossibles=this.echiquier.selectionne((Coordonnee)pieceDeplacable[(int)(Math.random()*pieceDeplacable.length)]);
			decision=coupsPossibles.get((int)(Math.random()*coupsPossibles.size()));
		}
		else {
			miniMax=Integer.MIN_VALUE;
			for(Object coord : pieceDeplacable) {
				for(Object coup : this.echiquier.selectionne((Coordonnee)coord).toArray()) {
					this.echiquier.joue((Coup)coup);
					temp=this.miniMax(0);
					this.echiquier.annuler();
					if(temp>miniMax) {
						miniMax=temp;
						decision=(Coup)coup;
					}
				}
			}
		}
		System.out.println(this.nombreCoupTest+",profondeur="+this.profondeurMax);
		return decision;
	}
	
	private int miniMax(int profondeur) {// monTour est vrai si c'est à l'ia de jouer
		Object[] pieceDeplacable=this.echiquier.pieceDeplacable().toArray();
		int temp, retour;
		this.nombreCoupTest++;
		if(profondeur>=this.profondeurMax) {
			return this.echiquier.score(this.couleur);
		}
		else {
			if(pieceDeplacable.length>0) {
				if(this.echiquier.isTour()==this.couleur) {
					retour=Integer.MIN_VALUE;
					for(Object coord : pieceDeplacable) {
						for(Object coup : this.echiquier.selectionne((Coordonnee)coord).toArray()) {
							this.echiquier.joue((Coup)coup);
							temp=this.miniMax(profondeur+1);
							this.echiquier.annuler();
							if(temp>retour) {
								retour=temp;
							}
						}
					}
				}
				else {
					retour=Integer.MAX_VALUE;
					for(Object coord : pieceDeplacable) {
						for(Object coup : this.echiquier.selectionne((Coordonnee)coord).toArray()) {
							this.echiquier.joue((Coup)coup);
							temp=this.miniMax(profondeur+1);
							this.echiquier.annuler();
							if(temp<retour) {
								retour=temp;
							}
						}
					}
				}
			}
			else {
				if(this.isCouleur()==this.echiquier.isTour()) {
					return Integer.MIN_VALUE;
				}
				else {
					return Integer.MAX_VALUE;
				}
			}
		}
		return retour;
	}

	public int getNiveau() {
		return this.profondeurMax/2;
	}

	public boolean isCouleur() {
		return couleur;
	}

	
}