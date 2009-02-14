package ojvm.data;

/**
 * Representations of Java values. 
 * 
 * File created June 8, 2000
 * @author Amr Sabry
 **/

public abstract class JavaValue {

    // Most values fit in one word in the local variable array for
    // example, but long and double need two words.
    public abstract int getSize ();

    // Conversions
    public abstract JavaFloatValue toFloat (boolean force) throws BadConversionE;
    public abstract JavaDoubleValue toDouble (boolean force) throws BadConversionE;
    public abstract JavaByteValue toByte (boolean force) throws BadConversionE;
    public abstract JavaCharValue toChar (boolean force) throws BadConversionE;
    public abstract JavaShortValue toShort (boolean force) throws BadConversionE;
    public abstract JavaIntValue toInt (boolean force) throws BadConversionE;
    public abstract JavaLongValue toLong (boolean force) throws BadConversionE;
    public abstract ReturnAddress toReturnAddress () throws BadConversionE;
    public abstract JavaReferenceValue toReference () throws BadConversionE;
}

