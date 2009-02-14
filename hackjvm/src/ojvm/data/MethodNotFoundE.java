package ojvm.data;

import ojvm.operations.LinkE;

/**
 * Exceptions related to accessing the methods of a JavaInstance or a class
 * 
 * @author Amr Sabry 
 * @version jdk-1.1 
*/

public class MethodNotFoundE extends LinkE {
    public MethodNotFoundE (String s) { super(s); }
}
