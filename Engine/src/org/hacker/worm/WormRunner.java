package org.hacker.worm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hacker.engine.Bot;
import org.hacker.engine.GameUI;
import org.hacker.engine.Viewer;

public class WormRunner {
    static boolean NATIVE = false;
    static boolean VIS    = true;
    static boolean SHOW_CYCLES    = true;

    public static void main(String[] args) throws Exception {
//        long seed = new Random().nextLong();
//        Random r = new Random(seed);
//        List<Bot> bots = new ArrayList<Bot>();
//        List<HackVMWormSpinalCord> spinals = new ArrayList<HackVMWormSpinalCord>();
//        for (int i = 0; i < 2; i++) {
//            Bot tb = null;
//            if (NATIVE) {
////                NativeWormSpinalCord spinal = new NativeWormSpinalCord(new SearcherFlat());
////                tb = new WormBot(spinal);
//            }
//            else {
//                HackVMWormSpinalCord spinal = new HackVMWormSpinalCord("../hackjvm/rt/bin;bin",
//                                        "org.hacker.worm.sample.SearcherFlat",
//                                        r.nextLong(),
//                                        "",
//                                        0);
//                spinals.add(spinal);
//                tb = new WormBot(spinal);
//            }
//            bots.add(tb);
//        }
//        WormModel model = new WormModel(bots, r.nextLong(), null);
//        if (VIS) {
//            Viewer viewer = new WormViewer(2);
//            GameUI gui = new GameUI(model, viewer);
//            gui.run(true);
//        }
//        else {
//            long st = System.currentTimeMillis();
//            while (true) {
//                long stThink = System.currentTimeMillis();
//                Bot winner = model.step();
//                if (SHOW_CYCLES && !NATIVE) {
//                    String s = "";
//                    for (HackVMWormSpinalCord cord : spinals) {
//                        s = s + cord.getLastCycles() + " ";
//                    }
//                    double tm = System.currentTimeMillis() - stThink;
//                    tm /= 1000.0;
//                    double d = spinals.get(0).getLastCycles() / tm;
//                    System.out.println(s + " cps: " + d);
//                }
////                System.out.println(model);
//                if (winner != null) {
//                    System.out.println("time: " + (System.currentTimeMillis() - st));
//                    System.out.println(model);
//                    System.out.println("Victor: " + winner);
//                    break;
//                }
//            }
//        }
    }
}
