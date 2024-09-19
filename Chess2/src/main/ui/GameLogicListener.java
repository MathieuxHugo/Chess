package main.ui;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import main.chess.Coup;

public class GameLogicListener implements MouseListener, MouseMotionListener{
    
    private MouseEvent firstMouseEvent;
    
    private Plateau plateau;
    
    private Partie partie;
    
    public GameLogicListener(Partie partie) {
	this.firstMouseEvent = null;
	this.partie = partie;
    }
    
    public void setPlateau(Plateau plateau) {
	this.plateau = plateau;
    }
    
    public void reset() {
	this.firstMouseEvent = null;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void mouseExited(MouseEvent e) {
	Case c = (Case) e.getSource();
	c.setSubrillance(false);
    }

    @Override
    public void mousePressed(MouseEvent e) {
	this.firstMouseEvent = e;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	if (!this.plateau.isEnAttente()) {
	    int finDePartie = this.plateau.movePiece((Case) this.firstMouseEvent.getSource(), e.getX(), e.getY());
	    this.partie.finPartie(finDePartie);
	}
	this.firstMouseEvent = null;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
	int dx = Math.abs(e.getX() - this.firstMouseEvent.getX());
	int dy = Math.abs(e.getY() - this.firstMouseEvent.getY());
	int t, x, y;
	Case c = (Case) this.firstMouseEvent.getSource();
	Component compo;
	Image icon;
	if ((dx > 5 || dy > 5) && !this.plateau.isEnAttente()) {
	    this.plateau.freeIcons();
	    icon = c.getIcon();
	    if (icon != null) {
		t = c.getWidth();
		x = e.getX() + c.getX();
		y = e.getY() + c.getY();
		this.plateau.drawDraggedIcon(icon, x, y, t);
	    }
	    if (!c.isDraging()) {
		this.plateau.startDragging(c);
	    }
	    this.plateau.repaint();
	}
    }

    @Override
    public void mouseEntered(MouseEvent e) {
	if (this.firstMouseEvent != null) {
	    Case c = (Case) e.getSource();
	    if (this.plateau.isInCoupsPossibles(c.getCoordonnee())) {
		c.setSubrillance(true);
	    }
	}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub
	
    }
}
