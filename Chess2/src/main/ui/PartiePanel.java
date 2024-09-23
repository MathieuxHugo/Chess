package main.ui;
import javax.inject.Inject;
import javax.swing.*;

import main.service.PartieService;
import main.model.Partie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PartiePanel extends JPanel {
    
    private PartieService partieService;
    private ChessGameActionListener chessGameActionListener;
    private JPanel listPanel;

    // Constructor where Guice will inject the PartieService and ChessGameActionListener
    @Inject
    public PartiePanel(PartieService partieService, ChessGameActionListener chessGameActionListener) {
        this.partieService = partieService;
        this.chessGameActionListener = chessGameActionListener;
        setLayout(new BorderLayout());
        
        // List panel where we'll add each Partie
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // Display vertically
        
        JScrollPane scrollPane = new JScrollPane(listPanel);
        add(scrollPane, BorderLayout.CENTER);
        
        add(new JLabel("List des parties"), BorderLayout.NORTH);

        add(chessGameActionListener.createButton(ChessGameActionListener.MENU), BorderLayout.SOUTH);
    }

   
    public void refreshParties() {
        listPanel.removeAll(); // Clear existing items
        List<Partie> parties = partieService.getAllParties();

        for (Partie partie : parties) {
            listPanel.add(createPartieRow(partie));
        }

        // Refresh the layout
        listPanel.revalidate();
        listPanel.repaint();
    }

    // Creates a JPanel for a single Partie row
    private JPanel createPartieRow(Partie partie) {
        JPanel partieRow = new JPanel();
        
        
        partieRow.setLayout(new GridLayout(1, 3)); // 3 columns: Name, Number of Moves, White/Black
        JLabel nameLabel = new JLabel(partie.getName());
        JLabel movesLabel = new JLabel(String.valueOf(partie.getCoups().split(",").length/2));
        JLabel colorLabel = new JLabel(partie.getComputer());
        
        partieRow.add(nameLabel);
        partieRow.add(movesLabel);
        partieRow.add(colorLabel);

        // Make the row clickable
        JButton clickButton = new JButton("Select");
        clickButton.addActionListener(e -> onPartieClick(partie));
        partieRow.add(clickButton);

        return partieRow;
    }

    // This method will be called when a Partie is clicked
    private void onPartieClick(Partie partie) {
        // Use the injected ChessGameActionListener to handle the click
        ActionEvent event = new ActionEvent(partie, ActionEvent.ACTION_PERFORMED, ChessGameActionListener.REPRENDRE_PARTIE_CHARGER);
        chessGameActionListener.actionPerformed(event);
    }
}
