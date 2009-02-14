package ojvm.data;

/**
 * 
 * These are exceptions thrown by our program. They should all be
 * caught and converted to appropriate JavaExceptions. It is a bug to
 * have an InternalException reach the interpreter.
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public abstract class InternalException extends Exception {
    // I am insisting on a string to describe the exception ...
    public InternalException (String s) { super(s); }
}
