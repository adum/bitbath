package ojvm.data;

/**
 * Exceptions related to conversions among JavaValues.
 * 
 * @author Amr Sabry 
 * @version jdk-1.1 
*/

public class BadConversionE extends InternalException {
    public BadConversionE (String s) { super(s); }
}
