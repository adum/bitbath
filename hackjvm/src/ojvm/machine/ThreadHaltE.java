package ojvm.machine;

import ojvm.data.InternalException;

/**
 * Exception thrown when a thread has no more instructions to execute
 * 
 * @author Amr Sabry 
 * @version jdk-1.1 
 **/

public class ThreadHaltE extends InternalException {
    public ThreadHaltE (String s) { super(s); }
}
