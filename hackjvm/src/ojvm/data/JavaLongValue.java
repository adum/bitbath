package ojvm.data;

/**
 * Representations of Java long values. 
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public class JavaLongValue extends JavaPrimitiveValue {
    private long v;
    public JavaLongValue (long v) { this.v = v; }
    public long getValue() { return v; }

    public int getSize () { return 2; }

    // Possible conversions: identity, toInt, toFloat, toDouble
    public JavaFloatValue toFloat (boolean force) throws BadConversionE { 
        if (force) return new JavaFloatValue((float)v);
        else throw new BadConversionE("Expecting a Float; found a Long");
    }
    public JavaDoubleValue toDouble (boolean force) throws BadConversionE { 
        if (force) return new JavaDoubleValue((double)v);
        else throw new BadConversionE("Expecting a Double; found a Long");
    }
    public JavaByteValue toByte (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Long to Byte");
    }
    public JavaCharValue toChar (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Long to Char");
    }
    public JavaShortValue toShort (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Long to Short");
    }
    public JavaIntValue toInt (boolean force) throws BadConversionE { 
        if (force) return new JavaIntValue((int)v);
        else throw new BadConversionE("Expecting an Int; found a Long");
    }
    public JavaLongValue toLong (boolean force) { 
        return this; 
    }
    public ReturnAddress toReturnAddress () throws BadConversionE {
        throw new BadConversionE("Can't convert Long to ReturnAddress");
    }

    public String toString () { 
        return "long: " + v;
    }
}

