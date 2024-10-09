package main.ia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import main.chess.Coup;
import main.chess.Echiquier;

public class EvaluateMove implements Runnable {

    private PriorityQueue<Move> movesToEvaluate = new PriorityQueue<>();

    private int index, numberOfPositionToEvaluate;

    private static final int winningScore = 100;

    private int[] scores;

    private boolean color;

    private Position initialPosition;

    public EvaluateMove(int i, int n, int[] scores, Move move, boolean color) {
	this.index = i;
	this.color = color;
	this.numberOfPositionToEvaluate = n;
	this.scores = scores;
	this.movesToEvaluate.add(move);
	this.initialPosition = move.getPosition();
    }

    @Override
    public void run() {
	evaluate(0);

	scores[index] = initialPosition.minMax();
    }

    private void evaluate(int i) {
	if (i < this.numberOfPositionToEvaluate && !this.movesToEvaluate.isEmpty()) {
	    Move m = this.movesToEvaluate.poll();
	    Position p = m.getPosition();
	    Echiquier e = new Echiquier(p.getFen());
	    LinkedList<Coup> coupsPossibles = Ordinateur.ajouterPromotion(e.getCoupsPossibles());
	    int id = 0;
	    Position child;
	    Move move;
	    for (Coup coup : coupsPossibles) {
		switch (e.joue(coup)) {
		case Echiquier.PARTIE_CONTINUE:
		    child = new Position(e.getFEN(), p.getProfondeur() + 1, p.getPositionId() + "/" + id++);
		    move = new Move(coup.toString(), p.getProfondeur(), child, coup.importance());
		    p.addMove(move);
		    child.setScore(e.score(color));
		    this.movesToEvaluate.add(move);
		    break;
		case Echiquier.PAT:
		    child = new Position(e.getFEN(), p.getProfondeur() + 1, p.getPositionId() + "/" + id++);
		    move = new Move(coup.toString(), p.getProfondeur(), child, coup.importance());
		    p.addMove(move);
		    child.setScore(0);
		    break;
		default:
		    child = new Position(e.getFEN(), p.getProfondeur() + 1, p.getPositionId() + "/" + id++);
		    move = new Move(coup.toString(), p.getProfondeur(), child, coup.importance());
		    p.addMove(move);
		    child.setScore(
			    e.isTour() == color ? -winningScore + p.getProfondeur() : winningScore - p.getProfondeur());
		}
		e.annulerLeger();
	    }
	    evaluate(i + 1);
	}
    }

}
