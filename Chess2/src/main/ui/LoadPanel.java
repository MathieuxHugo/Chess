package main.ui;

import javax.inject.Inject;
import javax.swing.*;

import main.service.PartieService;
import main.model.Partie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoadPanel extends JPanel {

    private PartieService partieService;

    private ChessGameActionListener chessGameActionListener;

    private JPanel listPanel;

    private JButton menuButton;
    
    private Font font;
    
    private GridLayout gridLayout;

    // Constructor where Guice will inject the PartieService and
    // ChessGameActionListener
    @Inject
    public LoadPanel(PartieService partieService, ChessGameActionListener chessGameActionListener) {
	this.partieService = partieService;
	this.chessGameActionListener = chessGameActionListener;
	this.font = new Font("Serif", Font.BOLD, 24);
	this.gridLayout = new GridLayout(1, 6);
	setLayout(new BorderLayout());

	add(this.createJLabel("List des parties"), BorderLayout.NORTH);

	// List panel where we'll add each Partie
	listPanel = new JPanel();
	listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // Display vertically

	JScrollPane scrollPane = new JScrollPane(listPanel);
	add(scrollPane, BorderLayout.CENTER);

	// Add the menu button at the bottom, and apply the size constraints
	menuButton = chessGameActionListener.createButton(ChessGameActionListener.MENU);
	add(menuButton, BorderLayout.SOUTH);

    }
    
    private JLabel createJLabel(String text) {
	JLabel label = new JLabel(text, SwingConstants.CENTER);
	label.setFont(new Font("Serif", Font.BOLD, 24));
	return label;
    }

    public void refreshParties() {
	listPanel.removeAll(); // Clear existing items
	List<Partie> parties = partieService.getAllParties();
	JPanel title = new JPanel();
	title.add(this.createJLabel("Nom"));
	title.add(this.createJLabel("Tour des"));
	title.add(this.createJLabel("Type de partie"));
	title.add(this.createJLabel("Partie finie"));
	title.add(this.createJLabel(""));
	title.add(this.createJLabel(""));
	title.setLayout(gridLayout);
	adjustComponentSizes(title);
	listPanel.add(title);
	for (Partie partie : parties) {
	    JPanel partieRow = createPartieRow(partie);
	    adjustComponentSizes(partieRow); // Adjust the size of each row
	    listPanel.add(partieRow);
	}
	adjustComponentSizes(menuButton);
	// Refresh the layout
	listPanel.revalidate();
	listPanel.repaint();
    }
    
    // Creates a JPanel for a single Partie row
    private JPanel createPartieRow(Partie partie) {
	JPanel partieRow = new JPanel();
	partieRow.setLayout(gridLayout); 

	JLabel nameLabel = this.createJLabel(partie.getName());
	JLabel movesLabel = this.createJLabel(partie.getFen().contains("w")?"Blanc":"Noir");
	JLabel gameType = this.createJLabel(partie.getComputer());
	JLabel gameOver = this.createJLabel(partie.isFinie()?"Oui":"Non");
	
	

	partieRow.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
	partieRow.add(nameLabel);
	partieRow.add(movesLabel);
	partieRow.add(gameType);
	partieRow.add(gameOver);

	JButton selectionner = new JButton("Selectionner");
	selectionner.setBackground(Color.GREEN);
	selectionner.addActionListener(e -> onPartieClick(partie));
	JButton supprimer = new JButton("Supprimer");
	supprimer.setBackground(Color.RED);
	supprimer.addActionListener(e -> supprimerPartie(partie));
	partieRow.add(selectionner);
	partieRow.add(supprimer);

	return partieRow;
    }

    // Adjusts the preferred size of a component to take up 1/10th of the height
    private void adjustComponentSizes(JComponent component) {
	component.setPreferredSize(new Dimension(4 * this.getWidth() / 5, this.getHeight() / 10));
	component.setMaximumSize(new Dimension(4 * this.getWidth() / 5, this.getHeight() / 10));
    }

    private void supprimerPartie(Partie partie) {
	partieService.delete(partie);
	this.refreshParties();
    }
    
    // This method will be called when a Partie is clicked
    private void onPartieClick(Partie partie) {
	// Use the injected ChessGameActionListener to handle the click
	ActionEvent event = new ActionEvent(partie, ActionEvent.ACTION_PERFORMED,
		ChessGameActionListener.REPRENDRE_PARTIE_CHARGER);
	chessGameActionListener.actionPerformed(event);
    }
}
