package ojvm.data;

import ojvm.util.Descriptor;
import ojvm.util.NameAndDescriptor;
import ojvm.util.BadDescriptorE;

/**
 * Internal representation of classes.
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public abstract class InternalClass extends InternalObject {
    protected InternalClass superClass; // may be null
    protected InternalClass[] interfaces; // immediate interfaces implemented by the class if it is a class
    protected boolean initialized;

    public InternalClass (InternalClass superClass, InternalClass[] interfaces, boolean initialized) {
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.initialized = initialized;
    }

    public boolean isInitialized () { return initialized; }
    public void setInitialized () { initialized=true; }

    public InternalClass getSuperClass() { return superClass; }
    public InternalClass[] getInterfaces() { return interfaces; }

    public boolean isSubclass (InternalClass otherClass) {
        if (getDesc().equals(otherClass.getDesc())) return true; 
        else {
            for (int i=0; i<interfaces.length; i++) {
                if (interfaces[i].isSubclass(otherClass)) return true;
            }
            if (superClass == null) return false;
            else return superClass.isSubclass(otherClass);
        }
    }

    public JavaValue getDefaultValue () { 
        try {
            return Descriptor.getDefaultValue(getDesc());
        }
        catch (BadDescriptorE e) { 
            throw new Error("Illegal descriptor " + e.getMessage());
        }
    }

    public abstract boolean isPrimitive(); 
    public abstract boolean isArray(); 
    public abstract boolean isReference(); 

    public abstract InternalClass getComponentClass();

    public abstract Descriptor getDesc ();
    public abstract boolean isPublic ();
    public abstract boolean isFinal ();
    public abstract boolean accSuperSet ();
    public abstract boolean isInterface ();
    public abstract boolean isAbstract ();
    public abstract int getNumAllInstanceFields ();
    public abstract InternalField[] getDeclaredInstanceFields ();
    public abstract JavaValue[] makeNewInstanceFields (); 
    public abstract void initializeInstanceValues (JavaValue[] vs, int myLastIndex);
    public abstract int findField (NameAndDescriptor key) throws FieldNotFoundE;
    public abstract InternalField[] getDeclaredStaticFields ();
    public abstract JavaValue getstatic (NameAndDescriptor key) throws FieldNotFoundE;
    public abstract void putstatic (NameAndDescriptor key, JavaValue fv) throws FieldNotFoundE;
    public abstract InternalMethod getInitializer ();
    public abstract InternalMethod findMethod (NameAndDescriptor key) throws MethodNotFoundE;
}
