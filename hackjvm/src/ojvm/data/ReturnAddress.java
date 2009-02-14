package ojvm.data;

/**
 * Representations of return addresses.
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public class ReturnAddress extends JavaPrimitiveValue {
    private int value;
    public ReturnAddress (int value) { this.value = value; }

    public int getSize () { return 1; }
    public int getValue() { return value; }

    // No conversions possible other than identity
    public JavaFloatValue toFloat (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert ReturnAddress to Float");
    }
    public JavaDoubleValue toDouble (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert ReturnAddress to Double");
    }
    public JavaByteValue toByte (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert ReturnAddress to Byte");
    }
    public JavaCharValue toChar (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert ReturnAddress to Char");
    }
    public JavaShortValue toShort (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert ReturnAddress to Short");
    }
    public JavaIntValue toInt (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert ReturnAddress to Int");
    }
    public JavaLongValue toLong (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert ReturnAddress to Long");
    }
    public ReturnAddress toReturnAddress () {
        return this; 
    }

    public String toString () { 
        return "returnAddress: "+ value;
    }
}
