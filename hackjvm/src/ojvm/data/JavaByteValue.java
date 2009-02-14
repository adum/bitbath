package ojvm.data;

/**
 * Representations of Java byte values. 
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public class JavaByteValue extends JavaPrimitiveValue {
    private byte v;
    public JavaByteValue (byte v) { this.v = v; }
    public byte getValue () { return v; }

    public int getSize () { return 1; }

    // No conversions possible other than identity conversion
    public JavaFloatValue toFloat (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Byte to Float");
    }
    public JavaDoubleValue toDouble (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Byte to Double");
    }
    public JavaByteValue toByte (boolean force) {
        return this;
    }
    public JavaCharValue toChar (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Byte to Char");
    }
    public JavaShortValue toShort (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Byte to Short");
    }
    public JavaIntValue toInt (boolean force) throws BadConversionE {
        return new JavaIntValue(v);
    }
    public JavaLongValue toLong (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Byte to Long");
    }
    public ReturnAddress toReturnAddress () throws BadConversionE {
        throw new BadConversionE("Can't convert Byte to ReturnAddress");
    }

    public String toString () {
        return "byte " + v;
    }
}
