package ojvm.data;

/**
 * Representations of Java int values. 
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public class JavaIntValue extends JavaPrimitiveValue {
    private int v;
    public JavaIntValue (int v) { this.v = v; }
    public int getValue() { return v; }

    public int getSize () { return 1; }

    // Possible conversions: identity, toLong, toFloat, toDouble, toByte, toChar, toShort
    public JavaFloatValue toFloat (boolean force) throws BadConversionE {
        if (force) return new JavaFloatValue((float)v);
        else throw new BadConversionE("Expecting a Float; found an Int");
    }
    public JavaDoubleValue toDouble (boolean force) throws BadConversionE {
        if (force) return new JavaDoubleValue((double)v);
        else throw new BadConversionE("Expecting a Double; found an Int");
    }
    public JavaByteValue toByte (boolean force) throws BadConversionE {
        if (force) return new JavaByteValue((byte)v);
        else throw new BadConversionE("Expecting a Byte; found an Int");
    }
    public JavaCharValue toChar (boolean force) throws BadConversionE {
        if (force) return new JavaCharValue((char)v);
        else throw new BadConversionE("Expecting a Char; found an Int");
    }
    public JavaShortValue toShort (boolean force) throws BadConversionE {
        if (force) return new JavaShortValue((short)v);
        else throw new BadConversionE("Expecting a Short; found an Int");
    }
    public JavaIntValue toInt (boolean force) {
        return this;
    }
    public JavaLongValue toLong (boolean force) throws BadConversionE {
        if (force) return new JavaLongValue((long)v);
        else {
            throw new BadConversionE("Expecting a Long; found an Int");
        }
    }
    public ReturnAddress toReturnAddress () throws BadConversionE {
        throw new BadConversionE("Can't convert Int to ReturnAddress");
    }

    public String toString () { 
        return "int: " + v;
    }

}
