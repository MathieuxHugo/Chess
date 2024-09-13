package main.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.util.LinkedList;

public class MenuLayout implements LayoutManager {
	public MenuLayout() {
		super();
	}
	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
	}

	@Override
	public void layoutContainer(Container parent) {
		int w, wGap;
		int h;
		int i=1,j=0,n=parent.getComponentCount(),nH=n,nW=1;
		w=parent.getWidth()>parent.getHeight()? parent.getWidth()/2:2*parent.getWidth()/3;
		h=parent.getHeight()/(nH*2+1);
		if(h!=0) {
			while(w/h>10) {
				nH=(nH+1)/2;
				h=parent.getHeight()/(nH*2+1);
				nW++;
				w=w/2;
			}
			
			wGap=(parent.getWidth()-nW*w)/(nW+1);
			for(Component c : parent.getComponents()) {
				c.setFont(new Font("Palatino",Font.BOLD,(w+h)/25));
				c.setBounds(wGap+(w+wGap)*j, h*i, w, h);
				i=(i+2)%(nH*2);
				if(i==1) {
					j++;
				}
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		// TODO Auto-generated method stub
		return new Dimension(500,500);
	}

	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		// TODO Auto-generated method stub
		return new Dimension(1000,1000);
	}

	@Override
	public void removeLayoutComponent(Component arg0) {
	}

}
