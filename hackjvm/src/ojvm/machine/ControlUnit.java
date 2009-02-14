package ojvm.machine;

import java.io.DataInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import ojvm.data.BadConversionE;
import ojvm.data.InternalClass;
import ojvm.data.InternalException;
import ojvm.data.InternalMethod;
import ojvm.data.JavaArray;
import ojvm.data.JavaArrayOutOfBoundsE;
import ojvm.data.JavaException;
import ojvm.data.JavaNonnullReferenceValue;
import ojvm.data.JavaValue;
import ojvm.data.ReturnAddress;
import ojvm.loading.AbsynClass;
import ojvm.loading.instructions.Instruction;
import ojvm.operations.BytecodeInterpreter;
import ojvm.operations.ClassInitializing;
import ojvm.operations.ClassLinking;
import ojvm.operations.ClassLoading;
import ojvm.operations.DirectoryClassLoading;
import ojvm.operations.Exceptions;
import ojvm.operations.JarStreamClassLoading;
import ojvm.operations.LinkE;
import ojvm.operations.MemoryClassLoading;
import ojvm.operations.NativeMethods;
import ojvm.operations.Strings;
import ojvm.util.BadDescriptorE;
import ojvm.util.Descriptor;

/**
 * A control unit for the machine that allows convenient access to the
 * internals of the machine and the global operations that use and
 * affect the state of the machine.
 * 
 * File created June 13, 2000
 * @author Amr Sabry
 **/

public class ControlUnit {
    private static Logger logger = Logger.getLogger(ControlUnit.class.getCanonicalName());
    public boolean debug_instructions = false;
    
    // pointers to machine components
    private ThreadsArea threadsArea;

    // pointers to global operations that need access to the machine state
    private ClassLoading classLoader;
    private ClassLinking classLinker;
    private ClassInitializing classInitializer;
    private BytecodeInterpreter bytecodeInterpreter;
//    private BytecodeVerifier bytecodeVerifier;
    private Strings stringOps;
    private Exceptions exceptionOps;
    private NativeMethods nativeMethods;

    private String indent = "";
    private String oldindent = "";

    private String classPath = null;
    
    public int maxInstructions = 0;
    public int instructionCount = 0;

    ////////////////////////////////////////////////////////////////////////////
    public ControlUnit (String classPath) throws LinkE, JavaException {
        this.classPath  = classPath;
        
        this.threadsArea = new ThreadsArea(); // first thing please

        this.classLoader = new ClassLoading(this);
        this.classLinker = new ClassLinking(this);
        this.classInitializer = new ClassInitializing(this);
        this.bytecodeInterpreter = new BytecodeInterpreter(this);
//        this.bytecodeVerifier = new BytecodeVerifier(this);
        this.stringOps = new Strings(this);
        this.exceptionOps = new Exceptions(this);
        this.nativeMethods = new NativeMethods(this);
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    // Facade to access machine components 

    public InternalClass getCurrentClass () { 
        return threadsArea.getCurrentClass(); 
    }

    public InternalMethod getCurrentMethod () { 
        return threadsArea.getCurrentMethod(); 
    }

    public JavaValue popOperandStack () throws OperandStackE { 
        return threadsArea.popOperandStack();
    }

    public void pushOperandStack (JavaValue v) throws OperandStackE { 
        threadsArea.pushOperandStack(v);
    }

    public JavaValue getLocalVar (int i) throws LocalVarsE {
        return threadsArea.getLocalVar(i);
    }

    public void putLocalVar (int i, JavaValue v) throws LocalVarsE {
        threadsArea.putLocalVar(i,v);
    }

    public ReturnAddress getCurrentPC() { 
        return threadsArea.getCurrentPC(); 
    }

    public void setPC (ReturnAddress newPC) {
        threadsArea.setPC(newPC);
    }

    public void incrementPC (int offset) {
        int currentAddress = getCurrentPC().getValue();
        ReturnAddress newPC = new ReturnAddress(currentAddress + offset);
        setPC(newPC);
    }

    VMFrame getCurrentFrame () { 
        return threadsArea.getCurrentFrame();
    }

    public void pushFrame (InternalMethod m, JavaValue[] args) throws LocalVarsE {
        oldindent = indent;
        // using the indent really _kills_ recursion
//        indent = indent + ">";
        threadsArea.pushFrame(m,args);
    }

    public void popFrame () throws VMStackE {
        indent = oldindent;
        threadsArea.popFrame();
    }
    
    public void cleanFrames() {
        try {
            while (true) {
                threadsArea.popFrame();
            }
        }
        catch (VMStackE e) {
            indent = "";
        }
    }

    public void run (InternalMethod m, JavaValue[] args) throws Exception {
        logger.setLevel(Level.ALL);
        logger.fine("run " + m.getName());
        try {
            nativeMethods.initialize();
            pushFrame(m,args);
            while (true) {
                int index = getCurrentPC().getValue();
                Instruction currentInstruction = getCurrentMethod().getInstructions()[index];
                incrementPC(currentInstruction.getLength());
                // System.out.print(indent);
                if (debug_instructions) {
//                    logger.info(instructionCount + " " + getCurrentClass().getDesc() + " :: " + getCurrentMethod().getName() + " " + 
//                            index + ": " + currentInstruction);// + ", " + getCurrentFrame());
                    System.out.println(instructionCount + " " + getCurrentClass().getDesc() + " :: " + getCurrentMethod().getName() + " " + 
                            index + ": " + currentInstruction + ", " + threadsArea.getStackSize());// + ", " + getCurrentFrame());
                }
                // System.out.println(getCurrentFrame());
                currentInstruction.accept(bytecodeInterpreter);
                if (++instructionCount == maxInstructions && maxInstructions != 0) {
                    throw new ExecutionTimeExceededException();
                }
            }
        }
        catch (ThreadHaltE e) { 
            return; // kill current thread and see if you can start another thread, otherwise return
        }
//        catch (JavaException e) { 
//            throw new Exception(e.getMessage()); // todo
//        }
        catch (InternalException e) { 
            throw new Exception(e.getMessage()); // todo
        }
    }

    // Occasionally, while we are executing a bytecode instruction, the
    // JVM needs to internally call a special method like a class initializer. 
    public void runSpecialMethod (InternalMethod m, JavaValue[] args) throws JavaException, LocalVarsE, ExecutionTimeExceededException {
        try {
            VMFrame callerFrame = getCurrentFrame(); 
            pushFrame(m,args);
            while (callerFrame != getCurrentFrame()) {
                int index = getCurrentPC().getValue();
                Instruction currentInstruction = getCurrentMethod().getInstructions()[index];
                incrementPC(currentInstruction.getLength());
                //                System.out.println();
                //                System.out.println(getCurrentFrame());
                //                System.out.print(indent);
                if (debug_instructions) {
//                    logger.info("* " + instructionCount + " " + getCurrentClass().getDesc() + " :: " + getCurrentMethod().getName() + "[" + 
//                            index + "] " + currentInstruction);
                        System.out.println("* " + instructionCount + " " + getCurrentClass().getDesc() + " :: " + getCurrentMethod().getName() + "[" + 
                            index + "] " + currentInstruction);
                }
                currentInstruction.accept(bytecodeInterpreter);
                if (++instructionCount == maxInstructions && maxInstructions != 0) {
                    throw new ExecutionTimeExceededException();
                }
            }
        }
        catch (ThreadHaltE e) {}
    }

    public void makeAndThrow (String exceptionName, String message) throws JavaException {
        logger.info("new exception: " + exceptionName + ", msg: " + message);
        exceptionOps.makeAndThrow(exceptionName, message);
    }

    public JavaArray createMultiArray(int dims, int[] dimSizes, Descriptor tp) throws BadConversionE, OperandStackE,
              JavaException, LinkE, BadDescriptorE {
          String s = tp.getStringDescriptor();
          s = s.substring(tp.getNumDimensions() - dims + 1, s.length());
          Descriptor compClassDesc = new Descriptor(s);
          Descriptor arrayClassDesc = new Descriptor("[" + s);
          InternalClass componentClass = findClass(compClassDesc);
          InternalClass arrayClass = findClass(arrayClassDesc);
          int ds = dims - 1;
          JavaArray ja = new JavaArray(arrayClass, componentClass, dimSizes[ds]);
          if (dims == 1) {
              // basic array
              return ja;
          }
          else {
              // more dims to go
              // fill in all values of this array from sub-arrays
              for (int i = 0; i < dimSizes[ds]; i++) {
                  JavaArray jasub = createMultiArray(dims - 1, dimSizes, tp);
                  try {
                      ja.store(i, new JavaNonnullReferenceValue(jasub));
                  }
                  catch (JavaArrayOutOfBoundsE e) {
                      e.printStackTrace();
                      throw new RuntimeException("illegal array size", e);
                  }
              }
          }
          return ja;
      }
    
    //////////////////////////////////////////////////////////////////////////////////////////
    // Facade to access all global operations

    // Strings

    public JavaValue[] convertStringArgs (String[] mainArgs) throws LinkE, JavaException {
        return stringOps.convertStringArgs(mainArgs);
    }

    public JavaValue makeStringInstanceFromLiteral (String chars) throws LinkE, JavaException {
        return stringOps.makeInstanceFromLiteral(chars);
    }

    // Class loading, linking, and initializing

    public InternalClass findClass (Descriptor className) throws LinkE {
        return classLoader.findClass(className);
    }

    public InternalClass linkClass (AbsynClass absynClass) throws LinkE {
        return classLinker.linkClass(absynClass);
    }

    public void initializeClass (InternalClass internalClass) throws LinkE { 
        classInitializer.initialize(internalClass);
    }

    // Native methods

    public void callNative (InternalMethod internalMethod, JavaValue[] args)
        throws LinkE, LocalVarsE, BadConversionE, OperandStackE, JavaException {
        nativeMethods.call(internalMethod,args);
    }

    public String getClassPath() {
        if (classPath != null) return classPath;
        return System.getProperty("java.class.path");
    }

    public void useExtraClassInput(String className, DataInputStream extraClassInput) {
        this.classLoader = new MemoryClassLoading(this, className, extraClassInput);
    }

    public void useExtraClassInputJarStream(String className, DataInputStream stream) {
        this.classLoader = new JarStreamClassLoading(this, stream);
    }

    public void useExtraClassInput(String rootDir) {
        this.classLoader = new DirectoryClassLoading(this, rootDir);
    }
}
