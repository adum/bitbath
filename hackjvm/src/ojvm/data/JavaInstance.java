package ojvm.data;

/**
 * Representations of Java objects. 
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public class JavaInstance extends JavaObject {
    private JavaValue[] fieldValues;
    // When creating an instance of java.lang.Class, all the information about the instance is in the internalObject
    // which in this case will be an InternalClass
    private InternalObject internalObject; 

    public JavaInstance (InternalClass objectClass) {
        super(objectClass);
        this.fieldValues = objectClass.makeNewInstanceFields();
    }

    public JavaInstance (InternalClass objectClass, InternalObject internalObject) {
        this(objectClass);
        this.internalObject = internalObject;
    }

    public JavaInstance toClassInstance () { return this; }
    public JavaArray toArray () throws BadConversionE {
        throw new BadConversionE("Expecting an array; found a class instance");
    }

    public InternalObject getInternalObject () { return internalObject; }

    public void putfield (int index, JavaValue v) throws FieldNotFoundE {
        try {
            fieldValues[index] = v;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new FieldNotFoundE("Invalid index " + index + " for field access");
        }
    }

    public JavaValue getfield (int index) throws FieldNotFoundE {
        try {
            return fieldValues[index];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new FieldNotFoundE("Invalid index " + index + " for field access");
        }
    }

    public String toString () {
        return "instanceof(" + getObjectClass().getDesc() + ")";
    }
}
