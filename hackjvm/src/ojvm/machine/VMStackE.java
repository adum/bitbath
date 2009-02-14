package ojvm.machine;

import ojvm.data.InternalException;

/**
 * Exception thrown when attempting to pop from an empty Java stack.
 * 
 * @author Amr Sabry 
 * @version jdk-1.1 
*/

public class VMStackE extends InternalException {
    public VMStackE (String s) { super(s); }
}

