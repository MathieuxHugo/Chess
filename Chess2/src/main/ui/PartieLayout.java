package main.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class PartieLayout implements LayoutManager {

    @Override
    public void addLayoutComponent(String name, Component comp) { }

    @Override
    public void removeLayoutComponent(Component comp) { }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return parent.getSize();
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(100, 100);
    }

    @Override
    public void layoutContainer(Container parent) {
        int width = parent.getWidth();
        int height = parent.getHeight();

        int minWidth = width / 10; // Minimum width for left and right components

        // Determine square panel size
        int squareSize = Math.min(width - 2 * minWidth, height);

        // Set bounds for the components
        Component leftComp = parent.getComponent(0);
        Component squareComp = parent.getComponent(1);
        Component rightComp = parent.getComponent(2);
        
        int sideCompWidth = (width - squareSize)/2;
        leftComp.setBounds(0, 0, sideCompWidth, height);
        squareComp.setBounds(minWidth + (width - 2 * minWidth - squareSize) / 2, (height - squareSize) / 2, squareSize, squareSize);
        rightComp.setBounds(width - sideCompWidth, 0, sideCompWidth, height);
    }
}
