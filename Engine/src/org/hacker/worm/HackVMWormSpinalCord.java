package org.hacker.worm;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ojvm.data.FieldNotFoundE;
import ojvm.data.InternalClass;
import ojvm.data.InternalMethod;
import ojvm.data.JavaInstance;
import ojvm.data.JavaLongValue;
import ojvm.data.JavaNonnullReferenceValue;
import ojvm.data.JavaObject;
import ojvm.data.JavaReferenceValue;
import ojvm.data.JavaValue;
import ojvm.data.MethodNotFoundE;
import ojvm.machine.ControlUnit;
import ojvm.operations.LinkE;
import ojvm.util.BadDescriptorE;
import ojvm.util.Descriptor;
import ojvm.util.NameAndDescriptor;

import org.hacker.engine.HackHelper;

public class HackVMWormSpinalCord implements WormSpinalCord {
    private final ControlUnit cu;
    private InternalMethod    thinkMethod;
    private JavaObject        classObject;
    private int               lastCycles;
    private String className;

    public HackVMWormSpinalCord(String cp, String className, long seed, String rootDir, int insCap) throws Exception {
        cu = new ControlUnit(cp);
        cu.maxInstructions = insCap;
        
        if (rootDir != null) cu.useExtraClassInput(rootDir);

        initClass(className, seed);
    }

    public HackVMWormSpinalCord(String cp, String className, long seed, DataInputStream dinp, int insCap, boolean isJar) throws Exception {
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
    
    @Override
        public String toString() {
                return className;
        }

        private void initClass(String className, long seed) throws BadDescriptorE, LinkE, FieldNotFoundE, MethodNotFoundE, Exception {
        this.className = className;
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
        // Find "main"
        NameAndDescriptor methodKey = new NameAndDescriptor("think", "(II[[IIII[[I)I");
        thinkMethod = startClass.findMethod(methodKey);
        if (!(thinkMethod.isPublic())) {
            throw new Exception("Method in class " + className + " should be declared public");
        }
    }

    public int think(int dx, int dy, int[][] board, int x, int y, int dir, int[][] enemies) {
        try {
            // convert args to hackjvm form
            List<JavaValue> args = new ArrayList<JavaValue>();
            JavaReferenceValue rv = new JavaNonnullReferenceValue(classObject);
            args.add(rv);
            args.add(HackHelper.createIntArg(dx));
            args.add(HackHelper.createIntArg(dy));
            JavaValue boardArg = HackHelper.create2DArrayArg(cu, board);
            HackHelper.copy2Darray(board, boardArg);
            args.add(boardArg);
            args.add(HackHelper.createIntArg(x));
            args.add(HackHelper.createIntArg(y));
            args.add(HackHelper.createIntArg(dir));
            JavaValue enemiesArg = HackHelper.create2DArrayArg(cu, enemies);
            HackHelper.copy2Darray(enemies, enemiesArg);
            args.add(enemiesArg);

            // call it
            cu.instructionCount = 0;
            cu.cleanFrames();
            cu.run(thinkMethod, args.toArray(new JavaValue[0]));
            lastCycles = cu.instructionCount;
            // get new direction from stack
            JavaValue retVal = cu.popOperandStack();
            return retVal.toInt(false).getValue();
        }
        catch (Exception e) {
            System.err.println("failure to think: " + e + " " + e.getMessage());
        }

        return 0;
    }

    public int getLastCycles() {
        return lastCycles;
    }

    public void addInfoProperties(Properties p) {
        p.setProperty("Instructions", ""+cu.instructionCount);
    }
}
