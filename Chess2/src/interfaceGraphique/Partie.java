package interfaceGraphique;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import chess.Coordonnee;
import chess.Coup;
import chess.Echiquier;
import chess.Piece;
import chess.PriseEnPassant;
import chess.Promotion;
import chess.Roque;
import chess.Promotion.Choix;
import ia.Ordinateur;


public class Partie extends JPanel implements MouseMotionListener,MouseListener{
	private JPanel plateau;
	private MouseEvent firstMouseEvent;
	public static int taille=8;
	private static Color couleurBords=new Color(182,155,98);
	private static String[] tabNomPiece= {"Pion","Tour","Cavalier","Fou","Reine","Roi"};
	private int gap;
	private PlateauLayout plateauLayout;
	private LinkedList<Coup> coupsPossibles;
	private boolean changerDeSens;
	private Ordinateur ordinateur;
	private Image[] iconPiecesBlanches;
	private Image[] iconPiecesNoires;
	private Case [][]tabCase;
	private Echiquier echiquier;
	private String messageVictoireBlanc;
	private String messageVictoireNoir;
	private Icon iconVictoireBlanc;
	private Icon iconVictoireNoir;
	private Icon iconPat;
	private Image sablier;
	private boolean finie;
	private boolean enAttente;
	private Promotion promotion;
	public Partie() throws FileNotFoundException {
		this.plateau=new JPanel();
		this.plateauLayout=new PlateauLayout(taille,taille);
		this.plateauLayout.setReversed(false);
		this.plateau.setLayout(this.plateauLayout);
		this.plateau.setBackground(Color.black);
		this.setLayout(new EchiquierLayout());
		this.add(this.plateau);
		this.setBackground(couleurBords);
		this.firstMouseEvent=null;
		this.coupsPossibles=null;
		this.initIcon();
		this.changerDeSens=true;
		this.ordinateur=null;
		this.tabCase=new Case[taille][taille];
		int i,j;
		for(i=0;i<taille;i++) {
			for(j=0;j<taille;j++) {
				tabCase[j][i]=new Case(new Coordonnee(j,i));
				tabCase[j][i].addMouseListener(this);
				tabCase[j][i].addMouseMotionListener(this);
				this.plateau.add(tabCase[j][i]);
			}
		}
		this.echiquier=new Echiquier();
		this.updatePlateau();
		this.messageVictoireNoir="Les Noirs ont gagnés!!!";
		this.messageVictoireBlanc="Les Blancs ont gagnés!!!";
		this.iconVictoireBlanc=null;
		this.iconVictoireNoir=null;
		this.iconPat=null;
		this.finie=false;
		this.promotion=null;
		this.ordinateur=null;
		this.setOpaque(true);
	}
	public Partie(int niveau,boolean couleurOrdinateur) throws FileNotFoundException {
		this.plateau=new JPanel();
		this.plateauLayout=new PlateauLayout(taille,taille);
		this.plateauLayout.setReversed(false);
		this.plateau.setLayout(this.plateauLayout);
		this.plateau.setBackground(Color.black);
		this.setLayout(new EchiquierLayout());
		this.add(this.plateau);
		this.setBackground(couleurBords);
		this.firstMouseEvent=null;
		this.coupsPossibles=null;
		this.initIcon();
		this.changerDeSens=true;
		this.ordinateur=null;
		this.tabCase=new Case[taille][taille];
		int i,j;
		for(i=0;i<taille;i++) {
			for(j=0;j<taille;j++) {
				tabCase[j][i]=new Case(new Coordonnee(j,i));
				tabCase[j][i].addMouseListener(this);
				tabCase[j][i].addMouseMotionListener(this);
				this.plateau.add(tabCase[j][i]);
			}
		}
		this.echiquier=new Echiquier();
		this.updatePlateau();
		this.messageVictoireNoir="Les Noirs ont gagnés!!!";
		this.messageVictoireBlanc="Les Blancs ont gagnés!!!";
		this.iconVictoireBlanc=null;
		this.iconVictoireNoir=null;
		this.iconPat=null;
		this.finie=false;
		this.promotion=null;
		this.ordinateur=new Ordinateur(niveau,couleurOrdinateur,this.echiquier);
		if(couleurOrdinateur) {
			this.echiquier.joue(this.ordinateur.joue());
			this.updatePlateau();
		}
	}
	private void updatePlateau() {
		int i,j;
		Piece temp;
		for(i=0;i<taille;i++) {
			for(j=0;j<taille;j++) {
				temp=this.echiquier.getPiece(i, j);
				if(temp!=null) {
					if(temp.isBlanc()) {
						switch(temp.toString()) {
							case Piece.PION:
								this.tabCase[i][j].setIcon(this.iconPiecesBlanches[0]);
								break;
							case Piece.TOUR:
								this.tabCase[i][j].setIcon(this.iconPiecesBlanches[1]);
							break;
							case Piece.CAVALIER:
								this.tabCase[i][j].setIcon(this.iconPiecesBlanches[2]);
								break;
							case Piece.FOU:
								this.tabCase[i][j].setIcon(this.iconPiecesBlanches[3]);
								break;
							case Piece.REINE:
								this.tabCase[i][j].setIcon(this.iconPiecesBlanches[4]);
								break;
							case Piece.ROI:
								this.tabCase[i][j].setIcon(this.iconPiecesBlanches[5]);
								break;
						}
					}
					else {
						switch(temp.toString()) {
							case Piece.PION:
								this.tabCase[i][j].setIcon(this.iconPiecesNoires[0]);
								break;
							case Piece.TOUR:
								this.tabCase[i][j].setIcon(this.iconPiecesNoires[1]);
							break;
							case Piece.CAVALIER:
								this.tabCase[i][j].setIcon(this.iconPiecesNoires[2]);
								break;
							case Piece.FOU:
								this.tabCase[i][j].setIcon(this.iconPiecesNoires[3]);
								break;
							case Piece.REINE:
								this.tabCase[i][j].setIcon(this.iconPiecesNoires[4]);
								break;
							case Piece.ROI:
								this.tabCase[i][j].setIcon(this.iconPiecesNoires[5]);
								break;
						}
					}
				}
				else {
					this.tabCase[i][j].setIcon(null);
				}
			}
		}
	}
	private void initIcon() throws FileNotFoundException {
		int i=0;
		ImageIcon imgIcon;
		File imageURL;
		String nomFichier;
		this.iconPiecesBlanches=new Image[6];
		this.iconPiecesNoires=new Image[6];
		for(i=0;i<6;i++) {
			nomFichier=tabNomPiece[i]+"Blanc"+".png";
			imageURL = new File("icons/"+nomFichier);
			if (!imageURL.exists()) {
				System.err.println("Resource not found: " + "icons/"+nomFichier);
				throw new FileNotFoundException("Icone du "+ tabNomPiece[i] +" "+"Blanc"+ ": icons/"+nomFichier +" non trouvé.");
			} else {
				try {
					imgIcon = new ImageIcon(imageURL.toURI().toURL(), tabNomPiece[i] +" "+"Blanc");
					iconPiecesBlanches[i]=imgIcon.getImage();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			nomFichier=tabNomPiece[i]+"Noir"+".png";
			imageURL = new File("icons/"+nomFichier);
			if (!imageURL.exists()) {
				System.err.println("Resource not found: " + "icons/"+nomFichier);
				throw new FileNotFoundException("Icone du "+ tabNomPiece[i] +" "+"Noir"+ ": icons/"+nomFichier +" non trouvé.");
			} else {
				try {
					imgIcon = new ImageIcon(imageURL.toURI().toURL(), tabNomPiece[i] +" "+"Noir");
					iconPiecesNoires[i]=imgIcon.getImage();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		imageURL = new File("icons/sablier.png");
		if (!imageURL.exists()) {
			System.err.println("Resource not found: " + "icons/sablier.png");
			throw new FileNotFoundException("Icone du sablier : icons/sablier.png non trouvée.");
		} else {
			try {
				imgIcon = new ImageIcon(imageURL.toURI().toURL(), "Sablier");
				this.sablier=imgIcon.getImage();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private boolean contient(Coordonnee c) {
		if(this.coupsPossibles!=null) {
			for(Coup coup : this.coupsPossibles) {
				if(coup.getArrivee().equals(c)) {
					return true;
				}
			}
		}
		return false;
	}
	private Case getCase(Coordonnee c) {
		return this.tabCase[c.getX()][c.getY()];
	}
	
	public boolean isFinie() {
		return finie;
	}
	
	public void enAttenteOrdinateur() {
		Graphics g=this.getGraphics();
		System.out.println(sablier.getHeight(this));
		g.drawImage(sablier, 0, 0, this);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		gap=this.plateau.getX();
		int i,t=(this.getWidth()-2*gap)/taille;
		gap=gap+this.plateauLayout.gethGap();
		g.setColor(Color.black);
		g.setFont(new Font(null,Font.BOLD,t/4));
		if(this.plateauLayout.isReversed()) {
			for(i=1;i<taille;i++) {
				g.fillRect(gap+t*i-1,0,3,this.getHeight());
				g.fillRect(0,gap+t*i-1,this.getWidth(),3);
				g.drawString(String.valueOf((char)('A'+taille-i)), gap+t*(i-1)+7*t/16,gap-gap/5);
				g.drawString(String.valueOf((char)('A'+taille-i)), gap+t*(i-1)+7*t/16,this.getHeight()-gap/5);
				g.drawString(String.valueOf(i), gap/5,gap+t*(i-1)+t/2+t/10);
				g.drawString(String.valueOf(i), this.getWidth()-7*gap/10,gap+t*(i-1)+t/2+t/10);
			}
			g.drawString(String.valueOf((char)('A'+taille-i)), gap+t*(i-1)+7*t/16,this.getHeight()-gap/5);
			g.drawString(String.valueOf((char)('A'+taille-i)), gap+t*(i-1)+9*t/20,gap-gap/5);
			g.drawString(String.valueOf(i), gap/5,gap+t*(i-1)+t/2+t/10);
			g.drawString(String.valueOf(i), this.getWidth()-7*gap/10,gap+t*(i-1)+t/2+t/10);
		}
		else {
			for(i=1;i<taille;i++) {
				g.fillRect(gap+t*i-1,0,3,this.getHeight());
				g.fillRect(0,gap+t*i-1,this.getWidth(),3);
				g.drawString(String.valueOf((char)('A'+(i-1))), gap+t*(i-1)+7*t/16,gap-gap/5);
				g.drawString(String.valueOf((char)('A'+(i-1))), gap+t*(i-1)+7*t/16,this.getHeight()-gap/5);
				g.drawString(String.valueOf(taille-i+1), gap/5,gap+t*(i-1)+t/2+t/10);
				g.drawString(String.valueOf(taille-i+1), this.getWidth()-7*gap/10,gap+t*(i-1)+t/2+t/10);
			}
			g.drawString(String.valueOf((char)('A'+(i-1))), gap+t*(i-1)+7*t/16,this.getHeight()-gap/5);
			g.drawString(String.valueOf((char)('A'+(i-1))), gap+t*(i-1)+9*t/20,gap-gap/5);
			g.drawString(String.valueOf(taille-i+1), gap/5,gap+t*(i-1)+t/2+t/10);
			g.drawString(String.valueOf(taille-i+1), this.getWidth()-7*gap/10,gap+t*(i-1)+t/2+t/10);
		}
		g.drawLine(0, 0, this.getWidth(), this.getHeight());
		g.drawLine(this.getWidth(), 0, 0, this.getHeight());
		g.drawLine(1, 0, this.getWidth(), this.getHeight()-1);
		g.drawLine(this.getWidth()-1, 0, 0, this.getHeight()-1);
		g.drawLine(0, 1, this.getWidth()-1, this.getHeight());
		g.drawLine(this.getWidth(), 1, 1, this.getHeight());
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		int dx=Math.abs(e.getX()-this.firstMouseEvent.getX());
		int dy=Math.abs(e.getY()-this.firstMouseEvent.getY());
		int t,x,y;
		Case c=(Case)this.firstMouseEvent.getSource();
		Case coinHG,coinBG,coinHD,coinBD;
		Component compo;
		Image icon;
		if((dx>5 || dy>5)) {
			for(Case tab[] : this.tabCase) {
				for(Case temp : tab) {
					temp.freeDragIcon();
					temp.freeChoix();
				}
			}
			icon=c.getIcon();
			if(icon!=null) {
				t=c.getWidth();
				x=e.getX()+c.getX();
				y=e.getY()+c.getY();
				compo=this.plateau.getComponentAt(x-t/2,y-t/2);
				if(compo!=null && compo.getClass()==Case.class) {
					coinHG=(Case)compo;
					coinHG.setDragIcon(icon, x-t/2-coinHG.getX(), y-t/2-coinHG.getY());
				}
				compo=this.plateau.getComponentAt(x-t/2,y+t/2);
				if(compo!=null && compo.getClass()==Case.class) {
					coinBG=(Case)compo;
					coinBG.setDragIcon(icon, x-t/2-coinBG.getX(), y-t/2-coinBG.getY());
					//coinBG.repaint();
				}
				compo=this.plateau.getComponentAt(x+t/2,y-t/2);
				if(compo!=null && compo.getClass()==Case.class) {
					coinHD=(Case)compo;
					coinHD.setDragIcon(icon, x-t/2-coinHD.getX(), y-t/2-coinHD.getY());
					//coinHD.repaint();
				}
				compo=this.plateau.getComponentAt(x+t/2,y+t/2);
				if(compo!=null && compo.getClass()==Case.class) {
					coinBD=(Case)compo;
					coinBD.setDragIcon(icon, x-t/2-coinBD.getX(), y-t/2-coinBD.getY());
					//coinBD.repaint();
				}
			}
			if(!c.isDraging()) {
				if(this.coupsPossibles!=null) {
					for(Coup coup : this.coupsPossibles) {
						this.getCase(coup.getArrivee()).setSubrillance(false);
					}
				}
				this.coupsPossibles=this.echiquier.selectionne(c.getCoordonnee());
				c.setDraging(true);
				this.promotion=null;
			}
			this.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(this.firstMouseEvent!=null) {
			Case c=(Case) e.getSource();
			if(this.contient(c.getCoordonnee())) {
				c.setSubrillance(true);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Case c = (Case)e.getSource();
		c.setSubrillance(false);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.firstMouseEvent=e;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Case cDest,c=(Case)this.firstMouseEvent.getSource();
		Component compo;
		String options[]= {"Oui","Non"};
		int finDePartie=-1;
		if(c.isDraging()) {
			compo=this.plateau.getComponentAt(e.getX()+c.getX(), e.getY()+c.getY());
			if(compo!=null && compo.getClass()==Case.class) {
				cDest=(Case)compo;
				finDePartie=this.joue(cDest.getCoordonnee());
				cDest.setSubrillance(false);
			}
			for(Case tab[] : this.tabCase) {
				for(Case temp : tab) {
					temp.freeDragIcon();
				}
			}
			c.setDraging(false);
			this.coupsPossibles=null;
		}
		else {
			if(this.coupsPossibles!=null) {
				for(Coup coup : this.coupsPossibles) {
					this.getCase(coup.getArrivee()).setSubrillance(false);
				}
				finDePartie=this.joue(c.getCoordonnee());
				this.coupsPossibles=this.echiquier.selectionne(c.getCoordonnee());
				if(this.coupsPossibles!=null) {
					for(Coup coup : this.coupsPossibles) {
						this.getCase(coup.getArrivee()).setSubrillance(true);
					}
				}
			}
			else {	
				//System.out.println("prout");
				if(this.promotion!=null) {
					if(c.isChoix()) {
						this.promotion.choixPromo(c.getChoix());
						c.setIcon(c.getChoixIcon());
						finDePartie=this.echiquier.joue(this.promotion);
						this.coupsPossibles=null;
					}
					for(Case tab[] : this.tabCase) {
						for(Case temp : tab) {
							temp.freeChoix();
						}
					}
					this.promotion=null;
				}
				this.coupsPossibles=this.echiquier.selectionne(c.getCoordonnee());
				if(this.coupsPossibles!=null) {
					for(Coup coup : this.coupsPossibles) {
						this.getCase(coup.getArrivee()).setSubrillance(true);
					}
				}
			}
		}
		this.updatePlateau();
		this.repaint();
		switch(finDePartie) {
			case Echiquier.BLANC_GAGNE:
				JOptionPane.showMessageDialog(this,this.messageVictoireBlanc, "Victoire des Blancs!!!", JOptionPane.INFORMATION_MESSAGE, this.iconVictoireBlanc);
				this.finie=true;
				break;
			case Echiquier.NOIR_GAGNE:
				JOptionPane.showMessageDialog(this,this.messageVictoireNoir, "Victoire des Noirs!!!", JOptionPane.INFORMATION_MESSAGE, this.iconVictoireNoir);
				this.finie=true;
				break;
			case Echiquier.PAT:
				JOptionPane.showMessageDialog(this,"Il y a Pat, c'est au tour d'un joueur mais il ne peut pas jouer et il n'est pas en echec. \nPersonne ne remporte la partie.", "Pat", JOptionPane.INFORMATION_MESSAGE, this.iconPat);
				this.finie=true;
				break;
			case Echiquier.PARTIE_CONTINUE:
				if(this.ordinateur!=null && this.echiquier.isTour()==this.ordinateur.isCouleur()) {
					this.enAttenteOrdinateur();
					this.joueOrdinateur();
				}
				break;
			default:
				if(this.promotion!=null) {
					Image icon[]=this.promotion.getpDepart().isBlanc()?this.iconPiecesBlanches:this.iconPiecesNoires;
					if(this.promotion.getArrivee().getX()==0) {
						this.getCase(this.promotion.getArrivee()).setChoixIcon(Choix.Reine, icon[4]);
						this.getCase(this.promotion.getArrivee().droite()).setChoixIcon(Choix.Fou, icon[3]);
						this.getCase(this.promotion.getArrivee().droite().droite()).setChoixIcon(Choix.Cavalier, icon[2]);
						this.getCase(this.promotion.getArrivee().droite().droite().droite()).setChoixIcon(Choix.Tour, icon[1]);
					}
					else {
						if(this.promotion.getArrivee().getX()==taille-1) {
							this.getCase(this.promotion.getArrivee()).setChoixIcon(Choix.Reine, icon[4]);
							this.getCase(this.promotion.getArrivee().gauche()).setChoixIcon(Choix.Fou, icon[3]);
							this.getCase(this.promotion.getArrivee().gauche().gauche()).setChoixIcon(Choix.Cavalier, icon[2]);
							this.getCase(this.promotion.getArrivee().gauche().gauche().gauche()).setChoixIcon(Choix.Tour, icon[1]);
						}
						else {
							if(this.promotion.getArrivee().getX()==taille-2) {
								this.getCase(this.promotion.getArrivee()).setChoixIcon(Choix.Reine, icon[4]);
								this.getCase(this.promotion.getArrivee().gauche()).setChoixIcon(Choix.Fou, icon[3]);
								this.getCase(this.promotion.getArrivee().gauche().gauche()).setChoixIcon(Choix.Cavalier, icon[2]);
								this.getCase(this.promotion.getArrivee().droite()).setChoixIcon(Choix.Tour, icon[1]);
							}
							else {
								this.getCase(this.promotion.getArrivee()).setChoixIcon(Choix.Reine, icon[4]);
								this.getCase(this.promotion.getArrivee().droite()).setChoixIcon(Choix.Fou, icon[3]);
								this.getCase(this.promotion.getArrivee().droite().droite()).setChoixIcon(Choix.Cavalier, icon[2]);
								this.getCase(this.promotion.getArrivee().gauche()).setChoixIcon(Choix.Tour, icon[1]);
							}
						}
					}
				}
				break;
		}
		this.firstMouseEvent=null;
		this.updatePlateau();
		this.repaint();
	}
	private void joueOrdinateur() {
		switch(this.echiquier.joue(this.ordinateur.joue())) {
		case Echiquier.BLANC_GAGNE:
			JOptionPane.showMessageDialog(this,this.messageVictoireBlanc, "Victoire des Blancs!!!", JOptionPane.INFORMATION_MESSAGE, this.iconVictoireBlanc);
			this.finie=true;
			break;
		case Echiquier.NOIR_GAGNE:
			JOptionPane.showMessageDialog(this,this.messageVictoireNoir, "Victoire des Noirs!!!", JOptionPane.INFORMATION_MESSAGE, this.iconVictoireNoir);
			this.finie=true;
			break;
		case Echiquier.PAT:
			JOptionPane.showMessageDialog(this,"Il y a Pat, c'est au tour d'un joueur mais il ne peut pas jouer et il n'est pas en echec. \nPersonne ne remporte la partie.", "Pat", JOptionPane.INFORMATION_MESSAGE, this.iconPat);
			this.finie=true;
			break;
		case Echiquier.PARTIE_CONTINUE:
			break;
		default:
			JOptionPane.showMessageDialog(this, "L'ordinateur ne sait pas quoi jouer!!!");
			break;
		}
		this.updatePlateau();
	}
	private int joue(Coordonnee destination) {
		Coup coup;
		int retour=-1,i=1;
		if(this.coupsPossibles!=null && !this.coupsPossibles.isEmpty()) {
			coup=this.coupsPossibles.get(0);
			while(!coup.getArrivee().equals(destination) && i<this.coupsPossibles.size()) {
				coup=this.coupsPossibles.get(i);
				i++;
			}
			if(coup.getArrivee().equals(destination)) {
				if(coup.getClass()==Promotion.class) {
					this.promotion=(Promotion)coup;
				}
				else {
					retour=this.echiquier.joue(coup);
					/*if(coup.getClass()==Coup.class) {
						this.getCase(coup.getArrivee()).setIcon(this.getCase(coup.getDepart()).getIcon());
						this.getCase(coup.getDepart()).setIcon(null);
					}
					else {
						if(coup.getClass()==Roque.class) {
							Roque r=(Roque)coup;
							this.getCase(r.getArrivee()).setIcon(this.getCase(r.getDepart()).getIcon());
							this.getCase(r.getDepart()).setIcon(null);
							this.getCase(r.getArriveeTour()).setIcon(this.getCase(r.getDepartTour()).getIcon());
							this.getCase(r.getDepartTour()).setIcon(null);
						}
						else {
							PriseEnPassant p=(PriseEnPassant)coup;
							this.getCase(p.getArrivee()).setIcon(this.getCase(p.getDepart()).getIcon());
							this.getCase(p.getDepart()).setIcon(null);
							this.getCase(p.getcPrise()).setIcon(null);
						}
					}*/
				}
			}
		}
		return retour;
	}
	public void annuler() {
		// TODO Auto-generated method stub
		this.echiquier.annuler();
		if(this.ordinateur!=null && this.ordinateur.isCouleur()==this.echiquier.isTour()) {
			this.echiquier.annuler();
		}
		this.updatePlateau();
		this.repaint();
	}
	
}
