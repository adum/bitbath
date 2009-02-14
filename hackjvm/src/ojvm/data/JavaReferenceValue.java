package ojvm.data;

/**
 * Representations of Java reference values. 
 * 
 * File created June 8, 2000
 * @author Amr Sabry
 **/

public abstract class JavaReferenceValue extends JavaValue {
    // All references fit in one word.
    public int getSize () { return 1; }

    public abstract boolean isNull ();
    public abstract JavaNonnullReferenceValue toNonnullReference () throws BadConversionE;

    public abstract boolean isAssignmentCompatible (InternalClass targetClass);

    // No conversions possible
    public JavaFloatValue toFloat (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Reference to Float");
    }
    public JavaDoubleValue toDouble (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Reference to Double");
    }
    public JavaByteValue toByte (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Reference to Byte");
    }
    public JavaCharValue toChar (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Reference to Char");
    }
    public JavaShortValue toShort (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Reference to Short");
    }
    public JavaIntValue toInt (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Reference to Int");
    }
    public JavaLongValue toLong (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Reference to Long");
    }
    public ReturnAddress toReturnAddress () throws BadConversionE {
        throw new BadConversionE("Can't convert Reference to ReturnAddress");
    }
    public JavaReferenceValue toReference () throws BadConversionE {
        return this; 
    }
}
