package ojvm.operations;

import java.util.logging.Logger;

import ojvm.data.*;
import ojvm.machine.*;

import ojvm.util.Descriptor;
import ojvm.util.BadDescriptorE;
import ojvm.util.CommonDescriptors;
import ojvm.util.NameAndDescriptor;

/**
 *
 * Native methods encountered while testing simple programs that use Sun's libraries
 *
 * File created June 22, 2000
 * @author Amr Sabry
 *  
*/

public class NativeMethods {
    private static Logger logger = Logger.getLogger(NativeMethods.class.getCanonicalName());
    
    public static boolean suppressPrints = false;

    private ControlUnit cu; 
    private boolean doneInit = false;

    public NativeMethods (ControlUnit cu) {
        this.cu = cu;
    }

    public void initialize () throws LinkE, JavaException {
        if (doneInit) return;
        try {
            // Apparently the java.lang.System class needs to be initialized using the following method
            // private static void initializeSystemClass()
            InternalClass javaLangSystem = cu.findClass(CommonDescriptors.javaLangSystemDesc);
            cu.initializeClass(javaLangSystem);
            NameAndDescriptor key = new NameAndDescriptor("initializeSystemClass","()V");
            InternalMethod m = javaLangSystem.findMethod(key);
            cu.runSpecialMethod(m,new JavaValue[0]);
        }
        catch (LocalVarsE e) {
            throw new Error("Bug in initializing native methods");
        }
        catch (MethodNotFoundE e) {
//            throw new Error("Expecting Java 1.2 library java.lang.System with method initializeSystemClass");
            logger.fine("Expecting Java 1.2 library java.lang.System with method initializeSystemClass (no biggie if this rt.jar doesn't have it tho)");
        }
        doneInit = true;
    }

    public void call (InternalMethod m, JavaValue[] args) 
        throws LinkE, LocalVarsE, OperandStackE, BadConversionE, JavaException {
        Descriptor declaringClass = m.getDeclaringClass().getDesc();
        String name = m.getName();
        String desc = m.getType().toString();
        
        ///////////////////////////////////////////////////////////////////
        // java.lang.Object
        ///////////////////////////////////////////////////////////////////

        // private static native registerNatives 
        if (declaringClass.equals(CommonDescriptors.javaLangObjectDesc) && 
            name.equals("registerNatives") && 
            desc.equals("()V")) {
            return;
        }
        // public native hashCode 
        else if (declaringClass.equals(CommonDescriptors.javaLangObjectDesc) &&
                 name.equals("hashCode") && 
                 desc.equals("()I")) {
            JavaValue objectref = args[0];
            int hashcode = objectref.hashCode();
            cu.pushOperandStack(new JavaIntValue(hashcode));
        }
        // public final native getClass
        else if (declaringClass.equals(CommonDescriptors.javaLangObjectDesc) &&
                 name.equals("getClass") && 
                 desc.equals("()Ljava/lang/Class;")) {
            JavaNonnullReferenceValue objectref = args[0].toReference().toNonnullReference();
            InternalClass internalResult = objectref.getObjectClass();
            InternalClass javaLangClass = cu.findClass(CommonDescriptors.javaLangClassDesc);
            cu.initializeClass(javaLangClass);
            JavaInstance internalInstance = new JavaInstance(javaLangClass,internalResult);
            JavaValue result = new JavaNonnullReferenceValue(internalInstance);
            cu.pushOperandStack(result);            
        }

        ///////////////////////////////////////////////////////////////////
        // java.lang.Class
        ///////////////////////////////////////////////////////////////////

        // private static native registerNatives 
        else if (declaringClass.equals(CommonDescriptors.javaLangClassDesc) && 
                 name.equals("registerNatives") && 
                 desc.equals("()V")) {
            return;
        }
        // private static native forName0 throws java.lang.ClassNotFoundException 
        else if (declaringClass.equals(CommonDescriptors.javaLangClassDesc) &&
                 name.equals("forName0") && 
                 desc.equals("(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;")) {
            try {
                JavaReferenceValue classNameReference = args[0].toReference();
                JavaInstance classNameInstance = classNameReference.toNonnullReference().toClassInstance();
                InternalClass javaLangStringClass = classNameInstance.getObjectClass();
                NameAndDescriptor key = new NameAndDescriptor("value","[C");
                int index = javaLangStringClass.findField(key);
                JavaReferenceValue valueField = classNameInstance.getfield(index).toReference();
                JavaValue[] javaCharsArray = valueField.toNonnullReference().toArray().getElements();

                key = new NameAndDescriptor("offset","int");
                index = javaLangStringClass.findField(key);
                int offset = classNameInstance.getfield(index).toInt(false).getValue();

                key = new NameAndDescriptor("count","int");
                index = javaLangStringClass.findField(key);
                int count = classNameInstance.getfield(index).toInt(false).getValue();

                char[] charsArray = new char[count];
                for (int i=0; i<count; i++) {
                    charsArray[i] = javaCharsArray[offset++].toChar(false).getValue();
                }
                Descriptor className = new Descriptor(new String(charsArray), Descriptor.JAVA_FORM);

                // they use 1 instead of true!!!!!!
                boolean init = args[1].toInt(false).getValue() == 1 ? true : false;
                JavaReferenceValue classLoader = args[2].toReference(); // ignore for now
                InternalClass internalResult = cu.findClass(className);
                if (init) cu.initializeClass(internalResult);
                // convert internalResult to an instance of java.lang.Class
                InternalClass javaLangClass = cu.findClass(CommonDescriptors.javaLangClassDesc);
                cu.initializeClass(javaLangClass);
                JavaInstance internalInstance = new JavaInstance(javaLangClass,internalResult);
                JavaValue result = new JavaNonnullReferenceValue(internalInstance);
                cu.pushOperandStack(result);
            }
            catch (BadDescriptorE e) {
                cu.makeAndThrow("java.lang.ClassNotFoundException", "Bad classname");
            }
        }
        // public native getName 
        else if (declaringClass.equals(CommonDescriptors.javaLangClassDesc) &&
                 name.equals("getName") && 
                 desc.equals("()Ljava/lang/String;")) {
            JavaReferenceValue objectref = args[0].toReference();
            JavaInstance objectInstance = objectref.toNonnullReference().toClassInstance();
            InternalClass objectClass = (InternalClass) objectInstance.getInternalObject(); 
            String className = objectClass.getDesc().toString();
            JavaValue result = cu.makeStringInstanceFromLiteral(className);
            cu.pushOperandStack(result);
        }

        // private native newInstance0 throws java.lang.InstantiationException java.lang.IllegalAccessException 
        else if (declaringClass.equals(CommonDescriptors.javaLangClassDesc) &&
                 name.equals("newInstance0") && 
                 desc.equals("()Ljava/lang/Object;")) {
            JavaReferenceValue classref = args[0].toReference();
            JavaInstance ji = classref.toNonnullReference().toClassInstance();
            InternalClass ic = (InternalClass) ji.getInternalObject();
            cu.initializeClass(ic);
            JavaObject obj = new JavaInstance(ic);
            JavaReferenceValue objref = new JavaNonnullReferenceValue(obj);
            NameAndDescriptor key = new NameAndDescriptor("<init>", "()V");
            InternalMethod cons = ic.findMethod(key);
            JavaValue[] consargs = { objref };
            cu.runSpecialMethod(cons,consargs);
            cu.pushOperandStack(objref); // ignore exceptions
        }

        ///////////////////////////////////////////////////////////////////
        // java.lang.ClassLoader
        ///////////////////////////////////////////////////////////////////

        // static native getCallerClassLoader : ()Ljava/lang/ClassLoader; 
        else if (declaringClass.equals(CommonDescriptors.javaLangClassLoaderDesc) &&
                 name.equals("getCallerClassLoader") && 
                 desc.equals("()Ljava/lang/ClassLoader;")) {
            // TODO
            cu.pushOperandStack(JavaNullReferenceValue.nullRef); 
        }

        ///////////////////////////////////////////////////////////////////
        // java.lang.System
        ///////////////////////////////////////////////////////////////////

        // private static native registerNatives 
        else if (declaringClass.equals(CommonDescriptors.javaLangSystemDesc) && 
                 name.equals("registerNatives") && 
                 desc.equals("()V")) {
            return;
        }
        // public static native currentTimeMillis 
        else if (declaringClass.equals(CommonDescriptors.javaLangSystemDesc) && 
                 name.equals("currentTimeMillis") && 
                 desc.equals("()J")) {
//            long ctm = System.currentTimeMillis();
            long ctm = 0; // we don't want to let them get entropy this way
            cu.pushOperandStack(new JavaLongValue(ctm));
        }
        // public static native arraycopy
        else if (declaringClass.equals(CommonDescriptors.javaLangSystemDesc) && 
                 name.equals("arraycopy") && 
                 desc.equals("(Ljava/lang/Object;ILjava/lang/Object;II)V")) {
            JavaReferenceValue a1 = args[0].toReference();
            if (a1.isNull()) cu.makeAndThrow("java.lang.NullPointerException", "Null reference to arraycopy");
            int i = args[1].toInt(false).getValue();
            JavaReferenceValue a2 = args[2].toReference();
            int j = args[3].toInt(false).getValue();
            int k = args[4].toInt(false).getValue();
            JavaArray ja1 = a1.toNonnullReference().toArray();
            JavaArray ja2 = a2.toNonnullReference().toArray();
            System.arraycopy(ja1.getElements(),i,ja2.getElements(),j,k);
        }
        // private static native initProperties 
        else if (declaringClass.equals(CommonDescriptors.javaLangSystemDesc) && 
                 name.equals("initProperties") && 
                 desc.equals("(Ljava/util/Properties;)Ljava/util/Properties;")) {
            // take the argument and save it in "private static Properties props;"
            // why are we returning another properties?
            NameAndDescriptor key = new NameAndDescriptor("props", "java.util.Properties");
            InternalClass javaLangSystem = cu.findClass(CommonDescriptors.javaLangSystemDesc);
            javaLangSystem.putstatic(key,args[0]);
            cu.pushOperandStack(args[0]);
        }
        // private static native  setIn0 
        else if (declaringClass.equals(CommonDescriptors.javaLangSystemDesc) &&
                 name.equals("setIn0") && 
                 desc.equals("(Ljava/io/InputStream;)V")) {
            NameAndDescriptor key = new NameAndDescriptor("in", "java.io.InputStream");
            InternalClass javaLangSystem = cu.findClass(CommonDescriptors.javaLangSystemDesc);
            javaLangSystem.putstatic(key,args[0]);
        }

        else if (declaringClass.equals(CommonDescriptors.javaLangSystemDesc) &&
                 name.equals("setOut0") && 
                 desc.equals("(Ljava/io/PrintStream;)V")) {
            NameAndDescriptor key = new NameAndDescriptor("out", "java.io.PrintStream");
            InternalClass javaLangSystem = cu.findClass(CommonDescriptors.javaLangSystemDesc);
            javaLangSystem.putstatic(key,args[0]);
        }

        else if (declaringClass.equals(CommonDescriptors.javaLangSystemDesc) &&
                 name.equals("setErr0") && 
                 desc.equals("(Ljava/io/PrintStream;)V")) {
            NameAndDescriptor key = new NameAndDescriptor("err", "java.io.PrintStream");
            InternalClass javaLangSystem = cu.findClass(CommonDescriptors.javaLangSystemDesc);
            javaLangSystem.putstatic(key,args[0]);
        }

        ///////////////////////////////////////////////////////////////////
        // java.io.FileInputStream
        ///////////////////////////////////////////////////////////////////

        // private static native  initIDs 
        else if (declaringClass.equals(CommonDescriptors.javaIoFileInputStreamDesc) &&
                 name.equals("initIDs") && 
                 desc.equals("()V")) {
            return;
        }

        ///////////////////////////////////////////////////////////////////
        // java.lang.double
        ///////////////////////////////////////////////////////////////////

        else if (declaringClass.equals(CommonDescriptors.javaLangDoubleDesc)) {
            if (name.equals("doubleToLongBits") && desc.equals("(D)J")) {
                long v = Double.doubleToLongBits(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaLongValue(v));
            }
            else if (name.equals("longBitsToDouble") && desc.equals("(J)D")) {
                double v = Double.longBitsToDouble(args[0].toLong(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
        }

        ///////////////////////////////////////////////////////////////////
        // java.lang.float
        ///////////////////////////////////////////////////////////////////

        else if (declaringClass.equals(CommonDescriptors.javaLangFloatDesc)) {
            if (name.equals("floatToIntBits") && desc.equals("(F)I")) {
                int v = Float.floatToIntBits(args[0].toFloat(true).getValue());
                cu.pushOperandStack(new JavaLongValue(v));
            }
            else if (name.equals("intBitsToFloat") && desc.equals("(I)F")) {
                Float v = Float.intBitsToFloat(args[0].toInt(true).getValue());
                cu.pushOperandStack(new JavaFloatValue(v));
            }
        }
        
        ///////////////////////////////////////////////////////////////////
        // java.lang.math
        ///////////////////////////////////////////////////////////////////

        else if (declaringClass.equals(CommonDescriptors.javaLangMathDesc)) {
            if (name.equals("sin") && desc.equals("(D)D")) {
                double v = Math.sin(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("cos") && desc.equals("(D)D")) {
                double v = Math.cos(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("acos") && desc.equals("(D)D")) {
                double v = Math.acos(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("asin") && desc.equals("(D)D")) {
                double v = Math.asin(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("atan") && desc.equals("(D)D")) {
                double v = Math.atan(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("atan2") && desc.equals("(DD)D")) {
                double v = Math.atan2(args[0].toDouble(true).getValue(), args[1].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("cbrt") && desc.equals("(D)D")) {
                double v = Math.cbrt(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("ceil") && desc.equals("(D)D")) {
                double v = Math.ceil(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("cosh") && desc.equals("(D)D")) {
                double v = Math.cosh(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("exp") && desc.equals("(D)D")) {
                double v = Math.exp(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("expm1") && desc.equals("(D)D")) {
                double v = Math.expm1(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("floor") && desc.equals("(D)D")) {
                double v = Math.floor(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("hypot") && desc.equals("(DD)D")) {
                double v = Math.hypot(args[0].toDouble(true).getValue(), args[1].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("log") && desc.equals("(D)D")) {
                double v = Math.log(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("pow") && desc.equals("(DD)D")) {
                double v = Math.pow(args[0].toDouble(true).getValue(), args[1].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("rint") && desc.equals("(D)D")) {
                double v = Math.rint(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("sinh") && desc.equals("(D)D")) {
                double v = Math.sinh(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("sqrt") && desc.equals("(D)D")) {
                double v = Math.sqrt(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("tan") && desc.equals("(D)D")) {
                double v = Math.tan(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
            else if (name.equals("tanh") && desc.equals("(D)D")) {
                double v = Math.tanh(args[0].toDouble(true).getValue());
                cu.pushOperandStack(new JavaDoubleValue(v));
            }
        }

        ///////////////////////////////////////////////////////////////////
        // java.io.FileOutputStream
        ///////////////////////////////////////////////////////////////////

        // private static native  initIDs 
        else if (declaringClass.equals(CommonDescriptors.javaIoFileOutputStreamDesc) &&
                 name.equals("initIDs") && 
                 desc.equals("()V")) {
            return;
        }
        
        else if (declaringClass.equals(CommonDescriptors.javaIoPrintStreamDesc) &&
                 name.equals("printBytes") && 
                 desc.equals("([B)V")) {
            JavaReferenceValue stringref = args[1].toReference();
            String s= args[1].toString();
            JavaNonnullReferenceValue nn = (JavaNonnullReferenceValue) stringref;
            JavaArray array = nn.toArray();
            
            JavaValue[] elements = array.getElements();
            for (int i = 0; i < elements.length; i++) {
                if (!suppressPrints)
                        System.out.print((char)elements[i].toByte(true).getValue());
            }
        }
        
        else if (declaringClass.equals(CommonDescriptors.javaIoPrintStreamDesc) &&
                 name.equals("print") && 
                 desc.equals("(Ljava/lang/String;)V")) {
            JavaReferenceValue stringref = args[1].toReference();
            String s= args[1].toString();
            JavaNonnullReferenceValue nn = (JavaNonnullReferenceValue) stringref;
            JavaArray array = nn.toArray();

                if (!suppressPrints)
                        System.out.print(s);
        }

        else if (declaringClass.equals(CommonDescriptors.javaIoFileOutputStreamDesc) &&
                 name.equals("writeBytes") && 
                 desc.equals("([BII)V")) {
            JavaValue fileOutputStream = args[0]; // assume system.out for now
            JavaReferenceValue arrayref = args[1].toReference();
            JavaValue[] javaBytesArray = arrayref.toNonnullReference().toArray().getElements();
            byte[] bytes = new byte[javaBytesArray.length];
            for (int i=0; i<bytes.length; i++) {
                bytes[i] = (byte) javaBytesArray[i].toInt(false).getValue();
            }
            int a = args[2].toInt(false).getValue();
            int b = args[3].toInt(false).getValue();

                if (!suppressPrints)
                        System.out.write(bytes,a,b);
        }

        ///////////////////////////////////////////////////////////////////
        // java.io.FileDescriptor
        ///////////////////////////////////////////////////////////////////

        // private static native  initIDs 
        else if (declaringClass.equals(CommonDescriptors.javaIoFileDescriptorDesc) &&
                 name.equals("initIDs") && 
                 desc.equals("()V")) {
            return;
        }

        ///////////////////////////////////////////////////////////////////
        // java.lang.Throwable
        ///////////////////////////////////////////////////////////////////

        // public native fillInStackTrace : ()Ljava/lang/Throwable; throws null
        else if (declaringClass.equals(CommonDescriptors.javaLangThrowableDesc) &&
                 name.equals("fillInStackTrace") && 
                 desc.equals("()Ljava/lang/Throwable;")) {
//            throw new Error("fillInStackTrace not implemented");
            System.err.println("fillInStackTrace not implemented");
            return;
        }

        ///////////////////////////////////////////////////////////////////
        // java.security.AccessController
        ///////////////////////////////////////////////////////////////////

        // public static native doPrivileged 
        else if (declaringClass.equals(CommonDescriptors.javaSecurityAccessControllerDesc) &&
                 name.equals("doPrivileged") && 
                 desc.equals("(Ljava/security/PrivilegedAction;)Ljava/lang/Object;")) {
            // skipping actual checks; TODO later
            JavaReferenceValue actionRef = args[0].toReference();
            JavaInstance actionInstance = actionRef.toNonnullReference().toClassInstance();
            InternalClass actionClass = actionInstance.getObjectClass();
            NameAndDescriptor key = new NameAndDescriptor("run", "()Ljava/lang/Object;");
            InternalMethod runMethod = actionClass.findMethod(key);
            JavaValue[] runArgs = { actionRef };
            cu.runSpecialMethod(runMethod,args);
        }

        else throw new Error("Unknown native method: " + declaringClass + " :: " + m);
    }
}
