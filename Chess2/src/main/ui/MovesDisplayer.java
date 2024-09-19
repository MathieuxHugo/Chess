package main.ui;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;


public class MovesDisplayer extends JPanel {

    private String moves;
    
    public MovesDisplayer() {
	this.setBackground(Color.LIGHT_GRAY);
	this.moves = "";
	this.setLayout(new MoveDisplayerLayout());
    }
    
    public void update(String moves) {
	if(this.moves!=moves) {
	    Font font = new Font(null, Font.BOLD, this.getWidth() / 25);
	    this.moves=moves;
	    this.removeAll();
	    for (String move : moves.split(",")) {
		JLabel label = new JLabel();
		label.setFont(font);
		label.setText(move);
		this.add(label);
	    }
	    this.validate();
	    this.repaint();
	}
    }
}
