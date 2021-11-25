package interfaceGraphique;

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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PopUpDeDebut extends JDialog implements ItemListener,ActionListener{
	public int niveau;
	public boolean bouN;
	private boolean lancerPartie;
	private JTextField profondeur;
	public PopUpDeDebut(Frame owner) {
		super(owner,"Nouvelle Partie",true);
		this.setLayout(new GridLayout());
		this.setVisible(false);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setContentPane(this.createPanel());;
		this.niveau=0;
		this.bouN=true;
		this.lancerPartie=false;
	}
	
	private JPanel createPanel() {
		JPanel panel= new JPanel();
		JCheckBox blancOuNoir = new JCheckBox("Jouer les : ",true);
		blancOuNoir.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		blancOuNoir.setSelectedIcon(new ColoredSquare(100,100,Color.WHITE));
		blancOuNoir.setDisabledIcon(new ColoredSquare(100,100,Color.BLACK));
		blancOuNoir.setIcon(new ColoredSquare(100,100,Color.BLACK));
		blancOuNoir.setDisabledSelectedIcon(new ColoredSquare(100,100,Color.BLACK));
		blancOuNoir.setPressedIcon(new ColoredSquare(100,100,Color.BLACK));
		blancOuNoir.setRolloverIcon(new ColoredSquare(100,100,Color.BLACK));
		blancOuNoir.setRolloverSelectedIcon(new ColoredSquare(100,100,Color.WHITE));
		blancOuNoir.addChangeListener((l)->{this.bouN=blancOuNoir.isSelected();});
		panel.add(blancOuNoir); 
		JComboBox<String> combobox= new JComboBox<String>();
		combobox.addItem("Facile");
		combobox.addItem("Moyen");
		combobox.addItem("Difficile");
		combobox.addItem("Profondeur personalisée");
		combobox.addItemListener(this);
		panel.add(combobox);
		this.profondeur=new JTextField();
		this.profondeur.setVisible(false);
		panel.add(this.profondeur);
		JButton retour=new JButton("Retour");
		retour.setActionCommand("RETOUR");
		retour.addActionListener(this);
		panel.add(retour);
		JButton ok=new JButton("OK");
		ok.setActionCommand("OK");
		ok.addActionListener(this);
		panel.add(ok);
		return panel;
	}
	public boolean ouvrir(){
		this.setSize(500, 300);
		
		this.setLocation(this.getParent().getX()-250+this.getParent().getWidth()/2, this.getParent().getY()-250+this.getParent().getHeight()/2);
		this.setVisible(true);
		return this.lancerPartie;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		switch ((String) e.getItem()) {
		case "Facile":
			this.niveau=0;
			this.profondeur.setVisible(false);
			break;
		case "Moyen":
			this.niveau=2;
			this.profondeur.setVisible(false);
			break;
		case "difficile":
			this.niveau=4;
			this.profondeur.setVisible(false);
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
		switch(e.getActionCommand()) {
			case "RETOUR": 
				this.lancerPartie=false;
				this.setVisible(false);
				break;
			case "OK":
				try {
					Partie.getInstance().nouvellePartie(this.niveau,!this.bouN);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
					e1.printStackTrace();
				}
				this.lancerPartie=true;
				this.setVisible(false);
				break;
			default : 
				break;
		}
	}
}
