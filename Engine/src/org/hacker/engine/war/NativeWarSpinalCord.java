package org.hacker.engine.war;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

import org.hacker.engine.war.order.MoveOrder;
import org.hacker.engine.war.order.Order;
import org.hacker.engine.war.order.StopOrder;

/**
 * calls Java natively
 */
public class NativeWarSpinalCord implements WarSpinalCord {
    private final Object brain;
    private Method thinkMethod;
    private Method buildMethod;
        private String className;

    public NativeWarSpinalCord(final Object brain, String className) {
        this.brain = brain;
        this.className = className;
        
        // find the method -- just go by name
        Method[] methods = brain.getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().equals("think")) {
                thinkMethod = m;
            }
            if (m.getName().equals("build")) {
                buildMethod = m;
            }
        }
        if (thinkMethod == null)
            throw new IllegalArgumentException("no 'think' method found");
        if (buildMethod == null)
            throw new IllegalArgumentException("no 'build' method found");
    }

    @Override
        public String toString() {
                return className;
        }

        public Order think(int[][] radio, double dx, double dy, double x, double y, boolean moving, int terrain, int id, int ourType,
                double hp, double maxHP, double range, double time,
                double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] radioOut) {
        Object[] args = { dx, dy, x, y, moving, terrain, id, ourType, hp, maxHP, range, time,
                        objX, objY, objID, objFaction, objType, radioOut};
        try {
            Object object = thinkMethod.invoke(brain, args);
            if (object == null)
                return null;
            Order order;
            Field typeField = object.getClass().getDeclaredField("orderType");
            int orderType = typeField.getInt(object);
            
            // did they emit radio?
            try {
                                Field radioField = object.getClass().getDeclaredField("radio");
                                Object radObj = radioField.get(object);
                                radio[0] = int[].class.cast(radObj);
//                              System.out.println(id + " emit " + radio[0][1]);
                        } catch (Exception e) {
                        }

                        switch (orderType) {
                case Order.MOVE:
                    double destX = object.getClass().getDeclaredField("destX").getDouble(object);
                    double destY = object.getClass().getDeclaredField("destY").getDouble(object);
                    order = new MoveOrder(destX, destY);
                    break;
                                case Order.STOP:
                                        order = new StopOrder();
                                        break;
                default:
                    return null;
            }
            return order;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int build(double dx, double dy, double x, double y, int terrain, int id, int buildItem,
                double hp, double maxHP, double time,
                double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] radioOut) {
        Object[] args = { dx, dy, x, y, terrain, id, buildItem, hp, maxHP, time,
                        objX, objY, objID, objFaction, objType, radioOut};
        try {
            Object object = buildMethod.invoke(brain, args);
            if (object == null)
                return -1;
            return (Integer)object;
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public void addInfoProperties(Properties p) {
    }

        public int getLastInstructionCount() {
                // no idea, of course
                return 0;
        }
}
