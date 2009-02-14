package ojvm.data;

import ojvm.util.Descriptor;
import ojvm.util.NameAndDescriptor;

/**
 * Internal representation of classes for array types.
 * 
 * File created June 23, 2000
 * @author Amr Sabry
 **/

public class InternalClassForArray extends InternalClass {
    private InternalClass componentClass;
    private int numDimensions;
    private Descriptor desc;

    public InternalClassForArray (InternalClass superClass, 
                                  InternalClass componentClass, 
                                  int numDimensions, 
                                  InternalClass[] interfaces,
                                  Descriptor desc) {
        super(superClass,interfaces,true);
        this.componentClass = componentClass;
        this.numDimensions = numDimensions;
        this.desc = desc;
    }

    public boolean isPrimitive() { return false; }
    public boolean isArray() { return true; }
    public boolean isReference() { return true; }

    public InternalClass getComponentClass() { return componentClass; }

    public Descriptor getDesc () { return desc; }
    public boolean isPublic () { return componentClass.isPublic(); }
    public boolean isFinal () { return true; }
    public boolean accSuperSet () { return false; }
    public boolean isInterface () { return false; }
    public boolean isAbstract () { return false; }

    public int getNumAllInstanceFields () { return 0; }
    public InternalField[] getDeclaredInstanceFields () { return new InternalField[0]; }
    public JavaValue[] makeNewInstanceFields () { return new JavaValue[0]; }
    public void initializeInstanceValues (JavaValue[] vs, int myLastIndex) { return; }
    public int findField (NameAndDescriptor key) throws FieldNotFoundE {
        throw new FieldNotFoundE("Array class " + desc + " has no fields");
    }
    public InternalField[] getDeclaredStaticFields () { return new InternalField[0]; }
    public JavaValue getstatic (NameAndDescriptor key) throws FieldNotFoundE {
        throw new FieldNotFoundE("Array class " + desc + " has no static fields");
    }
    public void putstatic (NameAndDescriptor key, JavaValue fv) throws FieldNotFoundE {
        throw new FieldNotFoundE("Array class " + desc + " has no static fields");
    }
    public InternalMethod getInitializer () { return null; }
    public InternalMethod findMethod (NameAndDescriptor key) throws MethodNotFoundE {
        // arrays inherit methods from java.lang.Object
        // but we should override "clone"
        return superClass.findMethod(key); 
    }
}
