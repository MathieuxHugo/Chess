package main.ia;

import java.util.LinkedList;
import java.util.PriorityQueue;

import main.chess.Coup;
import main.chess.Echiquier;
import main.chess.Piece;
import main.chess.Promotion;

public class Ordinateur {

    private int niveau;

    private boolean couleur;

    private Echiquier echiquier;

    private String possibilites;

    private double SCORE_MAX = 1024;

    private Position initialPosition;

    private ChessPositionGraph graph;

    public Ordinateur(int niveau, boolean couleur, Echiquier echiquier, boolean domination) {
	this.niveau = niveau;
	this.couleur = couleur;
	this.echiquier = echiquier;
	System.out.println("NIVEAU : " + niveau);
	this.initialPosition = null;

	graph = new ChessPositionGraph();
    }

    public Coup joue() {
	Echiquier copie = this.echiquier.clone();
	LinkedList<Coup> coupsPossibles = ajouterPromotion(copie.getCoupsPossibles());
	int scores[] = new int[coupsPossibles.size()];
	Thread moves[] = new Thread[coupsPossibles.size()];
	int i = 0;
	initialPosition = new Position(copie.getFEN(), 1, "Position 0");
	for (Coup coup : coupsPossibles) {
	    switch (copie.joue(coup)) {
	    case Echiquier.PARTIE_CONTINUE:
		Position position = new Position(copie.getFEN(), initialPosition.getProfondeur() + 1,
			initialPosition.getPositionId() + "/" + i);
		Move move = new Move(coup.toString(), initialPosition.getProfondeur(), position, coup.importance());
		initialPosition.addMove(move);
		moves[i] = new Thread(new EvaluateMove(i, this.niveau * 20, scores, move, couleur));
		break;
	    case Echiquier.PAT:
		moves[i] = null;
		scores[i] = 0;
		break;
	    default:
		return coup;
	    }
	    i++;
	    copie.annuler();
	}

	for (Thread move : moves) {
	    if (move != null) {
		move.start();
	    }
	}

	int bestMoveIndex = 0;
	try {
	    for (i = 0; i < moves.length; i++) {
		if (moves[i] != null) {

		    moves[i].join();
		}
		if (scores[i] > scores[bestMoveIndex]) {
		    bestMoveIndex = i;
		}
	    }
	} catch (InterruptedException e) {
	    for (Thread move : moves) {
		if (move != null) {
		    move.interrupt();
		}
	    }
	    e.printStackTrace();
	}
	initialPosition.setMinMaxScore(scores[bestMoveIndex]);
	return coupsPossibles.get(bestMoveIndex);
    }

    public void displayGraph() {
	if(initialPosition!=null)
	    this.graph.displayPosition(initialPosition);
    }

    public Coup joue2() {
	Echiquier copie = this.echiquier.clone();
	LinkedList<Coup> coupsPossibles = ajouterPromotion(copie.getCoupsPossibles());
	double miniMax, temp = 0;
	Coup decision = null;
	this.possibilites = "";
	if (this.niveau != 0) {
	    miniMax = Integer.MIN_VALUE;
	    for (Coup coup : coupsPossibles) {
		switch (copie.joue(coup)) {
		case Echiquier.PARTIE_CONTINUE:
		    temp = this.miniMax(1, copie);
		    break;
		case Echiquier.BLANC_GAGNE:
		    temp = this.couleur ? Integer.MAX_VALUE : Integer.MIN_VALUE;
		    break;
		case Echiquier.NOIR_GAGNE:
		    temp = this.couleur ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		    break;
		case Echiquier.PAT:
		    temp = 0;
		    break;
		}
		this.possibilites = this.possibilites + coup + " : " + temp + "\n";
		copie.annuler();
		if (temp > miniMax) {
		    miniMax = temp;
		    decision = coup;
		}
	    }
	} else {// L'IA joue un coup al�atoire si la profondeur est de 0
	    decision = coupsPossibles.get((int) (Math.random() * coupsPossibles.size()));
	}
	return decision;
    }

    public static LinkedList<Coup> ajouterPromotion(LinkedList<Coup> listeCoups) {
	LinkedList<Coup> listePromo = new LinkedList<Coup>();
	listeCoups.forEach(c -> {
	    if (c instanceof Promotion) {
		Promotion temp = (Promotion) c;
		temp.choixPromo(Piece.REINE);
		listePromo.add(temp.clone());
		temp.choixPromo(Piece.FOU);
		listePromo.add(temp.clone());
		temp.choixPromo(Piece.TOUR);
		listePromo.add(temp.clone());
		temp.choixPromo(Piece.CAVALIER);
	    }
	});
	listePromo.forEach(c -> listeCoups.add(c));
	return listeCoups;
    }

    private double miniMax(int profondeur, Echiquier copie) {// monTour est vrai si c'est � l'ia de jouer
	double temp = 0, min = Double.MAX_VALUE, max = Double.MIN_VALUE;
	LinkedList<Coup> coupsPossibles = ajouterPromotion(copie.getCoupsPossibles());
	if (profondeur >= this.niveau) {
	    return copie.score(this.couleur);
	} else {
	    for (Coup coup : coupsPossibles) {
		switch (copie.joue(coup)) {
		case Echiquier.PARTIE_CONTINUE:
		    temp = this.miniMax(profondeur + 1, copie);
		    break;
		case Echiquier.BLANC_GAGNE:
		    temp = this.couleur ? SCORE_MAX - profondeur : -SCORE_MAX + profondeur;
		    break;
		case Echiquier.NOIR_GAGNE:
		    temp = this.couleur ? -SCORE_MAX + profondeur : SCORE_MAX - profondeur;
		    break;
		case Echiquier.PAT:
		    temp = 0;
		    break;
		}
		copie.annulerLeger();// annuler sans recalculer les coups Possibles
		if (temp > max) {
		    max = temp;
		}
		if (temp < min) {
		    min = temp;
		}
	    }

	}
	return copie.isTour(this.couleur) ? max - (min / SCORE_MAX) : min + (max / SCORE_MAX);
    }

    public int getNiveau() {
	return this.niveau;
    }

    public boolean isCouleur() {
	return couleur;
    }

}