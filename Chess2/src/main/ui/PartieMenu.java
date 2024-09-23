package main.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PartieMenu extends JPanel {

    public PartieMenu(ChessGameActionListener listener) {
	this.setBackground(Color.DARK_GRAY);
	this.setLayout(new GridLayout(3,1));
	this.add(listener.createButton(ChessGameActionListener.ANNULER));
	this.add(listener.createButton(ChessGameActionListener.MENU));
	this.add(listener.createButton(ChessGameActionListener.SAUVEGARDER));
    }
    
}
