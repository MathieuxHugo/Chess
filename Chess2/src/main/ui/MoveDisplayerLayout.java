package main.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class MoveDisplayerLayout implements LayoutManager {

    @Override
    public void addLayoutComponent(String name, Component comp) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void removeLayoutComponent(Component comp) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void layoutContainer(Container parent) {
	int componentPerLine = 4;
	int horizontalMargin = parent.getWidth()/16;
	int verticalMargin = parent.getHeight()/8;
	int w = (parent.getWidth()-horizontalMargin)/componentPerLine;
	int h = parent.getHeight()/40;
	int i = 0, lineLength=0, height=0;
	for(Component c : parent.getComponents()) {
	    c.setBounds(horizontalMargin+lineLength, verticalMargin+height, w, h);
	    lineLength+=w;
	    i++;
	    if(i>componentPerLine-1) {
		i=0;
		lineLength=0;
		height+=h;
	    }
	    
	}
	
    }

}
