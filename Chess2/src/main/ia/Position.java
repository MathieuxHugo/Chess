package main.ia;

import java.util.ArrayList;
import java.util.List;

public class Position{
    private String fen;
    
    private int score;
    
    private int profondeur;

    private String positionId;
    
    private List<Move> moves;
    
    private int minMaxScore;
    
    public Position(String fen, int profondeur, String positionId) {
	super();
	this.positionId = positionId;
	this.fen = fen;
	this.profondeur = profondeur;
	this.score = 0;
	this.moves = new ArrayList<Move>();
	this.minMaxScore = 0;
    }
    
    public int getProfondeur() {
        return profondeur;
    }

    public String getFen() {
        return fen;
    }
    
    public int getScore() {
	return score;
    }
    
    
    public String getPositionId() {
        return positionId;
    }

    public void setScore(int score) {
	this.score = score;
    }
    
    public int minMax() {
	if(this.moves.isEmpty()) {
	    this.minMaxScore = this.score;
	    return this.score;
	}
	else {
	    int childScore;
	    if(this.profondeur%2==0) {
		minMaxScore = Integer.MAX_VALUE;
		for(Move move : this.moves) {
		    childScore=move.getPosition().minMax();
		    if(childScore<minMaxScore) {
			minMaxScore = childScore;
		    }
		}
	    }
	    else {
		minMaxScore = Integer.MIN_VALUE;
		for(Move move : this.moves) {
		    childScore=move.getPosition().minMax();
		    if(childScore>minMaxScore) {
			minMaxScore = childScore;
		    }
		}
	    }
	    return minMaxScore;
	}
    }
    
    
    
    public void setMinMaxScore(int minMaxScore) {
        this.minMaxScore = minMaxScore;
    }

    public int getMinMaxScore() {
        return minMaxScore;
    }

    public List<Move> getMoves(){
	return this.moves;
    }
    
    public void addMove(Move m) {
	this.moves.add(m);
    }
    
}
