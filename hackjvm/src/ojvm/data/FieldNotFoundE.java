package ojvm.data;

import ojvm.operations.LinkE;

/**
 * Exceptions related to accessing the fields of a JavaInstance or a class
 * 
 * @author Amr Sabry 
 * @version jdk-1.1 
*/

public class FieldNotFoundE extends LinkE {
    public FieldNotFoundE (String s) { super(s); }
}
