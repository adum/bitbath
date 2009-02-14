package ojvm.data;

/**
 * Representations of Java char values. 
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public class JavaCharValue extends JavaPrimitiveValue {
    private char v;
    public JavaCharValue (char v) { this.v = v; }
    public char getValue () { return v; }

    public int getSize () { return 1; }

    // No conversions possible other than identity conversion
    public JavaFloatValue toFloat (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Char to Float");
    }
    public JavaDoubleValue toDouble (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Char to Double");
    }
    public JavaByteValue toByte (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Char to Byte");
    }
    public JavaCharValue toChar (boolean force) {
        return this; 
    }
    public JavaShortValue toShort (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Char to Short");
    }
    public JavaIntValue toInt (boolean force) throws BadConversionE {
        //        throw new BadConversionE("Can't convert Char to Int");
        return new JavaIntValue(v);
    }
    public JavaLongValue toLong (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't convert Char to Long");
    }
    public ReturnAddress toReturnAddress () throws BadConversionE {
        throw new BadConversionE("Can't convert Char to ReturnAddress");
    }

    public String toString () {
        return "char " + v;
    }
}
