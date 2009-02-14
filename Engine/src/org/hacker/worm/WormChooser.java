package org.hacker.worm;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.jar.JarInputStream;

import org.hacker.engine.Bot;
import org.hacker.engine.Chooser;
import org.hacker.engine.DirectoryClassLoader;
import org.hacker.engine.GameUI;
import org.hacker.engine.JarClassLoader;
import org.hacker.engine.Viewer;

public class WormChooser extends Chooser {
        private Color[] factionCols = { new Color(0xA7341D), new Color(0x40760C), new Color(0xC8DE38), new Color(0xC672F6) };
        protected Color[] getFactionCols() {
                return factionCols;
        }

        protected int getMaxBots() {
        return 4;
    }
        

        @Override
    protected Object runIt(boolean vis, Random r, boolean natively, int insCap, boolean nativeRT) throws Exception {
        List<Bot> bots = new ArrayList<Bot>();
        for (int i = 0; i < getMaxBots(); i++) {
            final String lfp = "lastfilepath" + i;
            Bot tb;
            String fname = prefs.get(lfp, "");
            if (fname == null || fname.length() == 0) continue;
            File file = new File(fname);
            
            // determine class name
            boolean isJar = file.getName().endsWith(".jar");
            String className = findMethodName(file, isJar);

            WormSpinalCord spinal;
            // create spinal cord
            if (natively) {
                ClassLoader cl;
                if (!isJar)
                    cl = new DirectoryClassLoader(file.getParent());
                else
                    cl = new JarClassLoader(new JarInputStream(new FileInputStream(file)));
//                SingularClassLoader scl = new SingularClassLoader(buff, className);
                Class<?> c = cl.loadClass(className);
                Object obj = c.newInstance();
                spinal = new NativeWormSpinalCord(obj, className);
            }
            else {
                if (!isJar) {
                    spinal = new HackVMWormSpinalCord(classPath,
                                                className,
                                                r.nextLong(),
                                                file.getParent(),
                                                insCap);
                }
                else {
                    // jar
                    DataInputStream dinp = new DataInputStream(new FileInputStream(file));
                    spinal = new HackVMWormSpinalCord(classPath,
                            className,
                            r.nextLong(),
                            dinp,
                            insCap,
                            true);
                }
            }
            tb = new WormBot(spinal);
            bots.add(tb);
        }
        WormModel model = new WormModel(bots, r.nextLong(), null);
        if (vis) {
            Viewer viewer = new WormViewer(bots.size());
            GameUI gui = new GameUI(model, viewer);
            gui.run(false);
        }
        else {
            long st = System.currentTimeMillis();
            while (true) {
                model.step();
                Bot winner = model.findWinner();
                if (winner != null) {
                    System.out.println("time: " + (System.currentTimeMillis() - st));
                    System.out.println(model);
                    System.out.println("Victor: " + winner);
                    return model;
                }
            }
        }
        return null;
    }

    @Override
    protected String getWindowTitle() {
        return "Wormageddon Bot Simulator";
    }

    public static void main(String[] args) {
        final Chooser tc = new WormChooser();
        
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
