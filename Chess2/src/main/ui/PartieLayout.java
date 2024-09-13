package main.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class PartieLayout implements LayoutManager {

	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
	}

	@Override
	public void layoutContainer(Container parent){
		if(parent.getComponentCount()==3) {
			int w=parent.getWidth();
			int h=parent.getHeight();
			if(w>h) {
				parent.getComponent(0).setBounds(0, 0, (w-h)/2, h);
				parent.getComponent(1).setBounds((w+h)/2, 0, (w-h)/2, h);
				parent.getComponent(2).setBounds((w-h)/2,0,h,h);
			}
			else {
				parent.getComponent(0).setBounds(0, 0, w, (h-w)/2);
				parent.getComponent(1).setBounds(0, (w+h)/2, w, (h-w)/2);
				parent.getComponent(2).setBounds(0,(h-w)/2,w,w);
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		return null;
	}

	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		return null;
	}

	@Override
	public void removeLayoutComponent(Component arg0) {

	}

}
