package ia;

import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

import chess.Coordonnee;
import chess.Coup;
import chess.Echiquier;
import chess.Piece;
import chess.Promotion;
import interfaceGraphique.Case;

public class Ordinateur {
	private int profondeurMax;
	private boolean couleur;
	private Echiquier echiquier;
	private int nombreCoupTest;
	private String possibilites;
	private double SCORE_MAX=1024;
	public Ordinateur(int niveau, boolean couleur, Echiquier echiquier, boolean domination) {
		this.profondeurMax = niveau;
		this.couleur=couleur;
		this.echiquier=echiquier;
		this.nombreCoupTest=0;
	}
	
	public Coup joue() {
		Echiquier copie=this.echiquier.clone();
		LinkedList<Coup> coupsPossibles=ajouterPromotion(copie.getCoupsPossibles());
		double miniMax,temp=0;
		Coup decision=null;
		this.nombreCoupTest=0;
		this.possibilites="";
		if(this.profondeurMax!=0) {
			miniMax=Integer.MIN_VALUE;
			for(Coup coup : coupsPossibles) {
				switch(copie.joue(coup)) {
					case Echiquier.PARTIE_CONTINUE: 
						temp=this.miniMax(1,copie);
						break;
					case Echiquier.BLANC_GAGNE:
						temp=this.couleur?Integer.MAX_VALUE:Integer.MIN_VALUE;
						break;
					case Echiquier.NOIR_GAGNE:
						temp=this.couleur?Integer.MIN_VALUE:Integer.MAX_VALUE;
						break;
					case Echiquier.PAT:
						temp=0;
						break;
				}
				this.possibilites=this.possibilites+coup+" : "+temp+"\n";
				copie.annuler();
				if(temp>miniMax) {
					miniMax=temp;
					decision=coup;
				}
			}
		}
		else {//L'IA joue un coup aléatoire si la profondeur est de 0 
			decision=coupsPossibles.get((int)(Math.random()*coupsPossibles.size()));
		}
		/*System.out.println(this.possibilites);
		System.out.println(this.echiquier.scoreDomination(true));*/
		return decision;
	}
	private static LinkedList<Coup> ajouterPromotion(LinkedList<Coup> listeCoups) {
		LinkedList<Coup> listePromo= new LinkedList<Coup>(); 
		listeCoups.forEach(c -> {
			if(c instanceof Promotion) {
				Promotion temp=(Promotion) c;
				temp.choixPromo(Promotion.Choix.Reine);
				listePromo.add(temp.clone());
				temp.choixPromo(Promotion.Choix.Fou);
				listePromo.add(temp.clone());
				temp.choixPromo(Promotion.Choix.Tour);
				listePromo.add(temp.clone());
				temp.choixPromo(Promotion.Choix.Cavalier);
			}
		});
		listePromo.forEach(c -> listeCoups.add(c));
		return listeCoups;
	}
	
	private Double evalueCoup(Coup c) {
		
		return 0.0;
	}
	private Double evaluePiece() {
		return 0.0;
	}
	private Double evaluePiecePrise() {
		return 0.0;
	}
	private Double evalueCoordonnee() {
		return 0.0;
	}
	private double miniMax(int profondeur,Echiquier copie) {// monTour est vrai si c'est à l'ia de jouer
		double temp=0, min=Double.MAX_VALUE, max=Double.MIN_VALUE;
		LinkedList<Coup> coupsPossibles=ajouterPromotion(copie.getCoupsPossibles());
		this.nombreCoupTest++;
		if(profondeur>=this.profondeurMax) {
			return copie.scoreDomination(this.couleur);
		}
		else {
			for(Coup coup : coupsPossibles) {
				switch(copie.joue(coup)) {
					case Echiquier.PARTIE_CONTINUE: 
						temp=this.miniMax(profondeur+1,copie);
						break;
					case Echiquier.BLANC_GAGNE:
						temp=this.couleur?SCORE_MAX-profondeur:-SCORE_MAX+profondeur;
						break;
					case Echiquier.NOIR_GAGNE:
						temp=this.couleur?-SCORE_MAX+profondeur:SCORE_MAX-profondeur;
						break;
					case Echiquier.PAT:
						temp=0;
						break;
				}
				copie.annulerLeger();//annuler sans recalculer les coups Possibles
				if(temp>max) {
					max=temp;
				}
				if(temp<min) {
					min=temp;
				}
			}
			
		}
		return copie.isTour(this.couleur)?max-(min/SCORE_MAX):min+(max/SCORE_MAX);
	}
	public int getNiveau() {
		return this.profondeurMax/2;
	}

	public boolean isCouleur() {
		return couleur;
	}

	
}