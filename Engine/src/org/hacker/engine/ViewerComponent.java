package org.hacker.engine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * Swing component to wrap a view
 */
public class ViewerComponent extends JComponent {
    private Viewer viewer;
    private GameModel model;
    public boolean showInfo;

    public ViewerComponent(GameModel model, Viewer viewer) {
        this.viewer = viewer;
        this.model = model;
        setPreferredSize(new Dimension(860, 600));
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        viewer.draw(model, (Graphics2D)g, getSize().width, getSize().height, showInfo);
    }
}
