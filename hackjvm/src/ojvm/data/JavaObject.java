package ojvm.data;

/**
 * Representations of Java objects.
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public abstract class JavaObject {
    private InternalClass objectClass;

    public JavaObject (InternalClass objectClass) {
        this.objectClass = objectClass;
    }

    public InternalClass getObjectClass () {
        return objectClass;
    }

    public abstract JavaArray toArray () throws BadConversionE;
    public abstract JavaInstance toClassInstance () throws BadConversionE;
}
