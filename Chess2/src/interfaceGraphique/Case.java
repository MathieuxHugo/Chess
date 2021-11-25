package interfaceGraphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JComponent;

import chess.Coordonnee;
import chess.Echiquier;
import chess.Promotion.Choix;

public class Case extends JComponent{
	private Image icon;
	private Coordonnee coordonnee;
	private int debutdragX;
	private int debutdragY;
	private boolean isDraging;
	private boolean subrillance;
	private boolean selectionnee;
	private boolean dernierCoup;
	private static Color couleurCasesBlanches=Color.WHITE;
	private static Color couleurCasesNoires=Color.DARK_GRAY;
	private static Color couleurDernierCoup=new Color(80,120,40);
	private Image dragIcon;
	private Image choixIcon;
	private Choix choix;
	public Case(Image icon, Coordonnee coordonnee) {
		super();
		this.icon = icon;
		this.coordonnee = coordonnee;
		this.isDraging = false;
		this.subrillance = false;
		this.selectionnee = false;
		this.dernierCoup = false;
		this.dragIcon = null;
		this.choix=null;
		this.choixIcon=null;
	}
	public Case(Coordonnee coordonnee) {
		super();
		this.icon = null;
		this.coordonnee = coordonnee;
		this.isDraging = false;
		this.subrillance = false;
		this.selectionnee = false;
		this.dernierCoup = false;
		this.dragIcon = null;
		this.choix=null;
		this.choixIcon=null;
	}
	public void setChoixIcon(Choix choix, Image icon) {
		this.choix=choix;
		this.choixIcon=icon;
	}
	public boolean isChoix() {
		return this.choix!=null;
	}
	public Choix getChoix() {
		return this.choix;
	}
	
	public Image getChoixIcon() {
		return choixIcon;
	}
	public void freeChoix() {
		this.choix=null;
		this.choixIcon=null;;
	}
	// getter et setter sur les booléens
	public boolean isVide() {
		return this.icon==null;
	}
	public boolean isSubrillance() {
		return subrillance;
	}
	public void setSubrillance(boolean subrillance) {
		this.subrillance = subrillance;
	}
	public boolean isDraging() {
		return isDraging;
	}
	public void setDraging(boolean isDraging) {
		this.isDraging = isDraging;
	}
	public boolean isDernierCoup() {
		return dernierCoup;
	}
	public void setDernierCoup(boolean dernierCoup) {
		this.dernierCoup = dernierCoup;
	}
	// getter et setter sur icon
	public Image getIcon() {
		return icon;
	}
	public void setIcon(Image icon) {
		this.icon = icon;
	}
	//getter et setter sur dragIcon
	public void setDragIcon(Image img, int x, int y) {
		this.dragIcon=img;
		this.debutdragX=x;
		this.debutdragY=y;
	}
	public void freeDragIcon() {
		this.dragIcon=null;
	}
	public Coordonnee getCoordonnee() {
		return coordonnee;
	}
	public void setCoordonnee(Coordonnee coordonnee) {
		this.coordonnee = coordonnee;
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		int i,tailleOval;
		Color subri=couleurCasesBlanches.darker();
		if(this.choixIcon==null) {
			if((this.coordonnee.getX()+this.coordonnee.getY())%2==0) {
				if(this.dernierCoup) {
					g.setColor(couleurDernierCoup);
				}
				else {
					g.setColor(couleurCasesBlanches);
				}
			}
			else {
				if(this.dernierCoup) {
					g.setColor(couleurDernierCoup.darker());
				}
				else {
					g.setColor(couleurCasesNoires);
				}
			}
			
			if(this.selectionnee) {
				g.setColor(subri);
			}
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			if(this.subrillance) {
				g.setColor(subri);
				if(icon!=null) {
					for(i=0;i<5;i++) {
						g.drawOval(i+this.getWidth()/20, i+this.getHeight()/20, this.getWidth()-2*i-this.getWidth()/10, this.getHeight()-2*i-this.getHeight()/10);
					}
				}
				else {
					tailleOval=this.getWidth()/3;
					g.fillOval((this.getWidth()/2)-tailleOval/2, (this.getHeight()/2)-tailleOval/2, tailleOval, tailleOval);
				}
			}
			if(icon!=null && !isDraging) {
					g.drawImage(this.icon, 0, 0,this.getWidth(),this.getHeight(), this);
			}
			if(this.dragIcon!=null) {
				g.drawImage(this.dragIcon, this.debutdragX, this.debutdragY, this.getWidth(), this.getHeight(), this);
			}
		}
		else {
			g.setColor(couleurCasesBlanches.darker());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.drawImage(this.choixIcon, 0, 0,this.getWidth(),this.getHeight(), this);
		}
	}
		
}
