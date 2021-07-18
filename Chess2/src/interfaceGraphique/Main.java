package interfaceGraphique;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Main implements ActionListener {
	private CardLayout cards;
	private JPanel cardHolder;
	private JPanel partie;
	private JPanel menu;
	private Partie jeu;
	private final String REPRENDRE_PARTIE="Reprendre la Partie";
	private final String NOUVELLE_PARTIE="Nouvelle Partie";
	private final String NOUVELLE_PARTIE_C_O="Nouvelle Partie Contre l'Ordinateur";
	private final String CHARGER_PARTIE="Charger une partie";
	private final String QUITTER="Quitter";
	private final String MENU="MENU";
	private final String PARTIE="PLATEAU";
	private final String REPAINT="Repaint";
	private final String ANNULER="Annuler";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main m=new Main();
	}
	public Main() {
		JFrame fenetre=new JFrame();
		cards=new CardLayout();
		menu=createMenu();
		cardHolder=new JPanel();
		fenetre.setTitle("Jeu d'Echec");
		fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fenetre.setMinimumSize(new Dimension(500,500));
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
		fenetre.add(cardHolder);
		cardHolder.setLayout(cards);
		cardHolder.add(menu,MENU);
		try {
			partie=this.createPartie();
			cardHolder.add(partie,PARTIE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(fenetre, e.getMessage());
			fenetre.dispose();//Si les icones n'existent pas on ferme la fenetre.
		}
		cards.first(cardHolder);
	}
	private JPanel createMenu() {
		JPanel menu=new JPanel();
		menu.setName("Menu");
		menu.setVisible(true);
		menu.setOpaque(true);
		menu.setBackground(Color.BLACK);
		menu.setLayout(new MenuLayout());
		menu.add(this.createButton(this.NOUVELLE_PARTIE));
		menu.add(this.createButton(this.NOUVELLE_PARTIE_C_O));
		menu.add(this.createButton(this.CHARGER_PARTIE));
		menu.add(this.createButton(this.QUITTER));
		return menu;
	}
	private JPanel reprendreMenu() {
		JPanel menu=new JPanel();
		menu.setName("Menu");
		menu.setVisible(true);
		menu.setOpaque(true);
		menu.setBackground(Color.BLACK);
		menu.setLayout(new MenuLayout());
		menu.add(this.createButton(this.REPRENDRE_PARTIE));
		menu.add(this.createButton(this.NOUVELLE_PARTIE));
		menu.add(this.createButton(this.NOUVELLE_PARTIE_C_O));
		menu.add(this.createButton(this.CHARGER_PARTIE));
		menu.add(this.createButton(this.QUITTER));
		return menu;
	}
	private JPanel createPartie() throws FileNotFoundException {
		JPanel partie=new JPanel();
		JPanel menuD=new JPanel();
		JPanel menuG=new JPanel();
		JButton retour=this.createButton(MENU);
		JButton repaint= this.createButton(REPAINT);
		JButton annuler= this.createButton(ANNULER);
		partie.setLayout(new PartieLayout());
		menuG.setLayout(new MenuLayout());
		menuG.setBackground(Color.BLACK);
		menuD.setLayout(new MenuLayout());
		menuD.setBackground(Color.BLACK);
		menuG.add(retour);
		partie.add(menuG);
		menuD.add(annuler);
		repaint.setActionCommand(REPAINT);
		repaint.addActionListener(this);
		menuD.add(repaint);
		partie.add(menuD);
		this.jeu = new Partie();
		partie.add(this.jeu);
		partie.setVisible(true);
		return partie;
	}
	private JButton createButton(String nomEtAction) {
		JButton button=new JButton(nomEtAction);
		button.addActionListener(this);
		button.setActionCommand(nomEtAction);
		return button;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case REPRENDRE_PARTIE:
				cards.show(cardHolder, PARTIE);
				break;
			case NOUVELLE_PARTIE:
				partie.remove(this.jeu);
			try {
				this.jeu=new Partie();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

				JOptionPane.showMessageDialog(this.partie, e1.getMessage());
				System.exit(0);
				
			}
				partie.add(this.jeu);
				partie.repaint();
				cards.show(cardHolder, PARTIE);
				break;
			case NOUVELLE_PARTIE_C_O:
				partie.remove(this.jeu);
				try {
					this.jeu=new Partie(1,true);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

					JOptionPane.showMessageDialog(this.partie, e1.getMessage());
					System.exit(0);
					
				}	
					partie.add(this.jeu);
					partie.repaint();
					cards.show(cardHolder, PARTIE);
				break;
			case CHARGER_PARTIE:
				break;
			case QUITTER:
				System.exit(0);
			break;
			case MENU:
				if(this.jeu.isFinie()) {
					this.cardHolder.remove(menu);
					this.menu=this.createMenu();
					this.cardHolder.add(menu,MENU);
				}
				else {
					this.cardHolder.remove(menu);
					this.menu=this.reprendreMenu();
					this.cardHolder.add(menu,MENU);
				}
				cards.show(cardHolder, MENU);
				break;
			case REPAINT:
				this.jeu.repaint();
				break;
			case ANNULER:
				this.jeu.annuler();
			default:
				break;
		}
	}

}
