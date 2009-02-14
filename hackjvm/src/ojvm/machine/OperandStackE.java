package ojvm.machine;

import ojvm.data.InternalException;

/**
 * Exceptions related to the operand stack operations
 * 
 * @author Amr Sabry 
 * @version jdk-1.1 
*/

public class OperandStackE extends InternalException {
    public OperandStackE (String s) { super(s); }
}
