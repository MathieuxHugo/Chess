package main.ui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.google.inject.Inject;


public class ChessGameActionListener implements ActionListener {

    public static final String REPRENDRE_PARTIE = "Reprendre la Partie";

    public static final String NOUVELLE_PARTIE = "Nouvelle Partie";

    public static final String NOUVELLE_PARTIE_C_O = "Nouvelle Partie Contre l'Ordinateur";

    public static final String CHARGER_PARTIE = "Charger une partie";

    public static final String QUITTER = "Quitter";

    public static final String MENU = "MENU";
    
    public static final String REPRENDRE_MENU = "REPRENDRE_MENU";

    public static final String PARTIE = "PLATEAU";

    public static final String REPAINT = "Repaint";

    public static final String ANNULER = "Annuler";
    
    public static final String REPRENDRE_PARTIE_CHARGER = "CHARGER_PARTIE";

    public static final String SAUVEGARDER = "Sauvegarder";

    public static final String DISPLAY_GRAPH = "DISPLAY_GRAPH";
    
    private Partie partie;
    
    private JeuDEchec jeu;
    
    @Override
    public void actionPerformed(ActionEvent e) {
	switch (e.getActionCommand()) {
	case REPRENDRE_PARTIE:
	    jeu.show(PARTIE);
	    break;
	case NOUVELLE_PARTIE:
	    jeu.nouvellePartie(false);
	    break;
	case NOUVELLE_PARTIE_C_O:
	    jeu.nouvellePartie(true);
	    break;
	case CHARGER_PARTIE:
	    jeu.displayPartiePanel();
	    break;
	case QUITTER:
	    System.exit(0);
	    break;

	case SAUVEGARDER:
	    this.partie.sauvegarder();
	    break;
	case MENU:
	    if (partie.isFinie()) {
		jeu.show(MENU);;
	    } else {
		jeu.show(REPRENDRE_MENU);
	    }
	    break;
	case REPRENDRE_PARTIE_CHARGER:
	    main.model.Partie partieCharger = (main.model.Partie) e.getSource();
	    jeu.charger(partieCharger);
	    break;
	case ANNULER:
	    partie.annuler();
	    break;
	case DISPLAY_GRAPH:
	    partie.displayGraph();
	    break;
	default:
	    break;
	}
    }

    public JButton createButton(String nomEtAction) {
	JButton button = new JButton(nomEtAction);
	button.addActionListener(this);
	button.setActionCommand(nomEtAction);
	return button;
    }

    
    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    
    public void setJeu(JeuDEchec jeu) {
        this.jeu = jeu;
    }
    
    

}
