package ojvm.data;

/**
 * Representations of Java primitive values. 
 * 
 * File created June 8, 2000
 * @author Amr Sabry
 **/

public abstract class JavaPrimitiveValue extends JavaValue {

    public JavaReferenceValue toReference () throws BadConversionE {
        throw new BadConversionE("Can't convert Primitive value to Reference");
    }
}
