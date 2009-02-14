package ojvm.data;

/**
 * Representations of Java null reference values. 
 * 
 * File created June 14, 2000
 * @author Amr Sabry
 **/

public class JavaNullReferenceValue extends JavaReferenceValue {
    public static JavaNullReferenceValue nullRef = new JavaNullReferenceValue();

    private JavaNullReferenceValue () {}

    public boolean isNull () { return true; }

    public boolean isAssignmentCompatible (InternalClass targetClass) {
        // targetClass has been checked to be of reference type; null can be assigned to any reference type
        return true; 
    }

    public JavaNonnullReferenceValue toNonnullReference () throws BadConversionE {
        throw new BadConversionE("Can't convert null reference to a nonnull reference");
    }

    public String toString () {
        return "null";
    }

}
