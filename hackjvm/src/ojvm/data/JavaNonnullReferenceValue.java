package ojvm.data;

/**
 * Representations of Java non-null reference values. 
 * 
 * File created June 14, 2000
 * @author Amr Sabry
 **/

public class JavaNonnullReferenceValue extends JavaReferenceValue {
    private JavaObject object;

    public JavaNonnullReferenceValue (JavaObject object) {
        this.object = object;
    }

    public boolean isNull () { return false; }
    public JavaNonnullReferenceValue toNonnullReference () { return this; }

    public InternalClass getObjectClass () { return object.getObjectClass(); }

    public boolean isAssignmentCompatible (InternalClass targetClass) {
        InternalClass sourceClass = object.getObjectClass();
        
        if (sourceClass.isInterface()) {
            return sourceClass.isSubclass(targetClass);
        }
        else if (sourceClass.isArray()) {
            if (targetClass.isArray()) {
                InternalClass sourceComponentClass = sourceClass.getComponentClass();
                InternalClass targetComponentClass = targetClass.getComponentClass();
                return sourceComponentClass.isSubclass(targetComponentClass);
            }
            else return sourceClass.isSubclass(targetClass);
        }
        else if (sourceClass.isPrimitive()) return false; 
        else { // sourceClass is of class type 
            if (targetClass.isInterface()) {
                return sourceClass.isSubclass(targetClass);
            }
            else if (targetClass.isPrimitive()) return false; 
            else if (targetClass.isArray()) return false;
            else { // targetClass is of class type
                return sourceClass.isSubclass(targetClass);
            }
        }
    }

    public JavaArray toArray () throws BadConversionE { return object.toArray(); }
    public JavaInstance toClassInstance () throws BadConversionE { return object.toClassInstance(); }
    
    public String toString () {
        return "@" + object;
    }


}
