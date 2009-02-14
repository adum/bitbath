package org.hacker.engine;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * creates a Swing UI for displaying a game
 */
public class GameUI {
    private GameModel model;
    private Viewer viewer;
    private ViewerComponent vc;
    private boolean finished;
    protected boolean paused = false;
    protected boolean running;
    private JEditorPane botInfo;
    private Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
        private boolean showVis = true;

    public GameUI(GameModel model, Viewer viewer) {
        this.model = model;
        this.viewer = viewer;
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI(boolean mainWin) {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Watch Bots");
        if (mainWin)
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        else
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                finished = true;
            }
        });
        
        vc = new ViewerComponent(model, viewer);
        frame.getContentPane().add(vc, BorderLayout.CENTER);
        
        JPanel controlPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        controlPanel.add(buttonPanel, BorderLayout.NORTH);
        JButton stepButton = new JButton("Step");
        stepButton.setMnemonic('s');
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stepToIt();
            }
        });
        buttonPanel.add(stepButton);

        JButton goButton = new JButton("Go");
        goButton.setMnemonic('g');
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                running = true;
            }
        });
        buttonPanel.add(goButton);

        JButton pauseButton = new JButton("Pause");
        pauseButton.setMnemonic('p');
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                running = !running;
            }
        });
        buttonPanel.add(pauseButton);

        final Timer tm = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        tm.setDelay(10);

        
        final Thread thread = (new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (paused || finished) return;
                    if (running)
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

        final JSlider slider = new JSlider(0, 1000);
        slider.setValue(tm.getDelay());
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                tm.setDelay(slider.getValue());
            }
        });
        frame.getContentPane().add(slider, BorderLayout.SOUTH);

        JButton fastButton = new JButton("Fast");
        fastButton.setMnemonic('f');
        fastButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                slider.setValue(0);
            }
        });
        buttonPanel.add(fastButton);
        
        final JCheckBox infoCheckBox = new JCheckBox("Info");
        infoCheckBox.setSelected(prefs.getBoolean("info", true));
        vc.showInfo = infoCheckBox.isSelected();
        infoCheckBox.setMnemonic('i');
        infoCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prefs.putBoolean("info", infoCheckBox.isSelected());
                vc.showInfo = infoCheckBox.isSelected();
            }
        });
        buttonPanel.add(infoCheckBox);

        final JCheckBox visCheckBox = new JCheckBox("Draw", true);
        showVis = visCheckBox.isSelected();
        visCheckBox.setMnemonic('d');
        visCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showVis = visCheckBox.isSelected();
            }
        });
        buttonPanel.add(visCheckBox);
        
        botInfo = new JEditorPane();
        botInfo.setContentType("text/html");
        botInfo.setEditable(false);
        controlPanel.add(botInfo, BorderLayout.CENTER);
        
        frame.getContentPane().add(controlPanel, BorderLayout.EAST);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    protected void stepToIt() {
        if (finished) return;
        model.step();
        Object winner = model.findWinner();
        if (prefs.getBoolean("info", true))
            updateBotInfo();
        if (showVis)
                vc.repaint();
        if (winner != null) {
            System.out.println(model);
            System.out.println("winner: " + winner);
//            System.out.println("Victor: " + winner);
//            System.out.println(model.getReplay());
            List<Object> eliminated = model.getEliminated();
            int plc = 1;
            for (int i = eliminated.size() - 1; i >= 0; i--) {
                Object bot = eliminated.get(i);
                System.out.println(plc++ + ": " + bot);
            }
            finished = true;
        }
    }

    private void updateBotInfo() {
        Properties[] props = model.getBotTextInfo();
        StringBuilder sb = new StringBuilder();
        for (Properties p : props) {
            sb.append("<h2>Bot</h2>");
            sb.append("<table>");
            for (Object key : p.keySet()) {
                sb.append("<tr>");
                sb.append("<td>").append(key).append("</td>");
                sb.append("<td>").append(p.get(key)).append("</td>");
                sb.append("</tr>");
            }
            sb.append("</table>");
        }
        botInfo.setText(sb.toString());
    }

    public void run(final boolean mainWin) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(mainWin);
            }
        });
    }
}
