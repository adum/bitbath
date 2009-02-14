package ojvm.loading;

import ojvm.operations.LinkE;

/**
 * Root of exceptions thrown because of loading errors
 * 
 * @author Amr Sabry 
 * @version jdk-1.2 
 **/

public class LoadE extends LinkE {
    public LoadE (String s) { super(s); }
}
