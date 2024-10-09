package main.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PartieMenu extends JPanel {

    private JButton displayGraph;
    
    public PartieMenu(ChessGameActionListener listener) {
	this.setBackground(Color.DARK_GRAY);
	this.setLayout(new GridLayout(4,1));
	this.add(listener.createButton(ChessGameActionListener.ANNULER));
	this.add(listener.createButton(ChessGameActionListener.MENU));
	this.add(listener.createButton(ChessGameActionListener.SAUVEGARDER));
	displayGraph=listener.createButton(ChessGameActionListener.DISPLAY_GRAPH);
	displayGraph.setVisible(false);
	this.add(displayGraph);
    }
    
    public void setComputer(boolean show) {
	displayGraph.setVisible(show);
    }
    
}
