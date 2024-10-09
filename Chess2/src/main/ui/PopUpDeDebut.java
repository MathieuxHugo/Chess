package main.ui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.google.inject.Inject;

import main.service.PartieService;

public class PopUpDeDebut extends JDialog implements ItemListener, ActionListener, DocumentListener {

    public int niveau;

    public boolean bouN;

    private boolean lancerPartie;

    private JTextField profondeur;

    private JCheckBox blancOuNoir;

    private JTextField name;

    private JComboBox<String> comboboxNiveau;

    private List<String> nomDesParties;

    private boolean nomDePartieDisponible;

    private PartieService partieService;

    private Partie partie;

    @Inject
    public PopUpDeDebut(Frame owner, PartieService partieService, Partie partie) {
	super(owner, "Nouvelle Partie", true);
	this.partie = partie;
	this.partieService = partieService;
	this.setLayout(new GridLayout());
	this.setVisible(false);
	this.setDefaultCloseOperation(HIDE_ON_CLOSE);
	this.setContentPane(this.createPanel());
	this.bouN = true;
	this.lancerPartie = false;
	this.niveau=1;
    }

    private JPanel createPanel() {
	JPanel panel = new JPanel();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	this.name = new JTextField(dtf.format(now));
	this.name.getDocument().addDocumentListener(this);
	this.nomDePartieDisponible = true;
	panel.add(this.name);

	blancOuNoir = new JCheckBox("Jouer les : ", true);
	blancOuNoir.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	blancOuNoir.setSelectedIcon(new ColoredSquare(100, 100, Color.WHITE));
	blancOuNoir.setDisabledIcon(new ColoredSquare(100, 100, Color.BLACK));
	blancOuNoir.setIcon(new ColoredSquare(100, 100, Color.BLACK));
	blancOuNoir.setDisabledSelectedIcon(new ColoredSquare(100, 100, Color.BLACK));
	blancOuNoir.setPressedIcon(new ColoredSquare(100, 100, Color.BLACK));
	blancOuNoir.setRolloverIcon(new ColoredSquare(100, 100, Color.BLACK));
	blancOuNoir.setRolloverSelectedIcon(new ColoredSquare(100, 100, Color.WHITE));
	blancOuNoir.addChangeListener((l) -> {
	    this.bouN = blancOuNoir.isSelected();
	});
	panel.add(blancOuNoir);
	comboboxNiveau = new JComboBox<String>();
	comboboxNiveau.addItem("Facile");
	comboboxNiveau.addItem("Moyen");
	comboboxNiveau.addItem("Difficile");
	comboboxNiveau.addItem("Profondeur personalisée");
	comboboxNiveau.addItemListener(this);
	panel.add(comboboxNiveau);
	this.profondeur = new JTextField();
	this.profondeur.setVisible(false);
	panel.add(this.profondeur);
	JButton retour = new JButton("Retour");
	retour.setActionCommand("RETOUR");
	retour.addActionListener(this);
	panel.add(retour);
	JButton ok = new JButton("OK");
	ok.setActionCommand("OK");
	ok.addActionListener(this);
	panel.add(ok);
	return panel;
    }

    public boolean ouvrir(boolean contreOrdinateur) {
	this.nomDesParties = this.partieService.getNomsDesParties();
	this.profondeur.setVisible(contreOrdinateur);
	this.comboboxNiveau.setVisible(contreOrdinateur);
	this.blancOuNoir.setVisible(contreOrdinateur);
	this.setSize(500, 300);

	this.setLocation(this.getParent().getX() - 250 + this.getParent().getWidth() / 2,
		this.getParent().getY() - 250 + this.getParent().getHeight() / 2);
	this.setVisible(true);
	return this.lancerPartie;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
	switch ((String) e.getItem()) {
	case "Facile":
	    this.profondeur.setVisible(false);
	    this.niveau = 1;
	    break;
	case "Moyen":
	    this.profondeur.setVisible(false);
	    this.niveau = 4;
	    break;
	case "Difficile":
	    this.profondeur.setVisible(false);
	    this.niveau = 8;
	    break;
	default:
	    this.profondeur.setVisible(true);
	    this.profondeur.setText("");
	    break;
	}

	this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	switch (e.getActionCommand()) {
	case "RETOUR":
	    this.lancerPartie = false;
	    this.setVisible(false);
	    break;
	case "OK":
	    String nomDePartie = this.name.getText();
	    if (nomDePartieDisponible && nomDePartie != "") {
		try {
		    if (this.comboboxNiveau.isVisible()) {
			partie.nouvellePartie(this.niveau, !this.bouN, nomDePartie);
		    } else {
			partie.nouvellePartie(nomDePartie);
		    }

		} catch (FileNotFoundException e1) {
		    JOptionPane.showMessageDialog(this, e1.getMessage());
		    e1.printStackTrace();
		}
		this.lancerPartie = true;
		this.setVisible(false);
	    }
	    break;
	default:
	    break;
	}
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
	this.nameChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
	this.nameChanged();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
	this.nameChanged();
    }

    private void nameChanged() {
	String nomDePartie = this.name.getText();
	this.nomDePartieDisponible = !this.nomDesParties.contains(nomDePartie);
    }

}
