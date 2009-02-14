package ojvm.data;

import ojvm.util.Descriptor;
import ojvm.util.NameAndDescriptor;

/**
 * Internal representation of classes for primitive types.
 * 
 * File created June 23, 2000
 * @author Amr Sabry
 **/

public class InternalClassForPrimitive extends InternalClass {
    private Descriptor desc;

    public InternalClassForPrimitive (InternalClass superClass, Descriptor desc) {
        super(superClass,new InternalClass[0], true);
        this.desc = desc;
    }

    public boolean isPrimitive() { return true; }
    public boolean isArray() { return false; }
    public boolean isReference() { return false; }

    public InternalClass getComponentClass() { return null; }

    public Descriptor getDesc () { return desc; }
    public boolean isPublic () { return true; }
    public boolean isFinal () { return true; }
    public boolean accSuperSet () { return false; }
    public boolean isInterface () { return false; }
    public boolean isAbstract () { return false; }

    public int getNumAllInstanceFields () { return 0; }
    public InternalField[] getDeclaredInstanceFields () { return new InternalField[0]; }
    public JavaValue[] makeNewInstanceFields () { return new JavaValue[0]; }
    public void initializeInstanceValues (JavaValue[] vs, int myLastIndex) { return; }
    public int findField (NameAndDescriptor key) throws FieldNotFoundE {
        throw new FieldNotFoundE("Primitive class " + desc + " has no fields");
    }
    public InternalField[] getDeclaredStaticFields () { return new InternalField[0]; }
    public JavaValue getstatic (NameAndDescriptor key) throws FieldNotFoundE {
        throw new FieldNotFoundE("Primitive class " + desc + " has no static fields");
    }
    public void putstatic (NameAndDescriptor key, JavaValue fv) throws FieldNotFoundE {
        throw new FieldNotFoundE("Primitive class " + desc + " has no static fields");
    }
    public InternalMethod getInitializer () { return null; }
    public InternalMethod findMethod (NameAndDescriptor key) throws MethodNotFoundE {
        throw new MethodNotFoundE("Primitive class " + desc + " has no methods");
    }
}
