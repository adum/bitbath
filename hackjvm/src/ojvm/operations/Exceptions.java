package ojvm.operations;

import ojvm.data.JavaValue;
import ojvm.data.JavaNonnullReferenceValue;
import ojvm.data.JavaInstance;
import ojvm.data.InternalClass;
import ojvm.data.InternalMethod;
import ojvm.data.JavaException;
import ojvm.data.MethodNotFoundE;

import ojvm.machine.ControlUnit;
import ojvm.machine.ThreadHaltE;
import ojvm.machine.LocalVarsE;

import ojvm.util.Descriptor;
import ojvm.util.BadDescriptorE;
import ojvm.util.NameAndDescriptor;

/**
 * Building Java exceptions out of internal exceptions
 * 
 * File created June 23, 2000
 * @author Amr Sabry
 **/

public class Exceptions {
    private ControlUnit cu;

    public Exceptions (ControlUnit cu) { this.cu = cu; }

    public void makeAndThrow (String exceptionName, String message) throws JavaException {
        //        System.out.println("*****Throwing exception " + exceptionName + ": " + message);
        try {
            Descriptor exceptionDesc = new Descriptor(exceptionName, Descriptor.JAVA_FORM);
            InternalClass exceptionClass = cu.findClass(exceptionDesc);
            cu.initializeClass(exceptionClass);
            JavaInstance exceptionInstance = new JavaInstance(exceptionClass);
            JavaNonnullReferenceValue exceptionRef = new JavaNonnullReferenceValue(exceptionInstance);
            initializeInstance(exceptionClass, exceptionRef, message);
            throw new JavaException(exceptionRef);
        }
        catch (BadDescriptorE e) {
            throw new Error("Bug: incorrect exception name " + exceptionName);
        }
        catch (LinkE e) {
            makeAndThrow ("java.lang.LinkageError", e.getMessage());
            // Be more specific?
        }
    }

    private void initializeInstance (InternalClass exceptionClass, JavaValue exceptionRef, String message) 
        throws LinkE, JavaException {

        try {
            NameAndDescriptor key = new NameAndDescriptor("<init>","(Ljava/lang/String;)V");
            InternalMethod constructor = exceptionClass.findMethod(key);
            JavaValue stringRef = cu.makeStringInstanceFromLiteral(message);
            JavaValue[] argsArray = { exceptionRef, stringRef };
            cu.runSpecialMethod(constructor,argsArray);
        }
        catch (LocalVarsE e) {
            throw new Error("Bug in initializing exception");
        }
        catch (MethodNotFoundE e) {
            throw new Error("Exceping that every instance of exceptions has a constructor that takes a string");
        }
    }
}
