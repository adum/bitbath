package ojvm.data;

/**
 * Double and Long take two words. The real representation is in
 * JavaDoubleValue and JavaLongValue; this is just a filler.
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public class SecondWord extends JavaValue {
    public int getSize () { return 0; }

    // No conversions possible
    public JavaFloatValue toFloat (boolean force) throws BadConversionE { 
        throw new BadConversionE("Can't use second word of a Double or Long");
    }
    public JavaDoubleValue toDouble (boolean force) throws BadConversionE { 
        throw new BadConversionE("Can't use second word of a Double or Long");
    }
    public JavaByteValue toByte (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't use second word of a Double or Long");
    }
    public JavaCharValue toChar (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't use second word of a Double or Long");
    }
    public JavaShortValue toShort (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't use second word of a Double or Long");
    }
    public JavaIntValue toInt (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't use second word of a Double or Long");
    }
    public JavaLongValue toLong (boolean force) throws BadConversionE {
        throw new BadConversionE("Can't use second word of a Double or Long");
    }
    public ReturnAddress toReturnAddress () throws BadConversionE {
        throw new BadConversionE("Can't use second word of a Double or Long");
    }
    public JavaReferenceValue toReference () throws BadConversionE {
        throw new BadConversionE("Can't use second word of a Double or Long");
    }
    public JavaNonnullReferenceValue toNonnullReference () throws BadConversionE {
        throw new BadConversionE("Can't use second word of a Double or Long");
    }

    public String toString () { 
        return "-";
    }   
}
