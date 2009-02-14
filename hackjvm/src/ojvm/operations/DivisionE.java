package ojvm.operations;

import ojvm.data.InternalException;

/**
 * Exception thrown by primitive operations idiv, and irem
 * 
 * @author Amr Sabry 
 * @version jdk-1.2 
 **/

public class DivisionE extends InternalException {
    public DivisionE (String s) { super(s); }
}
