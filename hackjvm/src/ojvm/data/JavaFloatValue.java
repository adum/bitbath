package ojvm.data;

/**
 * Representations of Java float values. 
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public class JavaFloatValue extends JavaPrimitiveValue {
    private float v;
    public JavaFloatValue (float v) { this.v = v; }
    public float getValue() { return v; }

    public int getSize () { return 1; }

    // Possible conversions: identity, toDouble, toLong, toInt
    public JavaFloatValue toFloat (boolean force) { 
        return this; 
    }
    public JavaDoubleValue toDouble (boolean force) throws BadConversionE {
        if (force) return new JavaDoubleValue((double)v);
        else throw new BadConversionE("Expecting a Double; found a Float");
    }
    public JavaByteValue toByte (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Float to Byte");
    }
    public JavaCharValue toChar (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Float to Char");
    }
    public JavaShortValue toShort (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Float to Short");
    }
    public JavaIntValue toInt (boolean force) throws BadConversionE {
        if (force) return new JavaIntValue((int)v);
        else throw new BadConversionE("Expecting a Double; found a Float");
    }
    public JavaLongValue toLong (boolean force) throws BadConversionE {
        if (force) return new JavaLongValue((long)v);
        else throw new BadConversionE("Expecting a Double; found a Float");
    }
    public ReturnAddress toReturnAddress () throws BadConversionE {
        throw new BadConversionE("Can't convert Float to ReturnAddress");
    }

    public String toString () { 
        return "float: " + v;
    }

}
