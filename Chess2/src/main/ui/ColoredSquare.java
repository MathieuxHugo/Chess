package main.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class ColoredSquare implements Icon {
	private int height;
	private int width;
	private Color color;

	public ColoredSquare(int width, int height, Color color) {
		super();
		this.height = height;
		this.width = width;
		this.color = color;
	}

	@Override
	public int getIconHeight() {
		return height;
	}

	@Override
	public int getIconWidth() {
		return width;
	}

	@Override
	public void paintIcon(Component arg0, Graphics g, int x, int y) {
		g.setColor(this.color);
		g.fillRect(x, y, this.width, this.height);
	}

}
