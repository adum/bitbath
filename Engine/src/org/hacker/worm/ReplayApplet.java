package org.hacker.worm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hacker.engine.Bot;
import org.hacker.engine.ViewerComponent;

public class ReplayApplet extends JApplet {
    private boolean   finished;
    protected boolean paused = false;
    protected boolean running;
    private WormModel model;
    private ViewerComponent vc;

    @Override
    public void init() {
        // Execute a job on the event-dispatching thread:
        // creating this applet's GUI.
        try {
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        }
        catch (Exception e) {
            System.err.println("createGUI didn't successfully complete");
            e.printStackTrace();
        }
    }

    private void createGUI() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLACK);
        topPanel.setForeground(Color.WHITE);
        JPanel topButtonPanel = new JPanel(new FlowLayout());
        topButtonPanel.setBackground(Color.BLACK);
        topPanel.add(topButtonPanel, BorderLayout.WEST);
        
        JButton stepButton = new JButton("Step");
        stepButton.setMnemonic('s');
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stepToIt();
            }
        });
        topButtonPanel.add(stepButton);
        
        JButton goButton = new JButton("Go");
        goButton.setMnemonic('g');
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                running = true;
            }
        });
        topButtonPanel.add(goButton);
        
        JButton pauseButton = new JButton("Pause");
        pauseButton.setMnemonic('p');
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paused = !paused;
            }
        });
        topButtonPanel.add(pauseButton);

        String[] split = getParameter("replay").split(",");
        String replayMoves = split[split.length - 1];

        StringReader inp = new StringReader(replayMoves);
        
        int nbots = (split.length - 3) / 2;

        List<Bot> bots = new ArrayList<Bot>();
        List<ReplayWormSpinalCord> spinals = new ArrayList<ReplayWormSpinalCord>();
        for (int i = 0; i < nbots; i++) {
            Bot tb = null;
            ReplayWormSpinalCord spinal = new ReplayWormSpinalCord(inp);
            spinals.add(spinal);
            tb = new WormBot(spinal);
            bots.add(tb);
        }
        
        ReplayPlacer placer = new ReplayPlacer(split);

        model = new WormModel(bots, 0, placer);
        final CountdownViewer viewer = new CountdownViewer(nbots);

        vc = new ViewerComponent(model, viewer);
        final Timer tm = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        tm.setDelay(10);

        final JSlider slider = new JSlider(0, 1000);
        slider.setValue(tm.getDelay());
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                tm.setDelay(slider.getValue());
            }
        });
        topPanel.add(slider, BorderLayout.EAST);
        
        final Thread thread = (new Thread() {
            @Override
            public void run() {
                running = true;
                while (true) {
                    if (finished)
                        return;
                    if (paused) {
                        try {
                            Thread.sleep(50);
                        }
                        catch (InterruptedException e) {
                        }
                        continue;
                    }
                    if (!viewer.finishedCountdown()) {
                        vc.repaint();
                    }
                    else if (running)
                        stepToIt();
                    try {
                        if (tm.getDelay() > 0)
                            Thread.sleep(tm.getDelay());
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        getContentPane().add(vc, BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().setBackground(Color.BLACK);
    }

    protected void stepToIt() {
        if (finished) return;
        model.step();
        Bot winner = model.findWinner();
        vc.repaint();
        if (winner != null) {
            System.out.println(model);
            System.out.println("Victor: " + winner);
//            System.out.println(model.getReplay());
            finished = true;
        }
    }
}
