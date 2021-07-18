package ia;

import interfaceGraphique.Case;

public class Deplacement {
	private Case depart;
	private Case arrivee;
	private int score;
	private int potentiel;
	public Deplacement(Case depart, Case arrivee, int score, int potentiel) {
		super();
		this.depart = depart;
		this.arrivee = arrivee;
		this.score = score;
		this.potentiel = potentiel;
	}
	public Deplacement(Case depart, Case arrivee) {
		super();
		this.depart = depart;
		this.arrivee = arrivee;
	}
	
	public Deplacement(int score, int potentiel) {
		super();
		this.score = score;
		this.potentiel = potentiel;
	}
	public Case getDepart() {
		return depart;
	}
	public void setDepart(Case depart) {
		this.depart = depart;
	}
	public Case getArrivee() {
		return arrivee;
	}
	public void setArrivee(Case arrivee) {
		this.arrivee = arrivee;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getPotentiel() {
		return potentiel;
	}
	public void setPotentiel(int potentiel) {
		this.potentiel = potentiel;
	}
	
	
}
