package org.hacker.engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ojvm.loading.AbsynClass;
import ojvm.loading.ClassInputStream;

/**
 * Swing UI for setting up a bot game
 */
public abstract class Chooser {
    protected Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
    public String classPath = "../hackjvm/rt/bin";

    protected abstract String getWindowTitle();

    private JCheckBox cbNative, cbNativeRT;
    private JTextField insCapField;
    protected JCheckBox opt3d;

    protected int getMaxBots() {
    return 2;
    }

    protected abstract Color[] getFactionCols();

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public void createAndShowGUI() {
    //Make sure we have nice window decorations.
    JFrame.setDefaultLookAndFeelDecorated(true);

    //Create and set up the window.
    JFrame frame = new JFrame(getWindowTitle());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container cp = frame.getContentPane();
    cp.add(new JLabel("Choose bots to pit against eachother, then hit Go"), BorderLayout.NORTH);

    JPanel cntr = new JPanel(new GridBagLayout());
    final GridBagConstraints c = new GridBagConstraints();
    c.gridx = c.gridy = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipadx = 8;
    c.ipady = 8;

    cbNative = new JCheckBox("Run natively (much faster and easier to debug, but not guaranteed to be same as production, so check before submitting)"
        , prefs.getBoolean("native", false));
    cbNative.setSelected(prefs.getBoolean("native", false));
    cbNative.setMnemonic('v');
    cbNative.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        prefs.putBoolean("native", cbNative.isSelected());
        }
    });
    c.gridwidth = 3;
    cntr.add(cbNative, c);
    c.gridwidth = 1;
    c.gridy++;

    cbNativeRT = new JCheckBox("Sync random with HackJVM when running in native mode"
        , prefs.getBoolean("nativeRT", false));
    cbNativeRT.setSelected(prefs.getBoolean("nativeRT", false));
    cbNativeRT.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        prefs.putBoolean("nativeRT", cbNativeRT.isSelected());
        }
    });
    c.gridwidth = 3;
    cntr.add(cbNativeRT, c);
    c.gridwidth = 1;
    c.gridy++;

    // instruction limits
    cntr.add(new JLabel("Instruction cap"), c);
    c.gridx++;
    insCapField = new JTextField("" + defaultInsMax(), 16);
    cntr.add(insCapField, c);
    c.gridy++;
    c.gridx = 0;

    // seed
    cntr.add(new JLabel("Random seed"), c);
    c.gridx++;
    int sd = prefs.getInt("seed", -99);
    if (sd == -99)
        sd = Math.abs(new Random().nextInt());
    final JTextField seedField = new JTextField("" + sd, 16);
    cntr.add(seedField, c);
    final JCheckBox cbNewSeed = new JCheckBox("Generate new seed on launch", true);
    cbNewSeed.setSelected(prefs.getBoolean("newseed", true));
    cbNewSeed.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        prefs.putBoolean("newseed", cbNewSeed.isSelected());
        }
    });
    cbNewSeed.setMnemonic('n');
    c.gridx++;
    cntr.add(cbNewSeed, c);
    c.gridy++;
    c.gridx = 0;
    seedField.getDocument().addDocumentListener(new DocumentListener() {
        public void change() {
        prefs.put("seed", seedField.getText());
        }
        public void changedUpdate(DocumentEvent e) {
        change();
        }
        public void insertUpdate(DocumentEvent e) {
        change();
        }
        public void removeUpdate(DocumentEvent e) {
        change();
        }
    });

    // num bots label
    c.gridx++;
    if (getMaxBots() == 4)
        cntr.add(new JLabel("Choose either two bots or four bots"), c);
    else
        cntr.add(new JLabel("Choose two bots"), c);
    c.gridy++;
    c.gridx = 0;

    for (int i = 0; i < getMaxBots(); i++) {
        createBotEntry(cntr, c, i);
    }

    cp.add(cntr, BorderLayout.CENTER);

    JPanel controlPanel = new JPanel(new BorderLayout());

    JPanel southPanel = new JPanel(new FlowLayout());
    controlPanel.add(southPanel, BorderLayout.SOUTH);

    JButton goButton = new JButton("Go!");
    goButton.setMnemonic('g');
    goButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        try {
            // gen new seed?
            if (cbNewSeed.isSelected()) {
            seedField.setText(""+Math.abs(new Random().nextInt()));
            }
            Random r = new Random(Integer.parseInt(seedField.getText()));

            runIt(true, r, cbNative.isSelected(), Integer.parseInt(insCapField.getText()), cbNativeRT.isSelected());
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        }
    });
    southPanel.add(goButton);

    JButton seriesButton = new JButton("Run Series...");
    seriesButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        try {
            runSeries();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        }
    });
    southPanel.add(seriesButton);

    if (give3Doption()) {
        opt3d = new JCheckBox("Use 3D");
        southPanel.add(opt3d);
    }

    cp.add(controlPanel, BorderLayout.SOUTH);

    //Display the window.
    frame.pack();
    frame.setVisible(true);
    }

    protected boolean give3Doption() {
    return false;
    }

    protected int defaultInsMax() {
    return 500000;
    }

    protected void runSeries() throws Exception {
    final JFrame frame = new JFrame("Running Series");

    Container cp = frame.getContentPane();
    cp.add(new JLabel("Matches will run until you close this window"), BorderLayout.NORTH);

    final JEditorPane botInfo;

    botInfo = new JEditorPane();
    botInfo.setContentType("text/html");
    botInfo.setEditable(false);
    botInfo.setText("running...");
    cp.add(botInfo, BorderLayout.CENTER);

    //Display the window.
    frame.pack();
    frame.setSize(500, 400);
    frame.setVisible(true);

    new Thread() {
        @Override
        public void run() {
        try {
            Random seeder = new Random();
            Map<Object, Integer> wins = new HashMap<Object, Integer>();
            while (true) {
            int seed = Math.abs(seeder.nextInt());
            Random r = new Random(seed);
            System.out.println("seed: " + seed);

            Object winner = runIt(false, r, cbNative.isSelected(), Integer.parseInt(insCapField.getText()), cbNativeRT.isSelected());
            if (winner != null) {
                winner = winner.toString();
                if (wins.containsKey(winner) == false)
                wins.put(winner, Integer.valueOf(0));
                Integer v = wins.get(winner);
                wins.put(winner, v + 1);
            }

            StringBuilder sb = new StringBuilder();
            sb.append("<table>");
            for (Map.Entry<Object, Integer> p : wins.entrySet()) {
                sb.append("<tr>");
                sb.append("<td>").append(p.getKey()).append("</td>");
                sb.append("<td>").append(p.getValue()).append("</td>");
                sb.append("</tr>");
            }
            sb.append("</table>");
            botInfo.setText(sb.toString());

            Thread.sleep(200);
            if (!frame.isShowing())
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }.start();
    }

    private void createBotEntry(JPanel cntr, final GridBagConstraints c, int i) {
    final String lfp = "lastfilepath" + i;
    final JFileChooser fc = new JFileChooser(prefs.get(lfp, ""));
    ExampleFileFilter filter = new ExampleFileFilter();
    filter.addExtension("class");
    filter.addExtension("jar");
    filter.setDescription("Class files");
    fc.setFileFilter(filter);

    c.gridx = 0;
    final JLabel lab = new JLabel("Bot " + i);
    lab.setBackground(getFactionCols()[i]);
    lab.setForeground(Color.WHITE);
    lab.setOpaque(true);
    cntr.add(lab, c);

    c.gridx++;
    final JTextField tf = new JTextField(prefs.get(lfp, ""), 54);
    cntr.add(tf, c);
    tf.getDocument().addDocumentListener(new DocumentListener() {
        public void change() {
        prefs.put(lfp, tf.getText());
        }
        public void changedUpdate(DocumentEvent e) {
        change();
        }
        public void insertUpdate(DocumentEvent e) {
        change();
        }
        public void removeUpdate(DocumentEvent e) {
        change();
        }
    });

    c.gridx++;
    createChooseButton(cntr, c, lfp, fc, tf);

    c.gridy++;
    }

    private void createChooseButton(JPanel cntr, final GridBagConstraints c, final String lfp, final JFileChooser fc, final JTextField tf) {
    JButton but = new JButton("Choose class or jar file...");
    cntr.add(but, c);
    but.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        int returnVal = fc.showOpenDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION)
            return;
        try {
            File file = fc.getSelectedFile();
            prefs.put(lfp, file.getCanonicalPath());
            tf.setText(file.getCanonicalPath());
        }
        catch (IOException e1) {
        }
        }
    });
    }

    protected String findMethodName(File file, boolean isJar) throws Exception {
    String className = null;
    if (!isJar){
        DataInputStream dinp = new DataInputStream(new FileInputStream(file));
        ClassInputStream cis = new ClassInputStream(dinp);
        AbsynClass loadedClass = new AbsynClass(cis);
        className = loadedClass.getDesc().getJavaForm();
    }
    else {
        JarInputStream jinp = new JarInputStream(new FileInputStream(file));
        Manifest manifest = jinp.getManifest();
        Attributes mainAttributes = manifest.getMainAttributes();
        className = mainAttributes.getValue(Attributes.Name.MAIN_CLASS);
        if (className == null)
        throw new Exception("jar file must contain a Main-Class attribute pointing to bot's main class");
    }
    return className;
    }

    protected abstract Object runIt(boolean vis, Random r, boolean natively, int insCap, boolean nativeRT) throws Exception;
}
