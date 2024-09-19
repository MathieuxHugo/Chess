package main.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class PlateauLayout implements LayoutManager {
	private int ligne;
	private int colonne;
	private boolean reversed;
	private int hGap;
	private int wGap;
	private int margin;
	
	public PlateauLayout(int ligne, int colonne) {
		this.ligne=ligne;
		this.colonne=colonne;
		this.reversed=false;
	}
	
	public boolean isReversed() {
		return reversed;
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}
	
	public int gethGap() {
		return hGap;
	}

	public void sethGap(int hGap) {
		this.hGap = hGap;
	}

	public int getwGap() {
		return wGap;
	}

	public void setwGap(int wGap) {
		this.wGap = wGap;
	}

	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void layoutContainer(Container parent) {
		int i=0;
		int size = parent.getHeight();
		if(parent.getHeight()>parent.getWidth()) {
		    size=parent.getWidth();
		}

		margin = size/40;
		size=size-2*margin;
		int w=size/this.colonne,h=size/this.ligne;
		wGap=(size%this.colonne);
		hGap=(size%this.ligne);
		wGap=wGap/2+wGap%2;
		hGap=hGap/2+hGap%2;
		if(reversed) {
			for(Component c : parent.getComponents()) {
				c.setBounds(margin+wGap+w*(this.colonne-(1+i%this.colonne)), margin+hGap+h*(this.ligne-(1+i/this.ligne)), w, h);
				i++;
			}
		}
		else {
			for(Component c : parent.getComponents()) {
				c.setBounds(margin+wGap+w*(i%this.colonne), margin+hGap+h*(i/this.ligne), w, h);
				i++;
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeLayoutComponent(Component arg0) {
		// TODO Auto-generated method stub

	}

	
	public int getMargin() {
	    return margin;
	}
	
	

}
