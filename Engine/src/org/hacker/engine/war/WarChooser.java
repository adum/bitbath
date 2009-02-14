package org.hacker.engine.war;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.jar.JarInputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.hacker.engine.Chooser;
import org.hacker.engine.ClassLoaderFactory;
import org.hacker.engine.DirectoryClassLoader;
import org.hacker.engine.GameUI;
import org.hacker.engine.JarClassLoader;
import org.hacker.engine.NativeRandomLoaderFactory;
import org.hacker.engine.PassThruLoaderFactory;
import org.hacker.engine.war.jme.WarJme;
import org.hacker.engine.war.replay.ReplayReader;
import org.hacker.engine.war.replay.ReplayWarSpinalCordFactory;

import com.jme.app.AbstractGame.ConfigShowMode;

/**
 * Swing UI for starting a BitBath game
 */
public class WarChooser extends Chooser {
        private Color[] factionCols = { new Color(0xA7341D), new Color(0x1122bb), new Color(0xC8DE38), new Color(0xC672F6) };
        protected Color[] getFactionCols() {
                return factionCols;
        }

        // used for testing replays
        private boolean readFromReplayFile = false;
        private boolean writeReplay = false;

    @Override
    protected String getWindowTitle() {
        return "BitBath Simulator";
    }

    protected int getMaxBots() {
        return 2;
    }

    @Override
    protected boolean give3Doption() {
        return true;
    }

    @Override
    protected Object runIt(boolean vis, Random r, boolean natively, int insCap, boolean nativeRT) throws Exception {
        if (readFromReplayFile)
                writeReplay = false;
        long seed = r.nextLong();
        List<WarSpinalCordFactory> spinalFactories = new ArrayList<WarSpinalCordFactory>();
        for (int i = 0; i < getMaxBots(); i++) {
            final String lfp = "lastfilepath" + i;
            String fname = prefs.get(lfp, "");
            if (fname == null || fname.length() == 0) continue;
            File file = new File(fname);
            
            // determine class name
            boolean isJar = file.getName().endsWith(".jar");
            String className = findMethodName(file, isJar);

            WarSpinalCordFactory fac = null;
            // create spinal cord
            if (natively) {
                ClassLoaderFactory clf;
                if (!isJar) {
                        if (nativeRT)
                                clf = new NativeRandomLoaderFactory(file.getParent(), this.classPath, r.nextLong());
                        else {
                                ClassLoader cl = new DirectoryClassLoader(file.getParent());
                                clf = new PassThruLoaderFactory(cl);
                        }
                }
                else {
                    ClassLoader cl = new JarClassLoader(new JarInputStream(new FileInputStream(file)));
                    clf = new PassThruLoaderFactory(cl);
                }
                fac = new NativeWarSpinalCordFactory(clf, className);
                spinalFactories.add(fac);
            }
            else {
                // hackjvm
                if (!isJar) {
                        fac = new HackVMWarSpinalCordFactory(classPath,
                                                className,
                                                r.nextLong(),
                                                file.getParent(),
                                                1000000);
                }
                else {
                    // jar
                    FileInputStream finp = new FileInputStream(file);
                    byte[] bytes = new byte[(int) file.length()];
                    finp.read(bytes);
                    ByteArrayInputStream binp = new ByteArrayInputStream(bytes);
                        fac = new HackVMWarSpinalCordFactory(classPath,
                            className,
                            r.nextLong(),
                            binp,
                            1000000,
                            true);
                }
                spinalFactories.add(fac);
            }
        }
        
        // test replay
                if (readFromReplayFile) {
                        DataInputStream dinp = new DataInputStream(new GZIPInputStream(new FileInputStream("replay.bitbath")));
                        int cookie = dinp.readInt();
                        if (cookie != 0xDEAF1337)
                                throw new Error("wrong cookie: " + cookie);
                        seed = dinp.readLong();
                        spinalFactories.clear();
                        ReplayReader replayReader = new ReplayReader(dinp);
                        for (int i = 0; i < 2; i++) {
                                WarSpinalCordFactory fac = new ReplayWarSpinalCordFactory(replayReader);
                                spinalFactories.add(fac);
                        }
        }
        
                // create a dummy
                DataOutputStream dos = new DataOutputStream(new OutputStream() {
                        public void write(byte[] b, int off, int len) {}
                        public void write(int b) {}
                });

                if (vis) {
                    if (!opt3d.isSelected()) {
                        // 2D
                        if (writeReplay)
                            dos = new DataOutputStream(new GZIPOutputStream(new FileOutputStream("replay.bitbath")));
                        WarViewer viewer = new WarViewer();
                        WarModel model = new WarModel(seed, spinalFactories, viewer, dos);
                        GameUI gui = new GameUI(model, viewer);
                        gui.run(false);
                    }
                    else {
                        // 3D
                        final WarJme warjme = new WarJme();
                WarModel model = new WarModel(seed, spinalFactories, warjme, dos);
                warjme.setModel(model);
                warjme.setConfigShowMode(ConfigShowMode.AlwaysShow);
                new Thread() {
                    @Override
                    public void run() {
                        warjme.start();
                    }
                }.start();
                    }
        }
        else {
                // running a series of matches
            long st = System.currentTimeMillis();
                        WarModel model = new WarModel(seed, spinalFactories, null, dos);
            while (true) {
                model.step();
                Object winner = model.findWinner();
                if (winner != null) {
                    System.out.println("time: " + (System.currentTimeMillis() - st) + ", game time: " + model.getTime());
                    System.out.println("Victor: " + winner);
                    return winner;
                }
            }
        }
                return null;
    }

        protected int defaultInsMax() {
                return 1000000;
        }
        
    public static void main(String[] args) {
        final Chooser tc = new WarChooser();
        
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-cp"))
                tc.classPath = args[++i];
        }
        
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                tc.createAndShowGUI();
            }
        });
    }
}
