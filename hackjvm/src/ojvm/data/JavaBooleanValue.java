package ojvm.data;

/**
 * Representations of Java boolean values. 
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public class JavaBooleanValue extends JavaPrimitiveValue {
    private boolean v;
    public JavaBooleanValue (boolean v) { this.v = v; }
    public boolean getValue () { return v; }

    public int getSize () { return 1; }

    // No conversions possible 
    public JavaFloatValue toFloat (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Boolean to Float");
    }
    public JavaDoubleValue toDouble (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Boolean to Double");
    }
    public JavaByteValue toByte (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Boolean to Byte");
    }
    public JavaCharValue toChar (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Boolean to Char");
    }
    public JavaShortValue toShort (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Boolean to Short");
    }
    public JavaIntValue toInt (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Boolean to Int");
    }
    public JavaLongValue toLong (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Boolean to Long");
    }
    public ReturnAddress toReturnAddress () throws BadConversionE {
        throw new BadConversionE("Can't convert Boolean to ReturnAddress");
    }

    public String toString () {
        return "boolean: " + v;
    }
}
