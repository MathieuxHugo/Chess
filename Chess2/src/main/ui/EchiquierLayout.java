package main.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class EchiquierLayout implements LayoutManager {

	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void layoutContainer(Container parent) {
		int taille = parent.getWidth() / 30;
		if (parent.getComponentCount() == 1) {
			parent.getComponent(0).setBounds(taille, taille, parent.getWidth() - 2 * taille,
					parent.getHeight() - 2 * taille);
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
