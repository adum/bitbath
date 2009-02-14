package ojvm.data;

/**
 * Representations of Java double values. 
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public class JavaDoubleValue extends JavaPrimitiveValue {
    private double v;
    public JavaDoubleValue (double v) { this.v = v; }
    public double getValue() { return v; }

    public int getSize () { return 2; }

    // Possible conversions: identity, toFloat, toLong, toInt
    public JavaFloatValue toFloat (boolean force) throws BadConversionE { 
        if (force) return new JavaFloatValue((float)v); 
        else throw new BadConversionE("Expecting a Float; found a Double");
    }
    public JavaDoubleValue toDouble (boolean force) {
        return this; 
    }
    public JavaByteValue toByte (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Double to Byte");
    }
    public JavaCharValue toChar (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Double to Char");
    }
    public JavaShortValue toShort (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Double to Short");
    }
    public JavaIntValue toInt (boolean force) throws BadConversionE {
        if (force) return new JavaIntValue ((int)v);
        else throw new BadConversionE("Expecting an Int; found a Double");
    }
    public JavaLongValue toLong (boolean force) throws BadConversionE {
        if (force) return new JavaLongValue((long)v);
        else throw new BadConversionE("Expecting a Long; found a Double");
    }
    public ReturnAddress toReturnAddress () throws BadConversionE {
        throw new BadConversionE("Can't convert Double to ReturnAddress");
    }

    public String toString () { 
        return "double: " + v;
    }

}
