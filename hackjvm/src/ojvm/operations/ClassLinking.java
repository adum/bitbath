package ojvm.operations;

import java.util.logging.Logger;

import ojvm.loading.AbsynClass;
import ojvm.loading.AbsynMethod;
import ojvm.loading.AbsynField;

import ojvm.machine.ControlUnit;

import ojvm.data.InternalClass;
import ojvm.data.InternalClassFromAbsyn;

import ojvm.util.Descriptor;
import ojvm.util.CommonDescriptors;

/**
 *
 * Takes an AbsynClass and builds an InternalClass. The reason this
 * file is here is because we need access to the machine internals
 * (the control unit) to verify the AbsynClass and build the InternalClass.
 * 
 * File created June 14, 2000
 * @author Amr Sabry
 **/

public class ClassLinking {
    private static Logger logger = Logger.getLogger(ClassLinking.class.getCanonicalName());

    private ControlUnit cu; // access to the machine state in case we need to load another class...

    public ClassLinking (ControlUnit cu) { 
        this.cu = cu;
    }

    public InternalClass linkClass (AbsynClass absynClass) throws LinkE {
        logger.fine("*****Linking " + absynClass.getDesc());
        verifyClassPassTwo(absynClass);
        verifyClassPassThree(absynClass);

        // Verification creates an internal class for the superClass and for the interfaces
        // here we just get them 
        Descriptor superClassDesc = absynClass.getSuperClassDesc();
        InternalClass superClass = null;
        if (superClassDesc != null) superClass = cu.findClass(superClassDesc);
        Descriptor[] interfaceDescs = absynClass.getInterfaceDescs();
        InternalClass[] interfaces = new InternalClass[interfaceDescs.length];
        for (int i=0; i<interfaceDescs.length; i++) interfaces[i] = cu.findClass(interfaceDescs[i]);

        // the constructor for InternalClass prepares the class...
        return new InternalClassFromAbsyn(superClass,interfaces,absynClass);
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    // Pass 2 does everything that does not require looking at the Code attributes

    private void verifyClassPassTwo (AbsynClass absynClass) throws LinkE {
        Descriptor classDesc = absynClass.getDesc();
        Descriptor superClassDesc = absynClass.getSuperClassDesc();
        Descriptor[] interfaceDescs = absynClass.getInterfaceDescs();
        AbsynField[] declaredFields = absynClass.getDeclaredFields();
        AbsynMethod[] declaredMethods = absynClass.getDeclaredMethods();

        if (absynClass.isInterface()) {
            if (! absynClass.isAbstract()) throw new VerifyE("Interface must be abstract");
            if (absynClass.isFinal()) //  || absynClass.accSuperSet()
                throw new VerifyE("Interface cannot be final");
        }
        else { // a class not interface 
            if (absynClass.isFinal() && absynClass.isAbstract()) 
                throw new VerifyE("Class cannot be both final and abstract");
        }

        if (! absynClass.isInterface()) { // a class not interface
            if (superClassDesc != null) { // we are not java.lang.Object 
                InternalClass superClass = cu.findClass(superClassDesc); // load it and link it if necessary
                if (superClass.isFinal()) throw new VerifyE("Attempt to extend final class");
            }
            // we must be java.lang.Object since we have no superclass
            else if (! classDesc.equals(CommonDescriptors.javaLangObjectDesc)) 
                throw new VerifyE("Class should have a superclass");
        }
        // an interface must have a valid superClassDesc pointing to java.lang.Object
        else if (! superClassDesc.equals(CommonDescriptors.javaLangObjectDesc)) 
            throw new VerifyE("Interface should have superclass point to java.lang.Object"); 
        
        InternalClass[] interfaces = new InternalClass[interfaceDescs.length];
        for (int i=0; i<interfaceDescs.length; i++) {
            interfaces[i] = cu.findClass(interfaceDescs[i]);
            if (! interfaces[i].isInterface()) throw new VerifyE("Implementing something that's not interface");
        }

        for (int i=0; i<declaredFields.length; i++) verifyFieldPassTwo(absynClass,declaredFields[i]);

        for (int i=0; i<declaredMethods.length; i++) verifyMethodPassTwo(absynClass,declaredMethods[i]);
    }

    private void verifyFieldPassTwo (AbsynClass absynClass, AbsynField absynField) throws VerifyE {
        if (! absynClass.isInterface()) { // a class not an interface 
            atMostOne(absynField.isPrivate(), absynField.isProtected(), absynField.isPublic());
            if (absynField.isFinal() && absynField.isVolatile()) 
                throw new VerifyE("Field cannot be final and volatile"); 
        }
        else { // an interface 
            if (! (absynField.isPublic() && absynField.isStatic() && absynField.isFinal()))
                throw new VerifyE("Interface field must be public, static, and final"); 
            if (absynField.isPrivate() ||
                absynField.isProtected() ||
                absynField.isVolatile() ||
                absynField.isTransient())
                throw new 
                    VerifyE("Interface field cannot be private, protected, volatile, or transient"); 
        }
    }

    private void verifyMethodPassTwo (AbsynClass absynClass, AbsynMethod absynMethod) throws VerifyE {
        if (! absynClass.isInterface()) { // a class
            atMostOne(absynMethod.isPrivate(), absynMethod.isProtected(), absynMethod.isPublic());
            if (absynMethod.isAbstract()) {
                if (absynMethod.isFinal() ||
                    absynMethod.isNative() ||
                    absynMethod.isPrivate() ||
                    absynMethod.isStatic() ||
                    absynMethod.isStrict() ||
                    absynMethod.isSynchronized()) {
                    String msg = "Abstract method cannot be final, native, private, static, strict, ";
                    msg += "or synchronized"; 
                    throw new VerifyE(msg);
                }
            }
        }
        else { // an interface 
            if (! (absynMethod.isAbstract() && absynMethod.isPublic()))
                throw new VerifyE("Interface method must be abstract and public"); 
            if (absynMethod.isPrivate() ||
                absynMethod.isProtected() ||
                absynMethod.isStatic() ||
                absynMethod.isFinal() ||
                absynMethod.isSynchronized() ||
                absynMethod.isNative() ||
                absynMethod.isStrict())
                throw new 
                    VerifyE("Interface method cannot have any modifier other than abstract/public"); 
        }
        if (absynMethod.isConstructor()) {
            atMostOne(absynMethod.isPrivate(), absynMethod.isProtected(), absynMethod.isPublic());
            if (absynMethod.isStatic() ||
                absynMethod.isFinal() ||
                absynMethod.isSynchronized() ||
                absynMethod.isNative() ||
                absynMethod.isAbstract())
                throw new 
                    VerifyE("Constructor cannot be static, final, synchronized, native, or abstract"); 
        }
    }

    private void atMostOne (boolean b1, boolean b2, boolean b3) throws VerifyE {
        int r = 0;
        r += b1? 1 : 0;
        r += b2? 1 : 0;
        r += b3? 1 : 0;
        if (r > 1) throw new VerifyE("Inconsitent flags in declaration");
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    // Pass 3

    private void verifyClassPassThree (AbsynClass absynClass) { 
        AbsynMethod[] declaredMethods = absynClass.getDeclaredMethods();
        for (int i=0; i<declaredMethods.length; i++) 
            verifyMethodPassThree(declaredMethods[i]);
    }
    
    private void verifyMethodPassThree (AbsynMethod absynMethod) {
        if (absynMethod.isNative() || absynMethod.isAbstract()) return;
        // call the bytecode verifier
    }
}

