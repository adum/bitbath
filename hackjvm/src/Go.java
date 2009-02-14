// package ojvm;

import ojvm.util.Descriptor;
import ojvm.util.NameAndDescriptor;
import ojvm.util.BadDescriptorE;

import ojvm.data.JavaValue;
import ojvm.data.InternalClass;
import ojvm.data.InternalMethod;
import ojvm.data.InternalException;

import ojvm.machine.ControlUnit;

/**
 * Describe class <code>Go</code> here.
 *
 * @author <a href="mailto:sabry@semantix.cs.uoregon.edu">Amr A Sabry</a>
 * @version 1.0
 * @since 1.0
 */

public class Go {
    public static void main (String[] args) { 
        String name = null; // name of class to run
        Descriptor className = null; // descriptor of class to run

        if (args.length == 0) {
            System.err.println("pass class as first arg");
            return;
        }
        try {
            int classTarget = 0;
            String cp = "";
            while (true) {
                if (classTarget == args.length - 1) break; // no more room
                String s = args[classTarget];
                if (!s.startsWith("-")) break; // looks like a class name
                classTarget++;
                s = s.substring(1, s.length());
                if (s.equals("cp") || s.equals("classpath")) {
                    cp = args[classTarget++];
                }
                else {
                    throw new Exception("unknown arg: " + s);
                }
            }
            // Get the start class and arguments to its main method
            String[] mainArgs = new String[args.length - classTarget - 1];
            try {
                name = args[classTarget];
                System.arraycopy(args,classTarget + 1,mainArgs,0,mainArgs.length);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Need a class argument");
                System.exit(-1);
            }
        
            try {
                className = new Descriptor(name, Descriptor.JAVA_FORM);
            }
            catch (BadDescriptorE e) {
                System.out.println("Bad class name " + name);
                System.exit(-1);
            }

            // Create and initialize the virtual machine
            ControlUnit cu = new ControlUnit(cp);

            // Find and initialize the starting class
            InternalClass startClass = cu.findClass(className);
            cu.initializeClass(startClass);

            // Find "main"
            NameAndDescriptor mainKey = new NameAndDescriptor("main", "([Ljava/lang/String;)V");
            InternalMethod mainMethod = startClass.findMethod(mainKey);
            if (! (mainMethod.isPublic() && mainMethod.isStatic())) {
                System.out.println("Method main in class " + name + " should be declared public static");
                System.exit(-1);
            }
            
            // Run
//            cu.maxInstructions = 200;
            JavaValue[] runtimeMainArgs = cu.convertStringArgs(mainArgs);
            cu.run(mainMethod, runtimeMainArgs);
        }
        catch (InternalException e) {
            System.out.println("Internal Error: " + e.getMessage()); 
            e.printStackTrace();
        }
        catch (Throwable e) {
            System.out.println("Unknown Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
