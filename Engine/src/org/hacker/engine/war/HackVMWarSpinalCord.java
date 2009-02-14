package org.hacker.engine.war;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

import ojvm.data.FieldNotFoundE;
import ojvm.data.InternalClass;
import ojvm.data.InternalMethod;
import ojvm.data.JavaArray;
import ojvm.data.JavaDoubleValue;
import ojvm.data.JavaInstance;
import ojvm.data.JavaIntValue;
import ojvm.data.JavaLongValue;
import ojvm.data.JavaNonnullReferenceValue;
import ojvm.data.JavaObject;
import ojvm.data.JavaReferenceValue;
import ojvm.data.JavaValue;
import ojvm.machine.ControlUnit;
import ojvm.util.Descriptor;
import ojvm.util.NameAndDescriptor;

import org.hacker.engine.HackHelper;
import org.hacker.engine.war.order.MoveOrder;
import org.hacker.engine.war.order.Order;
import org.hacker.engine.war.order.StopOrder;

public class HackVMWarSpinalCord implements WarSpinalCord {
    private final ControlUnit cu;
    private InternalMethod    thinkMethod, buildMethod;
    private JavaObject        classObject;
    private boolean thinkFailure = false;

    public HackVMWarSpinalCord(String cp, String className, long seed, DataInputStream dinp, int insCap, boolean isJar) throws Exception {
        cu = new ControlUnit(cp);
        cu.maxInstructions = insCap;
        
        if (dinp != null) {
            if (isJar)
                cu.useExtraClassInputJarStream(className, dinp);
            else
                cu.useExtraClassInput(className, dinp);
        }

        initClass(className, seed);
    }

    public HackVMWarSpinalCord(String cp, String className, long seed, String rootDir, int insCap) throws Exception {
        cu = new ControlUnit(cp);
        cu.maxInstructions = insCap;
        
        if (rootDir != null) cu.useExtraClassInput(rootDir);

        initClass(className, seed);
    }

    private void initClass(String className, long seed) throws Exception {
        // set random seed
        {
            // Find and initialize the starting class
            Descriptor nameDesc = new Descriptor("java.util.Random", Descriptor.JAVA_FORM);
            InternalClass randClass = cu.findClass(nameDesc);
            cu.initializeClass(randClass);
            // grab the internal starting seed var and set it to passed in value
            NameAndDescriptor key = new NameAndDescriptor("hackedStartingSeed", "long");
            randClass.putstatic(key, new JavaLongValue(seed));
        }

        // Find and initialize the starting class
        Descriptor nameDesc = new Descriptor(className, Descriptor.JAVA_FORM);
        InternalClass startClass = cu.findClass(nameDesc);
        cu.initializeClass(startClass);
        classObject = new JavaInstance(startClass);
        {
            // call constructor with no args
            NameAndDescriptor key = new NameAndDescriptor("<init>", "()V");
            InternalMethod im = startClass.findMethod(key);

            JavaValue[] args = new JavaValue[1];
            args[0] = new JavaNonnullReferenceValue(classObject);
            try {
                cu.run(im, args);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.err.println("failed to call init");
            }
        }
        // Find think
        NameAndDescriptor methodKey = new NameAndDescriptor("think", "(DDDDZIIIDDDD[D[D[I[I[I[[I)Ljava/lang/Object;");
        thinkMethod = startClass.findMethod(methodKey);
        if (!(thinkMethod.isPublic())) {
            throw new Exception("Method in class " + className + " should be declared public");
        }
        // Find build
        methodKey = new NameAndDescriptor("build", "(DDDDIIIDDD[D[D[I[I[I[[I)I");
        buildMethod = startClass.findMethod(methodKey);
        if (!(buildMethod.isPublic())) {
            throw new Exception("Method in class " + className + " should be declared public");
        }
    }
    
    public Order think(int[][] radio, double dx, double dy, double x, double y, boolean moving, int terrain, int id, int ourType, double hp,
                double maxHP, double range, double time, double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType,
                int[][] radioOut) {
        try {
            // convert args to hackjvm form
            List<JavaValue> args = new ArrayList<JavaValue>();
            JavaReferenceValue rv = new JavaNonnullReferenceValue(classObject);
            args.add(rv);
            args.add(HackHelper.createDoubleArg(dx));
            args.add(HackHelper.createDoubleArg(dy));
            args.add(HackHelper.createDoubleArg(x));
            args.add(HackHelper.createDoubleArg(y));
            args.add(new JavaIntValue(moving ? 1 : 0));
//            args.add(new JavaBooleanValue(moving));
            args.add(new JavaIntValue(terrain));
            args.add(new JavaIntValue(id));
            args.add(new JavaIntValue(ourType));
            args.add(new JavaDoubleValue(hp));
            args.add(new JavaDoubleValue(maxHP));
            args.add(new JavaDoubleValue(range));
            args.add(new JavaDoubleValue(time));
            args.add(HackHelper.createArrayArg(cu, objX));
            args.add(HackHelper.createArrayArg(cu, objY));
            args.add(HackHelper.createArrayArg(cu, objID));
            args.add(HackHelper.createArrayArg(cu, objFaction));
            args.add(HackHelper.createArrayArg(cu, objType));
            JavaValue radioOutArg = HackHelper.create2DArrayArg(cu, radioOut);
            HackHelper.copy2Darray(radioOut, radioOutArg);
            args.add(radioOutArg);

            // call it
            cu.instructionCount = 0;
            cu.cleanFrames();
            cu.run(thinkMethod, args.toArray(new JavaValue[0]));
//            lastCycles = cu.instructionCount;
            // get order object from stack
            JavaValue retVal = cu.popOperandStack();
                        if (retVal instanceof JavaNonnullReferenceValue) {
                                JavaNonnullReferenceValue val = (JavaNonnullReferenceValue) retVal;
                                InternalClass cls = val.getObjectClass();
                                JavaInstance ji = val.toClassInstance();
                                
                                // look for radio
                                try {
                                        JavaValue radioVal = ji.getfield(cls.findField(new NameAndDescriptor("radio", "[I")));
                                        JavaArray arr = ((JavaNonnullReferenceValue)radioVal.toReference()).toArray();
                                        if (arr.getElements().length == 4) {
                                                radio[0] = new int[4];
                                                // copy out
                                                for (int i = 0; i < 4; i++) {
                                                        radio[0][i] = arr.get(i).toInt(false).getValue();
                                                }
                                        }
                                } catch (FieldNotFoundE e) {
//                                      e.printStackTrace();
                                }
                                
                                int orderType = ji.getfield(cls.findField(new NameAndDescriptor("orderType", "int"))).toInt(true).getValue();
                    Order order = null;
                                switch (orderType) {
                                case Order.MOVE:
                                        double destX = ji.getfield(cls.findField(new NameAndDescriptor("destX", "double"))).toDouble(true).getValue();
                                        double destY = ji.getfield(cls.findField(new NameAndDescriptor("destY", "double"))).toDouble(true).getValue();
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
            return null;
        }
        catch (Exception e) {
                if (!thinkFailure) {
                        System.err.println("failure to think: " + e + " " + e.getMessage());
                        e.printStackTrace();
                        thinkFailure = true;
                }
        }
        catch (Error err) {
            if (!thinkFailure) {
                System.err.println("error failure to think: " + err + " " + err.getMessage());
                err.printStackTrace();
                thinkFailure = true;
            }
        }

        return null;
    }

        public int build(double dx, double dy, double x, double y, int terrain, int id, int buildItem, double hp, double maxHP, double time,
                        double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] radioOut) {
        try {
            // convert args to hackjvm form
            List<JavaValue> args = new ArrayList<JavaValue>();
            JavaReferenceValue rv = new JavaNonnullReferenceValue(classObject);
            args.add(rv);
            args.add(HackHelper.createDoubleArg(dx));
            args.add(HackHelper.createDoubleArg(dy));
            args.add(HackHelper.createDoubleArg(x));
            args.add(HackHelper.createDoubleArg(y));
            args.add(new JavaIntValue(terrain));
            args.add(new JavaIntValue(id));
            args.add(new JavaIntValue(buildItem));
            args.add(new JavaDoubleValue(hp));
            args.add(new JavaDoubleValue(maxHP));
            args.add(new JavaDoubleValue(time));
            args.add(HackHelper.createArrayArg(cu, objX));
            args.add(HackHelper.createArrayArg(cu, objY));
            args.add(HackHelper.createArrayArg(cu, objID));
            args.add(HackHelper.createArrayArg(cu, objFaction));
            args.add(HackHelper.createArrayArg(cu, objType));
            JavaValue radioOutArg = HackHelper.create2DArrayArg(cu, radioOut);
            HackHelper.copy2Darray(radioOut, radioOutArg);
            args.add(radioOutArg);

            // call it
            cu.instructionCount = 0;
            cu.cleanFrames();
            cu.run(buildMethod, args.toArray(new JavaValue[0]));
//            lastCycles = cu.instructionCount;
            // get order object from stack
            JavaValue retVal = cu.popOperandStack();
            return retVal.toInt(false).getValue();
        }
        catch (Exception e) {
            System.err.println("failure to think: " + e + " " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
        }

        public int getLastInstructionCount() {
                return cu.instructionCount;
        }
}
