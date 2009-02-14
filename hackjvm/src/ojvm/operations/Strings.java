package ojvm.operations;

import java.util.Hashtable;

import ojvm.data.JavaValue;
import ojvm.data.JavaCharValue;
import ojvm.data.JavaReferenceValue;
import ojvm.data.JavaNonnullReferenceValue;
import ojvm.data.JavaInstance;
import ojvm.data.JavaArray;
import ojvm.data.InternalClass;
import ojvm.data.InternalMethod;
import ojvm.data.JavaException;
import ojvm.data.JavaArrayOutOfBoundsE;

import ojvm.machine.ControlUnit;
import ojvm.machine.ThreadHaltE;
import ojvm.machine.LocalVarsE;

import ojvm.util.CommonDescriptors;
import ojvm.util.NameAndDescriptor;

/**
 * Representations of Java strings
 * 
 * File created June 23, 2000
 * @author Amr Sabry
 **/

public class Strings {
    static Hashtable internedStrings = new Hashtable();
    private ControlUnit cu;

    public Strings (ControlUnit cu) { this.cu = cu; }

    // this method creates a new instance no matter what; 
    // the next method is used for string literals that should be interned
    public JavaValue makeNewInstance (String chars) throws LinkE, JavaException {
        InternalClass javaLangString = cu.findClass(CommonDescriptors.javaLangStringDesc);
        cu.initializeClass(javaLangString);
        JavaInstance obj = new JavaInstance(javaLangString);
        JavaValue objRef = new JavaNonnullReferenceValue(obj);
        initializeString(objRef, chars);
        return objRef;
    }

    public JavaValue makeInstanceFromLiteral (String chars) throws LinkE, JavaException {
        // check if already interned...
        if (internedStrings.containsKey(chars)) {
            return (JavaValue) internedStrings.get(chars);
        }

        // not interned; make new reference, intern it
        JavaValue objRef = makeNewInstance(chars);
        internedStrings.put(chars,objRef);
        return objRef;
    }

    private JavaValue initializeString (JavaValue objRef, String chars) throws LinkE, JavaException {
        // call the following constructor to initialize the object: public <init> : ([C)V throws null
        try {
            // create a reference to a JavaArray of JavaCharValues; this is the second argument to the constructor
            // (the first argument is a reference to the object itself)
            int size = chars.length();
            InternalClass charClass = cu.findClass(CommonDescriptors.charDesc);
            InternalClass charArrayClass = cu.findClass(CommonDescriptors.charArrayDesc);
            JavaArray args = new JavaArray(charArrayClass,charClass,size);
            for (int i=0; i<size; i++) args.store(i,new JavaCharValue(chars.charAt(i)));
            JavaValue argTwo = new JavaNonnullReferenceValue(args);
            JavaValue[] argsArray = { objRef, argTwo };

            //  find the constructor, and call it
            InternalClass javaLangString = cu.findClass(CommonDescriptors.javaLangStringDesc);
            NameAndDescriptor key = new NameAndDescriptor("<init>","([C)V");
            InternalMethod constructor = javaLangString.findMethod(key);
            cu.runSpecialMethod(constructor,argsArray);
            return objRef;
        }
        catch (JavaArrayOutOfBoundsE e) {
            throw new Error("Bug in initialization of JavaString");
        }
        catch (LocalVarsE e) {
            throw new Error("Bug in initialization of JavaString");
        }
    }

    public JavaValue[] convertStringArgs (String[] mainArgs) throws LinkE, JavaException {
        try {
            InternalClass javaLangString = cu.findClass(CommonDescriptors.javaLangStringDesc);
            InternalClass stringArrayClass = cu.findClass(CommonDescriptors.stringArrayDesc);
            JavaArray args = new JavaArray(stringArrayClass,javaLangString,mainArgs.length);
            for (int i=0; i<mainArgs.length; i++)
                args.store(i, makeInstanceFromLiteral(mainArgs[i]));

            JavaValue argOne = new JavaNonnullReferenceValue(args);
            JavaValue[] argsArray = { argOne };
            return argsArray;
        }
        catch (JavaArrayOutOfBoundsE e) {
            throw new Error("Bug in initialization of arguments to main");
        }
    }
}
