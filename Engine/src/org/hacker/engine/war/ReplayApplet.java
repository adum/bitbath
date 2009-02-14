package org.hacker.engine.war;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hacker.engine.Base64Coder;
import org.hacker.engine.ViewerComponent;
import org.hacker.engine.war.replay.ReplayReader;
import org.hacker.engine.war.replay.ReplayWarSpinalCordFactory;

/**
 * shows a replay of a match: data are in a param
 */
public class ReplayApplet extends JApplet {
    private boolean   finished;
    protected boolean paused = false;
    protected boolean running;
    private WarModel model;
    private ViewerComponent vc;

    @Override
    public void init() {
        // Execute a job on the event-dispatching thread:
        // creating this applet's GUI.
        try {
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    try {
                                                createGUI();
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                }
            });
        }
        catch (Exception e) {
            System.err.println("createGUI didn't successfully complete");
            e.printStackTrace();
        }
    }

    private void createGUI() throws Exception {
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

        String replay = getParameter("replay");

        byte[] replayBytes = Base64Coder.decode(replay);
                DataInputStream dinp = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(replayBytes)));
                int cookie = dinp.readInt();
                if (cookie != 0xDEAF1337)
                        throw new Error("wrong cookie: " + cookie);
                long seed = dinp.readLong();
                ReplayReader replayReader = new ReplayReader(dinp);
        List<WarSpinalCordFactory> spinalFactories = new ArrayList<WarSpinalCordFactory>();
                for (int i = 0; i < 2; i++) {
                        WarSpinalCordFactory fac = new ReplayWarSpinalCordFactory(replayReader);
                        spinalFactories.add(fac);
                }

        DataOutputStream dos = new DataOutputStream(new OutputStream() {
                public void write(byte[] b, int off, int len) {}
                public void write(int b) {}
        });
        final CountdownViewer viewer = new CountdownViewer();
                model = new WarModel(seed, spinalFactories, viewer, dos);

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
        Object winner = model.findWinner();
        vc.repaint();
        if (winner != null) {
            System.out.println(model);
            System.out.println("Victor: " + winner);
//            System.out.println(model.getReplay());
            finished = true;
        }
    }
}
