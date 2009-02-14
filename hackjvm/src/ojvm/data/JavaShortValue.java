package ojvm.data;

/**
 * Representations of Java short values. 
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public class JavaShortValue extends JavaPrimitiveValue {
    private short v;
    public JavaShortValue (short v) { this.v = v; }

    public int getSize () { return 1; }

    // No conversions possible other than identity
    public JavaFloatValue toFloat (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Short to Float");
    }
    public JavaDoubleValue toDouble (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Short to Double");
    }
    public JavaByteValue toByte (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Short to Byte");
    }
    public JavaCharValue toChar (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Short to Char");
    }
    public JavaShortValue toShort (boolean force) { 
        return this; 
    }
    public JavaIntValue toInt (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Short to Int");
    }
    public JavaLongValue toLong (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Short to Long");
    }
    public ReturnAddress toReturnAddress () throws BadConversionE {
        throw new BadConversionE("Can't convert Short to ReturnAddress");
    }

    public String toString () { 
        return "short: "+ v;
    }
}
