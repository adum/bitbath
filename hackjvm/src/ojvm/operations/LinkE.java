package ojvm.operations;

import ojvm.data.InternalException;

/**
 * Exceptions related to loading, linking, and initializing classes
 * 
 * @author Amr Sabry 
 * @version jdk-1.1 
*/

public class LinkE extends InternalException {
    public LinkE (String s) { super(s); }
}
