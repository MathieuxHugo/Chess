package main.ia;

import java.util.List;

public class Move implements Comparable<Move> {

    private int depth;

    private Position position;

    private int importance;

    private String name;
    
    public Move(String name, int depth, Position position, int importance) {
	super();
	this.name = name;
	this.depth = depth;
	this.position = position;
	this.importance = importance;
    }

    public int getDepth() {
	return depth;
    }

    public Position getPosition() {
	return position;
    }

    public int getImportance() {
	return importance;
    }

    @Override
    public int compareTo(Move p) {
	return p.getValue() - this.getValue();
    }

    public int getValue() {
	return this.importance - depth;
    }

    
    public String getName() {
        return name;
    }
    
    
}
